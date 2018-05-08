package ars.module.cms.service;

import java.util.List;

import ars.file.Describe;
import ars.invoke.local.Api;
import ars.invoke.request.Requester;
import ars.database.service.BasicService;
import ars.module.cms.model.Site;

/**
 * 站点业务操作接口
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
@Api("cms/site")
public interface SiteService<T extends Site> extends BasicService<T> {
    /**
     * 获取站点模板
     *
     * @param requester 请求对象
     * @return 文件描述对象树列表
     * @throws Exception 操作异常
     */
    @Api("templates")
    public List<Describe> templates(Requester requester) throws Exception;

}
