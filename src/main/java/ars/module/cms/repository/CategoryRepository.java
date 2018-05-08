package ars.module.cms.repository;

import ars.module.cms.model.Category;
import ars.database.repository.Repository;

/**
 * 内容类别数据操作接口
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
public interface CategoryRepository<T extends Category> extends Repository<T> {

}
