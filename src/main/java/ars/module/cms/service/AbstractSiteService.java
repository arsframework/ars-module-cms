package ars.module.cms.service;

import java.util.List;

import ars.util.Strings;
import ars.file.Describe;
import ars.file.disk.DiskOperator;
import ars.module.cms.model.Site;
import ars.module.cms.service.SiteService;
import ars.invoke.request.Requester;
import ars.database.service.StandardGeneralService;

/**
 * 站点业务操作抽象实现
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public abstract class AbstractSiteService<T extends Site> extends StandardGeneralService<T> implements SiteService<T> {
	private String templateDirectory = Strings.TEMP_PATH; // 模板文件目录

	public String getTemplateDirectory() {
		return templateDirectory;
	}

	public void setTemplateDirectory(String templateDirectory) {
		this.templateDirectory = Strings.getRealPath(templateDirectory);
	}

	@Override
	public List<Describe> templates(Requester requester) throws Exception {
		return new DiskOperator(this.templateDirectory).trees(null, requester.getParameters());
	}

}
