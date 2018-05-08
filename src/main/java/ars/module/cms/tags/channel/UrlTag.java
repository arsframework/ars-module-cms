package ars.module.cms.tags.channel;

import ars.module.cms.model.Channel;
import ars.invoke.channel.http.tags.AbstractTag;

/**
 * 获取栏目资源访问地址
 *
 * @author wuyongqiang
 */
public class UrlTag extends AbstractTag {
    private Channel entity;

    public Channel getEntity() {
        return entity;
    }

    public void setEntity(Channel entity) {
        this.entity = entity;
    }

    @Override
    protected Object execute() throws Exception {
        if (this.entity.getLink() != null) {
            return this.entity.getLink();
        }
        String context = this.getRequester().getHttpServletRequest().getContextPath();
        StringBuilder url = new StringBuilder();
        if (context != null && !context.isEmpty()) {
            url.append(context);
        }
        return url.append("/cms/channel/render?id=").append(this.entity.getId()).toString();
    }

}
