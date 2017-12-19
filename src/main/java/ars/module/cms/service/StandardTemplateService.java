package ars.module.cms.service;

import ars.file.Operator;
import ars.file.DiskOperator;
import ars.file.StandardDocumentManager;
import ars.module.cms.service.TemplateService;

/**
 * 模板资源业务操作标准实现
 * 
 * @author yongqiangwu
 * 
 */
public class StandardTemplateService extends StandardDocumentManager implements TemplateService {

	public StandardTemplateService(Operator operator) {
		super(operator);
	}

	public StandardTemplateService(String workingDirectory) {
		super(new DiskOperator(workingDirectory));
	}

}
