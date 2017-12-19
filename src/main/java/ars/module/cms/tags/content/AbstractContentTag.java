package ars.module.cms.tags.content;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ArrayList;

import ars.invoke.Invokes;
import ars.invoke.request.Requester;
import ars.module.cms.model.Channel;
import ars.module.cms.tags.AbstractCmsTag;

/**
 * 文章自定义标签抽象实现
 * 
 * @author yongqiangwu
 * 
 */
public abstract class AbstractContentTag extends AbstractCmsTag {
	private Map<String, Object> parameters;

	public AbstractContentTag() {
		this.setOrder("top,-order,-released");
	}

	@Override
	protected Map<String, Object> getParameters() {
		if (this.parameters == null) {
			Map<String, Object> parameters = super.getParameters();
			Map<String, Object> channelParameters = new HashMap<String, Object>(parameters.size());
			Map<String, Object> contentParameters = new HashMap<String, Object>(parameters.size());
			String prefix = "channels.";
			for (Entry<String, Object> entry : parameters.entrySet()) {
				String key = entry.getKey();
				if (key.startsWith(prefix)) {
					channelParameters.put(key.substring(prefix.length()), entry.getValue());
				} else {
					contentParameters.put(key, entry.getValue());
				}
			}
			channelParameters.put("active", true);
			Requester requester = Invokes.getCurrentRequester();
			List<?> channels = (List<?>) requester.build("cms/channel/objects", channelParameters).execute();
			if (channels.isEmpty()) {
				return new HashMap<String, Object>(0);
			}
			List<Integer> cids = new ArrayList<Integer>(channels.size());
			for (int i = 0; i < channels.size(); i++) {
				cids.add(((Channel) channels.get(i)).getId());
			}
			contentParameters.put("channels.id__in", cids);
			this.parameters = contentParameters;
		}
		return this.parameters;
	}

}
