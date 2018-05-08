package ars.module.cms.repository;

import ars.module.cms.model.Channel;
import ars.database.hibernate.HibernateSimpleRepository;

/**
 * 栏目数据操作抽象实现
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
public abstract class AbstractChannelRepository<T extends Channel> extends HibernateSimpleRepository<T>
    implements ChannelRepository<T> {

}
