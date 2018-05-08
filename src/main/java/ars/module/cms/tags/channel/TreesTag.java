package ars.module.cms.tags.channel;

import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取栏目树列表自定义标签
 *
 * @author wuyongqiang
 */
public class TreesTag extends AbstractCmsTag {

    @Override
    protected Object execute() throws Exception {
        return this.getRequester().execute("cms/channel/trees", this.getParameters());
    }

}
