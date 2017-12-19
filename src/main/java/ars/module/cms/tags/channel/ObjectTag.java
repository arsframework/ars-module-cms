package ars.module.cms.tags.channel;

import ars.invoke.Invokes;
import ars.invoke.request.Requester;
import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取单个栏目自定义标签
 * 
 * @author yongqiangwu
 * 
 */
public class ObjectTag extends AbstractCmsTag {

	@Override
	protected Object execute() throws Exception {
		Requester requester = Invokes.getCurrentRequester();
		return requester.build("cms/channel/object", this.getParameters()).execute();
	}

}
