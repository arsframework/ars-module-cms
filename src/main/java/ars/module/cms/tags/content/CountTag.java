package ars.module.cms.tags.content;

import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取文章数量自定义标签
 *
 * @author wuyongqiang
 */
public class CountTag extends AbstractCmsTag {

    @Override
    protected Object execute() throws Exception {
        return this.getRequester().execute("cms/content/count", this.getParameters());
    }

}
