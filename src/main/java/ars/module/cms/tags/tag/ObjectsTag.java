package ars.module.cms.tags.tag;

import ars.invoke.Invokes;
import ars.invoke.request.Requester;
import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取文章标签列表自定义标签
 * 
 * @author yongqiangwu
 * 
 */
public class ObjectsTag extends AbstractCmsTag {

	@Override
	protected Object execute() throws Exception {
		Requester requester = Invokes.getCurrentRequester();
		return requester.build("cms/tag/objects", this.getParameters()).execute();
	}

}
