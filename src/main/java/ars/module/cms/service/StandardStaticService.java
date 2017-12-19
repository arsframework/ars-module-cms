package ars.module.cms.service;

import ars.file.Operator;
import ars.file.DiskOperator;
import ars.file.StandardDocumentManager;
import ars.module.cms.service.StaticService;

/**
 * 静态资源业务操作标准实现
 * 
 * @author yongqiangwu
 * 
 */
public class StandardStaticService extends StandardDocumentManager implements StaticService {

	public StandardStaticService(Operator operator) {
		super(operator);
	}

	public StandardStaticService(String workingDirectory) {
		super(new DiskOperator(workingDirectory));
	}

}
