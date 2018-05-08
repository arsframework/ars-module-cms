package ars.module.cms.tags.channel;

import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取单个栏目自定义标签
 *
 * @author wuyongqiang
 */
public class ObjectTag extends AbstractCmsTag {

    @Override
    protected Object execute() throws Exception {
        return this.getRequester().execute("cms/channel/object", this.getParameters());
    }

}
