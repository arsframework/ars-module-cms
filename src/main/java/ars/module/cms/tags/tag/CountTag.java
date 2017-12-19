package ars.module.cms.tags.tag;

import ars.invoke.Invokes;
import ars.invoke.request.Requester;
import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取文章标签数量自定义标签
 * 
 * @author yongqiangwu
 * 
 */
public class CountTag extends AbstractCmsTag {

	@Override
	protected Object execute() throws Exception {
		Requester requester = Invokes.getCurrentRequester();
		return requester.build("cms/tag/count", this.getParameters()).execute();
	}

}
