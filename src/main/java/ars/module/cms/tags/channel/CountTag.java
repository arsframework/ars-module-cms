package ars.module.cms.tags.channel;

import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取栏目数量自定义标签
 *
 * @author wuyongqiang
 */
public class CountTag extends AbstractCmsTag {

    @Override
    protected Object execute() throws Exception {
        return this.getRequester().execute("cms/channel/count", this.getParameters());
    }

}
