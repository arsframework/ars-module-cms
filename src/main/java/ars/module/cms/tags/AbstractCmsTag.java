package ars.module.cms.tags;

import java.util.Map;
import java.util.List;
import java.util.LinkedList;

import ars.util.Strings;
import ars.database.repository.Query;
import ars.invoke.channel.http.tags.ResourceTag;

/**
 * 内容管理自定义标签抽象实现
 *
 * @author wuyongqiang
 */
public abstract class AbstractCmsTag extends ResourceTag {
    private int page; // 页码
    private int size; // 页面大小
    private String order; // 排序（多个排序属性之间使用“,”号隔开）
    private String condition; // 高级查询

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page < 0) {
            throw new IllegalArgumentException("Page must not be less than 0");
        }
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size must not be less than 0");
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

    @Override
    protected Map<String, Object> getParameters() {
        Map<String, Object> parameters = super.getParameters();
        parameters.put("active", true);
        if (this.page > 0) {
            parameters.put(Query.PAGE, this.page);
        }
        if (this.size > 0) {
            parameters.put(Query.SIZE, this.size);
        }
        if (!Strings.isBlank(this.order)) {
            List<String> orders = new LinkedList<String>();
            for (String property : Strings.split(this.order, ',')) {
                property = property.trim();
                if (Strings.isBlank(property)) {
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
