package ars.module.cms.tags.content;

import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取单个文章自定义标签
 *
 * @author wuyongqiang
 */
public class ObjectTag extends AbstractCmsTag {

    @Override
    protected Object execute() throws Exception {
        return this.getRequester().execute("cms/content/object", this.getParameters());
    }

}
