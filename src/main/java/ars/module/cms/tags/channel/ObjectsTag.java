package ars.module.cms.tags.channel;

import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取栏目列表自定义标签
 * 
 * @author yongqiangwu
 * 
 */
public class ObjectsTag extends AbstractCmsTag {

	@Override
	protected Object execute() throws Exception {
		return this.getRequester().execute("cms/channel/objects", this.getParameters());
	}

}
