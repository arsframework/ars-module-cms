package ars.module.cms.service;

import ars.invoke.local.Api;
import ars.module.cms.model.Category;
import ars.database.service.TreeService;
import ars.database.service.BasicService;

/**
 * 内容类别业务操作接口
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
@Api("cms/category")
public interface CategoryService<T extends Category> extends BasicService<T>, TreeService<T> {

}
