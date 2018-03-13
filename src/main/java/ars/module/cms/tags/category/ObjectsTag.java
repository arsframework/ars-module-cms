package ars.module.cms.tags.category;

import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取类别列表自定义标签
 * 
 * @author yongqiangwu
 * 
 */
public class ObjectsTag extends AbstractCmsTag {

	@Override
	protected Object execute() throws Exception {
		return this.getRequester().execute("cms/category/objects", this.getParameters());
	}

}
