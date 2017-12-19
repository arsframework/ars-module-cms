package ars.module.cms.tags.tag;

import ars.invoke.Invokes;
import ars.invoke.request.Requester;
import ars.module.cms.tags.Paging;
import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取文章标签分页自定义标签
 * 
 * @author yongqiangwu
 * 
 */
public class PagingTag extends AbstractCmsTag {

	@Override
	protected Object execute() throws Exception {
		Requester requester = Invokes.getCurrentRequester();
		Integer count = (Integer) requester.build("cms/tag/count", this.getParameters()).execute();
		return new Paging(count, this.getPage(), this.getSize());
	}

}
