package ars.module.cms.repository;

import ars.module.cms.model.Content;
import ars.database.repository.Repository;

/**
 * 文章数据操作接口
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public interface ContentRepository<T extends Content> extends Repository<T> {

}
