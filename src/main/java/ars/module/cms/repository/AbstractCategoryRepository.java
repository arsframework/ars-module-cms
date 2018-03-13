package ars.module.cms.repository;

import ars.module.cms.model.Category;
import ars.module.cms.repository.CategoryRepository;
import ars.database.hibernate.HibernateSimpleRepository;

/**
 * 内容类别数据操作抽象实现
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public abstract class AbstractCategoryRepository<T extends Category> extends HibernateSimpleRepository<T>
		implements CategoryRepository<T> {

}
