package ars.module.cms.model;

import java.util.Set;
import java.util.HashSet;

import ars.invoke.Invokes;
import ars.invoke.request.Requester;
import ars.invoke.channel.http.HttpRequester;
import ars.database.model.AbstractTreeModel;
import ars.module.people.model.Group;

/**
 * 内容栏目数据模型
 * 
 * @author yongqiangwu
 * 
 */
public class Channel extends AbstractTreeModel<Channel> {
	private static final long serialVersionUID = 1L;

	private String code; // 栏目编号
	private String name; // 栏目名称
	private String logo; // 栏目图片
	private String link; // 外部链接
	private String template; // 栏目模板
	private Set<Group> groups = new HashSet<Group>(0); // 所属部门

	/**
	 * 获取访问路径
	 * 
	 * @return 访问路径
	 */
	public String getUrl() {
		if (this.link != null) {
			return this.link;
		}
		Requester requester = Invokes.getCurrentRequester();
		StringBuilder url = new StringBuilder();
		if (requester instanceof HttpRequester) {
			url.append(((HttpRequester) requester).getHttpServletRequest().getContextPath());
		}
		return url.append("/cms/channel/view?id=").append(this.getId()).toString();
	}

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

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	@Override
	public String toString() {
		return this.name == null ? super.toString() : this.name;
	}

}
