package ars.module.cms.service;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

import ars.util.Streams;
import ars.util.Strings;
import ars.file.Describe;
import ars.file.disk.DiskOperator;
import ars.invoke.request.Requester;
import ars.invoke.request.ParameterInvalidException;
import ars.invoke.channel.http.HttpRequester;
import ars.module.cms.model.Channel;
import ars.module.cms.service.ChannelService;
import ars.database.repository.Query;
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
	private String templateDirectory = Strings.TEMP_PATH; // 模板文件目录
	private String staticizeDirectory = Strings.TEMP_PATH; // 静态化文件目录

	public String getTemplateDirectory() {
		return templateDirectory;
	}

	public void setTemplateDirectory(String templateDirectory) {
		this.templateDirectory = Strings.getRealPath(templateDirectory);
	}

	public String getStaticizeDirectory() {
		return staticizeDirectory;
	}

	public void setStaticizeDirectory(String staticizeDirectory) {
		this.staticizeDirectory = Strings.getRealPath(staticizeDirectory);
	}

	@Override
	public void initObject(Requester requester, T entity) {
		super.initObject(requester, entity);
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

	@Override
	public List<Describe> templates(Requester requester) throws Exception {
		return new DiskOperator(this.templateDirectory).trees(null, requester.getParameters());
	}

	@Override
	public String view(HttpRequester requester) throws Exception {
		T channel = this.object(requester);
		return channel == null || channel.getTemplate() == null ? null : requester.view(channel.getTemplate(), channel);
	}

	@Override
	public void render(HttpRequester requester) throws Exception {
		T channel = this.object(requester);
		if (channel != null && channel.getTemplate() != null) {
			String name = new StringBuilder("channel_").append(channel.getId()).append(".html").toString();
			File file = new File(this.staticizeDirectory, name);
			if (!channel.getStaticize() || !file.exists()) {
				synchronized (name.intern()) {
					if (!channel.getStaticize()) {
						channel = this.object(requester);
					}
					if (!channel.getStaticize() || !file.exists()) {
						requester.render(channel.getTemplate(), channel, file);
						if (!channel.getStaticize()) {
							channel.setStaticize(true);
							this.getRepository().update(channel);
						}
					}
				}
			}
			OutputStream os = requester.getHttpServletResponse().getOutputStream();
			try {
				Streams.write(file, os);
			} finally {
				os.close();
			}
		}
	}

}
