package ars.module.cms.tags.category;

import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取类别树列表自定义标签
 * 
 * @author yongqiangwu
 * 
 */
public class TreesTag extends AbstractCmsTag {

	@Override
	protected Object execute() throws Exception {
		return this.getRequester().execute("cms/category/trees", this.getParameters());
	}

}
