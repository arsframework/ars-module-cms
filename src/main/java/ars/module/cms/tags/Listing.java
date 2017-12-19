package ars.module.cms.tags;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * 列表对象
 * 
 * @author yongqiangwu
 * 
 */
public class Listing implements Serializable {
	private static final long serialVersionUID = 1L;

	private Paging paging;
	private List<?> objects;

	public Listing(Paging paging) {
		this(paging, new ArrayList<Object>(0));
	}

	public Listing(Paging paging, List<?> objects) {
		if (paging == null) {
			throw new IllegalArgumentException("Illegal paging:" + paging);
		}
		if (objects == null) {
			throw new IllegalArgumentException("Illegal objects:" + objects);
		}
		this.paging = paging;
		this.objects = objects;
	}

	public Paging getPaging() {
		return paging;
	}

	public java.util.List<?> getObjects() {
		return objects;
	}

}
