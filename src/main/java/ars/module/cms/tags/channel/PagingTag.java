package ars.module.cms.tags.channel;

import ars.module.cms.tags.Paging;
import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取栏目分页自定义标签
 *
 * @author wuyongqiang
 */
public class PagingTag extends AbstractCmsTag {

    @Override
    protected Object execute() throws Exception {
        Integer count = (Integer) this.getRequester().execute("cms/channel/count", this.getParameters());
        return new Paging(count, this.getPage(), this.getSize());
    }

}
