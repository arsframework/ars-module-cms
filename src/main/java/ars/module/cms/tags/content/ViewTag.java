package ars.module.cms.tags.content;

import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取内容视图
 *
 * @author wuyongqiang
 */
public class ViewTag extends AbstractCmsTag {

    @Override
    protected Object execute() throws Exception {
        return this.getRequester().execute("cms/content/view", this.getParameters());
    }

}
