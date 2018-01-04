package ars.module.cms.service;

import java.io.File;
import java.util.Map;
import java.util.List;

import ars.file.Describe;
import ars.file.Operator;
import ars.file.disk.DiskOperator;
import ars.invoke.request.Requester;
import ars.invoke.request.ParameterInvalidException;
import ars.invoke.channel.http.Https;
import ars.invoke.channel.http.HttpRequester;
import ars.module.cms.model.Channel;
import ars.module.cms.service.ChannelService;
import ars.database.repository.Query;
import ars.database.repository.Repository;
import ars.database.service.StandardGeneralService;

/**
 * 栏目业务操作抽象实现
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public abstract class AbstractChannelService<T extends Channel> extends StandardGeneralService<T>
		implements ChannelService<T> {
	private Operator templateOperator = new DiskOperator();

	public Operator getTemplateOperator() {
		return templateOperator;
	}

	public void setTemplateOperator(Operator templateOperator) {
		this.templateOperator = templateOperator;
	}

	@Override
	public void initObject(Requester requester, T entity, Map<String, Object> parameters) {
		super.initObject(requester, entity, parameters);
		if (entity.getGroups().isEmpty()) {
			throw new ParameterInvalidException("groups", "required");
		}
		Channel parent = entity.getParent();
		Query<T> query = this.getRepository().query().ne("id", entity.getId()).eq("name", entity.getName());
		if (parent == null) {
			query.empty("parent");
		} else {
			query.eq("parent", parent);
		}
		if (query.count() > 0) {
			throw new ParameterInvalidException("name", "exist");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateObject(Requester requester, T object) {
		T parent = (T) object.getParent();
		Repository<T> repository = this.getRepository();
		T old = repository.get(object.getId());
		super.updateObject(requester, object);
		if (old.getActive() != object.getActive()) {
			if (object.getActive() == Boolean.TRUE) {
				while (parent != null) {
					if (parent.getActive() == Boolean.FALSE) {
						parent.setActive(true);
						repository.update(parent);
					}
					parent = (T) parent.getParent();
				}
			} else if (object.getActive() == Boolean.FALSE) {
				List<T> menus = repository.query().ne("id", object.getId()).eq("active", true)
						.start("key", object.getKey()).list();
				for (int i = 0; i < menus.size(); i++) {
					T menu = menus.get(i);
					menu.setActive(false);
					repository.update(menu);
				}
			}
		}
	}

	@Override
	public List<Describe> templates(Requester requester, Map<String, Object> parameters) throws Exception {
		if (this.templateOperator == null) {
			throw new RuntimeException("Template operator has not been initialize");
		}
		return this.templateOperator.trees(null, parameters);
	}

	@Override
	public String view(HttpRequester requester, Integer id, String code, Map<String, Object> parameters)
			throws Exception {
		if (this.templateOperator == null) {
			throw new RuntimeException("Template operator has not been initialize");
		}
		T channel = id != null ? this.getRepository().get(id)
				: code != null ? this.getRepository().query().eq("code", code).single() : null;
		if (channel == null || channel.getTemplate() == null) {
			return null;
		}
		String workingDirectory = this.templateOperator.getWorkingDirectory();
		String template = new File(workingDirectory, channel.getTemplate()).getPath()
				.substring(Https.ROOT_PATH.length());
		return Https.render(requester, template, channel, parameters);
	}

}
