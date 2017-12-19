package ars.module.cms.tags.content;

import java.util.Map;

import ars.invoke.Invokes;
import ars.invoke.request.Requester;
import ars.module.cms.tags.content.AbstractContentTag;

/**
 * 获取文章数量自定义标签
 * 
 * @author yongqiangwu
 * 
 */
public class CountTag extends AbstractContentTag {

	@Override
	protected Object execute() throws Exception {
		Map<String, Object> parameters = this.getParameters();
		if (parameters.isEmpty()) {
			return 0;
		}
		Requester requester = Invokes.getCurrentRequester();
		return requester.build("cms/content/count", parameters).execute();
	}

}
