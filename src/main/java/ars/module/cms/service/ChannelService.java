package ars.module.cms.service;

import java.util.List;

import ars.file.Describe;
import ars.invoke.local.Api;
import ars.invoke.request.Requester;
import ars.invoke.channel.http.HttpRequester;
import ars.module.cms.model.Channel;
import ars.database.service.TreeService;
import ars.database.service.BasicService;

/**
 * 栏目业务操作接口
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
@Api("cms/channel")
public interface ChannelService<T extends Channel> extends BasicService<T>, TreeService<T> {
    /**
     * 获取栏目模板
     *
     * @param requester 请求对象
     * @return 文件描述对象树列表
     * @throws Exception 操作异常
     */
    @Api("templates")
    public List<Describe> templates(Requester requester) throws Exception;

    /**
     * 获取栏目视图
     *
     * @param requester 请求对象
     * @return 栏目视图
     * @throws Exception 操作异常
     */
    @Api("view")
    public String view(HttpRequester requester) throws Exception;

    /**
     * 渲染栏目视图
     *
     * @param requester 请求对象
     * @throws Exception 操作异常
     */
    @Api("render")
    public void render(HttpRequester requester) throws Exception;

}
