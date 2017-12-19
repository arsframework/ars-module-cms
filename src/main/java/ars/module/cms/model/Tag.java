package ars.module.cms.model;

import ars.database.model.AbstractModel;

/**
 * 文章标签数据模型
 * 
 * @author yongqiangwu
 * 
 */
public class Tag extends AbstractModel {
	private static final long serialVersionUID = 1L;

	private String name; // 标签名称

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
