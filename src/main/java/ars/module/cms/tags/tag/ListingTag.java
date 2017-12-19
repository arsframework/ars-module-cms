package ars.module.cms.tags.tag;

import java.util.Map;
import java.util.List;

import ars.invoke.Invokes;
import ars.invoke.request.Requester;
import ars.module.cms.tags.Listing;
import ars.module.cms.tags.Paging;
import ars.module.cms.tags.AbstractCmsTag;

/**
 * 获取带分页文章标签列表自定义标签
 * 
 * @author yongqiangwu
 * 
 */
public class ListingTag extends AbstractCmsTag {

	@Override
	protected Object execute() throws Exception {
		Requester requester = Invokes.getCurrentRequester();
		Map<String, Object> parameters = this.getParameters();
		Integer count = (Integer) requester.build("cms/tag/count", parameters).execute();
		Paging paging = new Paging(count, this.getPage(), this.getSize());
		List<?> objects = (List<?>) requester.build("cms/tag/objects", parameters).execute();
		return new Listing(paging, objects);
	}

}
