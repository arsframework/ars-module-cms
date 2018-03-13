package ars.module.cms.tags.category;

import java.util.Map;
import java.util.List;

import ars.module.cms.tags.Paging;
import ars.module.cms.tags.Listing;
import ars.module.cms.tags.AbstractCmsTag;
import ars.invoke.channel.http.HttpRequester;

/**
 * 获取带分页类别列表自定义标签
 * 
 * @author yongqiangwu
 * 
 */
public class ListingTag extends AbstractCmsTag {

	@Override
	protected Object execute() throws Exception {
		HttpRequester requester = this.getRequester();
		Map<String, Object> parameters = this.getParameters();
		Integer count = (Integer) requester.execute("cms/category/count", parameters);
		Paging paging = new Paging(count, this.getPage(), this.getSize());
		List<?> objects = (List<?>) requester.execute("cms/category/objects", parameters);
		return new Listing(paging, objects);
	}

}