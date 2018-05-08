package ars.module.cms.tags.content;

import java.util.Map;
import java.util.List;

import ars.module.cms.tags.Paging;
import ars.module.cms.tags.Listing;
import ars.module.cms.tags.AbstractCmsTag;
import ars.database.repository.Query;
import ars.invoke.channel.http.HttpRequester;

/**
 * 获取带分页文章列表自定义标签
 *
 * @author wuyongqiang
 */
public class ListingTag extends AbstractCmsTag {

    @Override
    protected Object execute() throws Exception {
        HttpRequester requester = this.getRequester();
        Map<String, Object> parameters = this.getParameters();
        Integer page = (Integer) parameters.remove(Query.PAGE);
        Integer size = (Integer) parameters.remove(Query.SIZE);
        if (parameters.isEmpty()) {
            return new Listing(new Paging(0, this.getPage(), this.getSize()));
        }
        Integer count = (Integer) requester.execute("cms/content/count", parameters);
        Paging paging = new Paging(count, this.getPage(), this.getSize());
        if (page != null) {
            parameters.put(Query.PAGE, page);
        }
        if (size != null) {
            parameters.put(Query.SIZE, size);
        }
        List<?> objects = (List<?>) requester.execute("cms/content/objects", parameters);
        return new Listing(paging, objects);
    }

}
