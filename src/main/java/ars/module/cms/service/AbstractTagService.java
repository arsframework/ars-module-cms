package ars.module.cms.service;

import ars.module.cms.model.Tag;
import ars.module.cms.service.TagService;
import ars.database.service.StandardGeneralService;

/**
 * 文章标签业务操作抽象实现
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public abstract class AbstractTagService<T extends Tag> extends StandardGeneralService<T> implements TagService<T> {

}
