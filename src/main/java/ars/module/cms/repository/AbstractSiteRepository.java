package ars.module.cms.repository;

import ars.module.cms.model.Site;
import ars.database.hibernate.HibernateSimpleRepository;

/**
 * 站点数据操作抽象实现
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
public abstract class AbstractSiteRepository<T extends Site> extends HibernateSimpleRepository<T>
    implements SiteRepository<T> {

}
