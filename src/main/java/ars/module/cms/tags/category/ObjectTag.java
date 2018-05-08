package ars.module.cms.tags.category;

import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取单个类别自定义标签
 *
 * @author wuyongqiang
 */
public class ObjectTag extends AbstractCmsTag {

    @Override
    protected Object execute() throws Exception {
        return this.getRequester().execute("cms/category/object", this.getParameters());
    }

}
