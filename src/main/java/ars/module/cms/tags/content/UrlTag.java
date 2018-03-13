package ars.module.cms.tags.content;

import ars.module.cms.model.Content;
import ars.invoke.channel.http.tags.AbstractTag;

/**
 * 获取内容资源访问地址
 * 
 * @author yongqiangwu
 *
 */
public class UrlTag extends AbstractTag {
	private Content entity;

	public Content getEntity() {
		return entity;
	}

	public void setEntity(Content entity) {
		this.entity = entity;
	}

	@Override
	protected Object execute() throws Exception {
		if (this.entity.getLink() != null) {
			return this.entity.getLink();
		}
		String context = this.getRequester().getHttpServletRequest().getContextPath();
		StringBuilder url = new StringBuilder();
		if (context != null && !context.isEmpty()) {
			url.append(context);
		}
		return url.append("/cms/content/render?id=").append(this.entity.getId()).toString();
	}

}
