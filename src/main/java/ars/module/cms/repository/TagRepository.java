package ars.module.cms.repository;

import ars.module.cms.model.Tag;
import ars.database.repository.Repository;

/**
 * 文章标签数据操作接口
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public interface TagRepository<T extends Tag> extends Repository<T> {

}
