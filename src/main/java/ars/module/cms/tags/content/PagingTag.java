package ars.module.cms.tags.content;

import java.util.Map;

import ars.invoke.Invokes;
import ars.invoke.request.Requester;
import ars.module.cms.tags.Paging;
import ars.module.cms.tags.content.AbstractContentTag;

/**
 * 获取文章分页自定义标签
 * 
 * @author yongqiangwu
 * 
 */
public class PagingTag extends AbstractContentTag {

	@Override
	protected Object execute() throws Exception {
		Map<String, Object> parameters = this.getParameters();
		if (parameters.isEmpty()) {
			new Paging(0, this.getPage(), this.getSize());
		}
		Requester requester = Invokes.getCurrentRequester();
		Integer count = (Integer) requester.build("cms/content/count", parameters).execute();
		return new Paging(count, this.getPage(), this.getSize());
	}

}
