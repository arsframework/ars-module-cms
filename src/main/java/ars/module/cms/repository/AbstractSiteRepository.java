package ars.module.cms.repository;

import ars.module.cms.model.Site;
import ars.module.cms.repository.SiteRepository;
import ars.database.hibernate.HibernateSimpleRepository;

/**
 * 站点数据操作抽象实现
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public abstract class AbstractSiteRepository<T extends Site> extends HibernateSimpleRepository<T>
		implements SiteRepository<T> {

}
