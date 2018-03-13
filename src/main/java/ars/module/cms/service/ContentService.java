package ars.module.cms.service;

import java.util.List;

import ars.file.Describe;
import ars.invoke.local.Api;
import ars.invoke.request.Requester;
import ars.invoke.channel.http.HttpRequester;
import ars.module.cms.model.Content;
import ars.database.service.BasicService;

/**
 * 文章业务操作接口
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
@Api("cms/content")
public interface ContentService<T extends Content> extends BasicService<T> {
	/**
	 * 获取文章模板
	 * 
	 * @param requester
	 *            请求对象
	 * @return 文件描述对象树列表
	 * @throws Exception
	 *             操作异常
	 */
	@Api("templates")
	public List<Describe> templates(Requester requester) throws Exception;

	/**
	 * 获取文章视图
	 * 
	 * @param requester
	 *            请求对象
	 * @return 文章视图
	 * @throws Exception
	 *             操作异常
	 */
	@Api("view")
	public String view(HttpRequester requester) throws Exception;

	/**
	 * 渲染文章视图
	 * 
	 * @param requester
	 *            请求对象
	 * @throws Exception
	 *             操作异常
	 */
	@Api("render")
	public void render(HttpRequester requester) throws Exception;

}
