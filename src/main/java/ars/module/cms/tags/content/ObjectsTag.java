package ars.module.cms.tags.content;

import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取文章列表自定义标签
 *
 * @author wuyongqiang
 */
public class ObjectsTag extends AbstractCmsTag {

    @Override
    protected Object execute() throws Exception {
        return this.getRequester().execute("cms/content/objects", this.getParameters());
    }

}
