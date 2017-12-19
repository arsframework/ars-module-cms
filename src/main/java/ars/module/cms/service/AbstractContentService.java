package ars.module.cms.service;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import ars.util.Beans;
import ars.util.Nfile;
import ars.util.Strings;
import ars.file.Operator;
import ars.file.Describe;
import ars.file.DiskOperator;
import ars.file.RandomNameGenerator;
import ars.file.DateDirectoryGenerator;
import ars.invoke.channel.http.Https;
import ars.invoke.channel.http.HttpRequester;
import ars.invoke.request.Requester;
import ars.invoke.request.AccessDeniedException;
import ars.invoke.request.RequestHandleException;
import ars.invoke.request.ParameterInvalidException;
import ars.module.cms.model.Tag;
import ars.module.cms.model.Channel;
import ars.module.cms.model.Content;
import ars.module.cms.service.TagService;
import ars.module.cms.service.ContentService;
import ars.module.people.model.User;
import ars.module.people.model.Group;
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

	private String staticDirectory; // 内容静态资源目录
	private String templateDirectory; // 内容模板资源目录
	private Operator staticOperator;
	private Operator templateOperator;

	@Resource
	private TagService<Tag> tagService;

	public String getStaticDirectory() {
		return staticDirectory;
	}

	public void setStaticDirectory(String staticDirectory) {
		this.staticDirectory = staticDirectory;
		DiskOperator operator = new DiskOperator(staticDirectory);
		operator.setNameGenerator(new RandomNameGenerator());
		operator.setDirectoryGenerator(new DateDirectoryGenerator());
		this.staticOperator = operator;
	}

	public String getTemplateDirectory() {
		return templateDirectory;
	}

	public void setTemplateDirectory(String templateDirectory) {
		this.templateDirectory = templateDirectory;
		this.templateOperator = new DiskOperator(templateDirectory);
	}

	public Operator getStaticOperator() {
		return staticOperator;
	}

	public void setStaticOperator(Operator staticOperator) {
		this.staticOperator = staticOperator;
	}

	public Operator getTemplateOperator() {
		return templateOperator;
	}

	public void setTemplateOperator(Operator templateOperator) {
		this.templateOperator = templateOperator;
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
						throw new AccessDeniedException("Illegal operation");
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
		String tag = object.getTag();
		if (tag != null) {
			for (String t : tag.split("[,，]")) {
				t = t.trim();
				if (t.isEmpty()) {
					continue;
				}
				synchronized (("__cms_tag_" + t).intern()) {
					Tag entity = this.tagService.getRepository().query().eq("name", t).single();
					if (entity == null) {
						entity = new Tag();
						entity.setName(t);
						this.tagService.saveObject(requester, entity);
					}
				}
			}
		}
		return super.saveObject(requester, object);
	}

	@Override
	public void updateObject(Requester requester, T object) {
		if (object.getTag() != null) {
			T old = this.getRepository().get(object.getId());
			if (!Beans.isEqual(old.getTag(), object.getTag())) {
				for (String t : object.getTag().split("[,，]")) {
					t = t.trim();
					if (t.isEmpty()) {
						continue;
					}
					synchronized (("__cms_tag_" + t).intern()) {
						Tag entity = this.tagService.getRepository().query().eq("name", t).single();
						if (entity == null) {
							entity = new Tag();
							entity.setName(t);
							this.tagService.saveObject(requester, entity);
						}
					}
				}
			}
		}
		super.updateObject(requester, object);
	}

	@Override
	public void deleteObject(Requester requester, T object) {
		if (requester.getUser().equals(object.getCreator()) && !Repositories.getRepository(User.class).query()
				.eq("code", requester.getUser()).single().getAdmin()) {
			throw new RequestHandleException("No delete permission");
		}
		super.deleteObject(requester, object);
	}

	@Override
	public List<Describe> templates(Requester requester, Map<String, Object> parameters) throws Exception {
		if (this.templateOperator == null) {
			throw new RuntimeException("Template operator has not been initialize");
		}
		return this.templateOperator.trees(null, parameters);
	}

	@Override
	public String view(HttpRequester requester, Integer id, Map<String, Object> parameters) throws Exception {
		T content = this.getRepository().get(id);
		if (content == null || content.getTemplate() == null) {
			return null;
		}
		if (this.templateOperator == null) {
			throw new RuntimeException("Template operator has not been initialize");
		}
		String workingDirectory = this.templateOperator.getWorkingDirectory();
		String template = new File(workingDirectory, content.getTemplate()).getPath()
				.substring(Https.ROOT_PATH.length());
		return Https.render(requester, template, content, parameters);
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

	@Override
	public String upload(Requester requester, String path, Nfile file, Map<String, Object> parameters)
			throws Exception {
		if (this.staticOperator == null) {
			throw new RuntimeException("Static operator has not been initialize");
		}
		return this.staticOperator.write(file, path);
	}

	@Override
	public Nfile download(Requester requester, String path, Map<String, Object> parameters) throws Exception {
		if (this.staticOperator == null) {
			throw new RuntimeException("Static operator has not been initialize");
		}
		return this.staticOperator.read(path);
	}

}
