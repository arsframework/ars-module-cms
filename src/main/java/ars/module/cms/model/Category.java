package ars.module.cms.model;

import ars.database.model.AbstractTreeModel;

/**
 * 内容类别数据模型
 * 
 * @author yongqiangwu
 *
 */
public class Category extends AbstractTreeModel<Category> {
	private static final long serialVersionUID = 1L;

	private String name; // 类别名称

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name == null ? super.toString() : this.name;
	}

}
