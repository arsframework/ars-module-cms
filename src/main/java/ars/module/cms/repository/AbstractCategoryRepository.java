package ars.module.cms.repository;

import ars.module.cms.model.Category;
import ars.database.hibernate.HibernateSimpleRepository;

/**
 * 内容类别数据操作抽象实现
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
public abstract class AbstractCategoryRepository<T extends Category> extends HibernateSimpleRepository<T>
    implements CategoryRepository<T> {

}
