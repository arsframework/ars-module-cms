package ars.module.cms.tags.category;

import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取类别数量自定义标签
 * 
 * @author yongqiangwu
 * 
 */
public class CountTag extends AbstractCmsTag {

	@Override
	protected Object execute() throws Exception {
		return this.getRequester().execute("cms/category/count", this.getParameters());
	}

}
