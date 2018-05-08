package ars.module.cms.model;

import java.util.Set;
import java.util.HashSet;

import ars.database.model.AbstractTreeModel;

/**
 * 内容栏目数据模型
 *
 * @author wuyongqiang
 */
public class Channel extends AbstractTreeModel<Channel> {
    private static final long serialVersionUID = 1L;

    private String code; // 栏目编号
    private String name; // 栏目名称
    private String logo; // 栏目图片
    private String link; // 外部链接
    private String template; // 栏目模板
    private Boolean staticize = false; // 是否已静态化
    private Set<Site> sites = new HashSet<Site>(0); // 所属站点

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Boolean getStaticize() {
        return staticize;
    }

    public void setStaticize(Boolean staticize) {
        this.staticize = staticize;
    }

    public Set<Site> getSites() {
        return sites;
    }

    public void setSites(Set<Site> sites) {
        this.sites = sites;
    }

    @Override
    public String toString() {
        return this.name == null ? super.toString() : this.name;
    }

}
