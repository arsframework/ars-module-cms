package ars.module.cms.model;

import ars.database.model.AbstractModel;

/**
 * 站点数据模型
 * 
 * @author yongqiangwu
 *
 */
public class Site extends AbstractModel {
	private static final long serialVersionUID = 1L;

	private String code; // 站点编号
	private String name; // 站点名称
	private String logo; // 站点图标
	private String domain; // 站点域名
	private String template; // 首页模板
	private Boolean staticize = false; // 是否已静态化

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

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
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

	@Override
	public String toString() {
		return this.name == null ? super.toString() : this.name;
	}

}
