package ars.module.cms.tags.content;

import java.util.Map;
import java.util.List;

import ars.invoke.Invokes;
import ars.invoke.request.Requester;
import ars.module.cms.tags.Paging;
import ars.module.cms.tags.Listing;
import ars.module.cms.tags.content.AbstractContentTag;

/**
 * 获取带分页文章列表自定义标签
 * 
 * @author yongqiangwu
 * 
 */
public class ListingTag extends AbstractContentTag {

	@Override
	protected Object execute() throws Exception {
		Requester requester = Invokes.getCurrentRequester();
		Map<String, Object> parameters = this.getParameters();
		if (parameters.isEmpty()) {
			return new Listing(new Paging(0, this.getPage(), this.getSize()));
		}
		Integer count = (Integer) requester.build("cms/content/count", parameters).execute();
		Paging paging = new Paging(count, this.getPage(), this.getSize());
		List<?> objects = (List<?>) requester.build("cms/content/objects", parameters).execute();
		return new Listing(paging, objects);
	}

}
