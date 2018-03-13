package ars.module.cms.service;

import ars.module.cms.model.Site;
import ars.module.cms.service.SiteService;
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

}
