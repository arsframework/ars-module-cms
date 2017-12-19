package ars.module.cms.repository;

import ars.module.cms.model.Tag;
import ars.module.cms.repository.TagRepository;
import ars.database.hibernate.HibernateSimpleRepository;

/**
 * 文章标签数据操作抽象实现
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public abstract class AbstractTagRepository<T extends Tag> extends HibernateSimpleRepository<T>
		implements TagRepository<T> {

}
