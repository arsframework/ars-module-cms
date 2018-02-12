package ars.module.cms.service;

import java.util.Map;
import java.util.List;

import ars.file.Describe;
import ars.file.Operator;
import ars.file.disk.DiskOperator;
import ars.invoke.request.Requester;
import ars.invoke.request.AccessDeniedException;
import ars.invoke.request.ParameterInvalidException;
import ars.invoke.channel.http.HttpChannel;
import ars.invoke.channel.http.HttpRequester;
import ars.module.cms.model.Channel;
import ars.module.cms.service.ChannelService;
import ars.module.people.model.User;
import ars.module.people.model.Group;
import ars.database.repository.Query;
import ars.database.repository.Repository;
import ars.database.repository.Repositories;
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
	private Operator operator = new DiskOperator();

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	@Override
	public void initObject(Requester requester, T entity, Map<String, Object> parameters) {
		super.initObject(requester, entity, parameters);
		if (entity.getGroups().isEmpty()) {
			throw new ParameterInvalidException("groups", "required");
		}
		User owner = Repositories.getRepository(User.class).query().eq("code", requester.getUser()).single();
		if (!owner.getAdmin()) {
			for (Group group : entity.getGroups()) {
				if (!group.getKey().startsWith(owner.getGroup().getKey())) {
					throw new ParameterInvalidException("groups", "illegal");
				}
			}
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
				List<T> channels = repository.query().ne("id", object.getId()).eq("active", true)
						.start("key", object.getKey()).list();
				for (int i = 0; i < channels.size(); i++) {
					T channel = channels.get(i);
					channel.setActive(false);
					repository.update(channel);
				}
			}
		}
	}

	@Override
	public void deleteObject(Requester requester, T object) {
		User owner = Repositories.getRepository(User.class).query().eq("code", requester.getUser()).single();
		if (!owner.getAdmin()) {
			for (Group group : object.getGroups()) {
				if (!group.getKey().startsWith(owner.getGroup().getKey())) {
					throw new AccessDeniedException("error.data.unauthorized");
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
		T channel = this.object(requester, parameters);
		return channel == null || channel.getTemplate() == null ? null
				: ((HttpChannel) requester.getChannel()).view(requester, channel.getTemplate(), channel);
	}

	@Override
	public void render(HttpRequester requester, Map<String, Object> parameters) throws Exception {
		T channel = this.object(requester, parameters);
		if (channel != null && channel.getTemplate() != null) {
			((HttpChannel) requester.getChannel()).render(requester, channel.getTemplate(), channel);
		}
	}

}
