package ars.module.cms.service;

import java.util.Map;
import java.util.List;
import java.util.regex.Pattern;
import java.io.Serializable;

import ars.util.Beans;
import ars.util.Strings;
import ars.file.Operator;
import ars.file.Describe;
import ars.file.disk.DiskOperator;
import ars.invoke.channel.http.HttpChannel;
import ars.invoke.channel.http.HttpRequester;
import ars.invoke.request.Requester;
import ars.invoke.request.AccessDeniedException;
import ars.invoke.request.ParameterInvalidException;
import ars.module.cms.model.Tag;
import ars.module.cms.model.Channel;
import ars.module.cms.model.Content;
import ars.module.cms.service.ContentService;
import ars.module.people.model.User;
import ars.module.people.model.Group;
import ars.database.repository.Repository;
import ars.database.repository.Repositories;
import ars.database.service.StandardGeneralService;

/**
 * 文章业务操作抽象实现
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public abstract class AbstractContentService<T extends Content> extends StandardGeneralService<T>
		implements ContentService<T> {
	/**
	 * Html标本标签正则表达匹配对象
	 */
	public static final Pattern HTML_SCRIPT_PATTERN = Pattern.compile("<script[^>]*?>[\\s\\S]*?<\\/script>",
			Pattern.CASE_INSENSITIVE);

	private Operator operator = new DiskOperator();

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	/**
	 * 保存内容标签
	 * 
	 * @param name
	 *            标签名称
	 */
	protected void saveTag(String name) {
		if (name != null) {
			Repository<Tag> repository = Repositories.getRepository(Tag.class);
			for (String item : name.split("[,，]")) {
				item = item.trim();
				if (item.isEmpty()) {
					continue;
				}
				synchronized (("__cms_tag_" + item).intern()) {
					Tag entity = repository.query().eq("name", item).single();
					if (entity == null) {
						entity = new Tag();
						entity.setName(item);
						repository.save(entity);
					}
				}
			}
		}
	}

	@Override
	public void initObject(Requester requester, T entity, Map<String, Object> parameters) {
		super.initObject(requester, entity, parameters);
		if (entity.getChannels().isEmpty()) {
			throw new ParameterInvalidException("channels", "required");
		}
		User owner = Repositories.getRepository(User.class).query().eq("code", requester.getUser()).single();
		if (!owner.getAdmin()) {
			for (Channel channel : entity.getChannels()) {
				for (Group group : channel.getGroups()) {
					if (!group.getKey().startsWith(owner.getGroup().getKey())) {
						throw new ParameterInvalidException("channels", "illegal");
					}
				}
			}
		}
		if (entity.getTitle() != null) {
			entity.setTitle(HTML_SCRIPT_PATTERN.matcher(entity.getTitle()).replaceAll(Strings.EMPTY_STRING));
		}
		if (entity.getTxt() != null) {
			entity.setTxt(HTML_SCRIPT_PATTERN.matcher(entity.getTxt()).replaceAll(Strings.EMPTY_STRING));
		}
	}

	@Override
	public Serializable saveObject(Requester requester, T object) {
		this.saveTag(object.getTag());
		return super.saveObject(requester, object);
	}

	@Override
	public void updateObject(Requester requester, T object) {
		if (object.getTag() != null
				&& !Beans.isEqual(this.getRepository().get(object.getId()).getTag(), object.getTag())) {
			this.saveTag(object.getTag());
		}
		super.updateObject(requester, object);
	}

	@Override
	public void deleteObject(Requester requester, T object) {
		User owner = Repositories.getRepository(User.class).query().eq("code", requester.getUser()).single();
		if (!owner.getAdmin()) {
			for (Channel channel : object.getChannels()) {
				for (Group group : channel.getGroups()) {
					if (!group.getKey().startsWith(owner.getGroup().getKey())) {
						throw new AccessDeniedException("error.data.unauthorized");
					}
				}
			}
		}
		super.deleteObject(requester, object);
	}

	@Override
	public List<Describe> templates(Requester requester, Map<String, Object> parameters) throws Exception {
		return this.operator.trees(null, parameters);
	}

	@Override
	public String view(HttpRequester requester, Map<String, Object> parameters) throws Exception {
		T content = this.object(requester, parameters);
		return content == null || content.getTemplate() == null ? null
				: ((HttpChannel) requester.getChannel()).view(requester, content.getTemplate(), content);
	}

	@Override
	public void render(HttpRequester requester, Map<String, Object> parameters) throws Exception {
		T content = this.object(requester, parameters);
		if (content != null && content.getTemplate() != null) {
			((HttpChannel) requester.getChannel()).render(requester, content.getTemplate(), content);
		}
	}

	@Override
	public int accesses(Requester requester, Integer id, Map<String, Object> parameters) {
		synchronized (("__cms_content_accesse_count_" + id).intern()) {
			T content = this.getRepository().get(id);
			if (content != null) {
				int number = content.getAccesses();
				content.setAccesses(++number);
				this.updateObject(requester, content);
				return number;
			}
		}
		return 0;
	}

}
