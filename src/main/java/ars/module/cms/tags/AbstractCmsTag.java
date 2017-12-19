package ars.module.cms.tags;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;

import ars.util.Strings;
import ars.database.repository.Query;
import ars.invoke.channel.http.tags.AbstractTag;

/**
 * 内容管理自定义标签抽象实现
 * 
 * @author yongqiangwu
 * 
 */
public abstract class AbstractCmsTag extends AbstractTag {
	private Object param; // 参数，支持键/值对形式或字符串形式。如果为字符串则多个条件使用“,”号隔开；如果参数值为多个值，则使用“[]”包围，并且每个值使用“,”号隔开。
	private int page; // 页码
	private int size; // 页面大小
	private String order = "order"; // 排序（多个排序属性之间使用“,”号隔开）
	private String condition; // 高级查询

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param instanceof String ? ((String) param).trim() : param;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if (page < 0) {
			throw new IllegalArgumentException("Illegal page:" + page);
		}
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("Illegal size:" + size);
		}
		this.size = size;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order.trim();
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition.trim();
	}

	/**
	 * 获取请求参数
	 * 
	 * @return 参数键/值对
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> getParameters() {
		Map<String, Object> parameters = this.param instanceof Map ? (Map<String, Object>) this.param
				: this.param instanceof String ? Strings.toMap((String) this.param) : new HashMap<String, Object>();
		parameters.put("active", true);
		if (this.page > 0) {
			parameters.put(Query.PAGE, this.page);
		}
		if (this.size > 0) {
			parameters.put(Query.SIZE, this.size);
		}
		if (!Strings.isEmpty(this.order)) {
			List<String> orders = new LinkedList<String>();
			for (String property : Strings.split(this.order, ',')) {
				property = property.trim();
				if (property.isEmpty()) {
					continue;
				}
				orders.add(property);
			}
			parameters.put(Query.ORDER, orders);
		}
		if (!Strings.isEmpty(this.condition)) {
			parameters.put(Query.CONDITION, this.condition);
		}
		return parameters;
	}

}
