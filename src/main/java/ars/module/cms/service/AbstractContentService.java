package ars.module.cms.service;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

import ars.util.Servers;
import ars.util.Streams;
import ars.util.Strings;
import ars.file.Describe;
import ars.file.disk.DiskOperator;
import ars.invoke.request.Requester;
import ars.invoke.request.ParameterInvalidException;
import ars.invoke.channel.http.Https;
import ars.invoke.channel.http.HttpRequester;
import ars.module.cms.model.Content;
import ars.module.cms.service.ContentService;
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
		if (entity.getChannels().isEmpty()) {
			throw new ParameterInvalidException("channels", "required");
		}
		entity.setTxt(Https.getSafeHtml(entity.getTxt()));
	}

	@Override
	public List<Describe> templates(Requester requester) throws Exception {
		return new DiskOperator(this.templateDirectory).trees(null, requester.getParameters());
	}

	@Override
	public String view(HttpRequester requester) throws Exception {
		T content = this.object(requester);
		return content == null || content.getTemplate() == null ? null : requester.view(content.getTemplate(), content);
	}

	@Override
	public void render(HttpRequester requester) throws Exception {
		T content = this.object(requester);
		if (content != null && content.getTemplate() != null) {
			String name = new StringBuilder("content_").append(content.getId()).append(".html").toString();
			File file = new File(this.staticizeDirectory, name);
			if (!content.getStaticize() || !file.exists()) {
				synchronized (name.intern()) {
					if (!content.getStaticize()) {
						content = this.object(requester);
					}
					if (!content.getStaticize() || !file.exists()) {
						requester.render(content.getTemplate(), content, file);
						if (!content.getStaticize()) {
							content.setStaticize(true);
							this.getRepository().update(content);
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

			// 更新内容访问量
			final int id = content.getId();
			Servers.submit(new Runnable() {

				@Override
				public void run() {
					synchronized (("__cms_content_accesse_count_" + id).intern()) {
						T content = getRepository().get(id);
						if (content != null) {
							content.setAccesses(content.getAccesses() + 1);
							getRepository().update(content);
						}
					}
				}

			});
		}
	}

}
