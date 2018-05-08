package ars.module.cms.tags;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * 列表对象
 *
 * @author wuyongqiang
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
            throw new IllegalArgumentException("Paging must not be null");
        }
        if (objects == null) {
            throw new IllegalArgumentException("Objects must not be null");
        }
        this.paging = paging;
        this.objects = objects;
    }

    public Paging getPaging() {
        return paging;
    }

    public List<?> getObjects() {
        return objects;
    }

}
