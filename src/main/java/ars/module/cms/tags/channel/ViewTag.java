package ars.module.cms.tags.channel;

import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取栏目视图
 *
 * @author wuyongqiang
 */
public class ViewTag extends AbstractCmsTag {

    @Override
    protected Object execute() throws Exception {
        return this.getRequester().execute("cms/channel/view", this.getParameters());
    }

}
