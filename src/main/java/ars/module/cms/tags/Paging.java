package ars.module.cms.tags;

import java.io.Serializable;

import ars.util.Beans;

/**
 * 分页对象
 * 
 * @author yongqiangwu
 * 
 */
public class Paging implements Serializable {
	private static final long serialVersionUID = 1L;

	private int page; // 当前页码
	private int size; // 页面大小
	private int total; // 页码总数
	private int count; // 数据总数

	public Paging(int count, int page, int size) {
		if (count < 0) {
			throw new IllegalArgumentException("Illegal count:" + count);
		}
		if (page < 0) {
			throw new IllegalArgumentException("Illegal page:" + page);
		}
		if (size < 0) {
			throw new IllegalArgumentException("Illegal size:" + size);
		}
		this.page = page;
		this.size = size;
		this.count = count;
		if (count > 0 && size > 0) {
			this.total = (int) Math.ceil((double) count / (double) size);
		}
		if (this.page > this.total) {
			this.page = this.total;
		}
	}

	public int getPage() {
		return page;
	}

	public int getSize() {
		return size;
	}

	public int getTotal() {
		return total;
	}

	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return Beans.getValues(this).toString();
	}

}
