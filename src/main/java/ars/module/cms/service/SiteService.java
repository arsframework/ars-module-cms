package ars.module.cms.service;

import ars.invoke.local.Api;
import ars.module.cms.model.Site;
import ars.database.service.BasicService;

/**
 * 站点业务操作接口
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
@Api("cms/site")
public interface SiteService<T extends Site> extends BasicService<T> {

}
