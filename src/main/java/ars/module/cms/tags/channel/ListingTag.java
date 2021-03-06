package ars.module.cms.tags.channel;

import java.util.Map;
import java.util.List;

import ars.module.cms.tags.Paging;
import ars.module.cms.tags.Listing;
import ars.module.cms.tags.AbstractCmsTag;
import ars.database.repository.Query;
import ars.invoke.channel.http.HttpRequester;

/**
 * 获取带分页栏目列表自定义标签
 * 
 * @author yongqiangwu
 * 
 */
public class ListingTag extends AbstractCmsTag {

	@Override
	protected Object execute() throws Exception {
		HttpRequester requester = this.getRequester();
		Map<String, Object> parameters = this.getParameters();
		Integer page = (Integer) parameters.remove(Query.PAGE);
		Integer size = (Integer) parameters.remove(Query.SIZE);
		Integer count = (Integer) requester.execute("cms/channel/count", parameters);
		Paging paging = new Paging(count, this.getPage(), this.getSize());
		if (page != null) {
			parameters.put(Query.PAGE, page);
		}
		if (size != null) {
			parameters.put(Query.SIZE, size);
		}
		List<?> objects = (List<?>) requester.execute("cms/channel/objects", parameters);
		return new Listing(paging, objects);
	}

}
