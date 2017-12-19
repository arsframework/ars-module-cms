package ars.module.cms.tags.content;

import java.util.Map;

import ars.invoke.Invokes;
import ars.invoke.request.Requester;
import ars.module.cms.tags.content.AbstractContentTag;

/**
 * 获取单个文章自定义标签
 * 
 * @author yongqiangwu
 * 
 */
public class ObjectTag extends AbstractContentTag {

	@Override
	protected Object execute() throws Exception {
		Map<String, Object> parameters = this.getParameters();
		if (parameters.isEmpty()) {
			return null;
		}
		Requester requester = Invokes.getCurrentRequester();
		return requester.build("cms/content/object", parameters).execute();
	}

}
