package ars.module.cms.tags.channel;

import ars.invoke.Invokes;
import ars.invoke.request.Requester;
import ars.module.cms.tags.Paging;
import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取栏目分页自定义标签
 * 
 * @author yongqiangwu
 * 
 */
public class PagingTag extends AbstractCmsTag {

	@Override
	protected Object execute() throws Exception {
		Requester requester = Invokes.getCurrentRequester();
		Integer count = (Integer) requester.build("cms/channel/count", this.getParameters()).execute();
		return new Paging(count, this.getPage(), this.getSize());
	}

}
