package ars.module.cms.service;

import ars.invoke.request.Requester;
import ars.invoke.request.ParameterInvalidException;
import ars.module.cms.model.Category;
import ars.database.repository.Query;
import ars.database.service.StandardGeneralService;

/**
 * 内容类别业务操作抽象实现
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
public abstract class AbstractCategoryService<T extends Category> extends StandardGeneralService<T>
    implements CategoryService<T> {

    @Override
    public void initObject(Requester requester, T entity) {
        super.initObject(requester, entity);
        Category parent = entity.getParent();
        Query<T> query = this.getRepository().query().ne("id", entity.getId()).eq("name", entity.getName());
        if (parent == null) {
            query.empty("parent");
        } else {
            query.eq("parent", parent);
        }
        if (query.count() > 0) {
            throw new ParameterInvalidException("name", "exist");
        }
    }

}
