package ars.module.cms.service;

import ars.invoke.local.Api;
import ars.module.cms.model.Tag;
import ars.database.service.UpdateService;
import ars.database.service.SearchService;

/**
 * 文章标签业务操作接口
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
@Api("cms/tag")
public interface TagService<T extends Tag> extends UpdateService<T>, SearchService<T> {

}
