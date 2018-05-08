package ars.module.cms.repository;

import ars.module.cms.model.Content;
import ars.database.hibernate.HibernateSimpleRepository;

/**
 * 文章数据操作抽象实现
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
public abstract class AbstractContentRepository<T extends Content> extends HibernateSimpleRepository<T>
    implements ContentRepository<T> {

}
