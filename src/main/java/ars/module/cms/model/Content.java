package ars.module.cms.model;

import java.util.Set;
import java.util.Date;
import java.util.HashSet;

import ars.invoke.Invokes;
import ars.invoke.request.Requester;
import ars.invoke.channel.http.HttpRequester;
import ars.database.model.AbstractModel;
import ars.module.system.model.Attachment;

/**
 * 内容数据模型
 * 
 * @author yongqiangwu
 * 
 */
public class Content extends AbstractModel {
	private static final long serialVersionUID = 1L;

	private String title; // 文章标题
	private String tag; // 文章标签
	private String txt; // 文章正文
	private String logo; // 标题图
	private String link; // 外部链接
	private String author; // 文章作者
	private String origin; // 文章来源
	private String template; // 文章模板
	private Boolean top = false; // 是否置顶
	private Boolean focus = false; // 是否焦点
	private Boolean recommend = false; // 是否推荐
	private Integer accesses = 0; // 访问次数
	private Date released = new Date(); // 发布日期
	private Set<Channel> channels = new HashSet<Channel>(0); // 栏目集合
	private Set<Attachment> attachments = new HashSet<Attachment>(0); // 附件集合

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
		return url.append("/cms/content/view?id=").append(this.getId()).toString();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Boolean getTop() {
		return top;
	}

	public void setTop(Boolean top) {
		this.top = top;
	}

	public Boolean getFocus() {
		return focus;
	}

	public void setFocus(Boolean focus) {
		this.focus = focus;
	}

	public Boolean getRecommend() {
		return recommend;
	}

	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}

	public Integer getAccesses() {
		return accesses;
	}

	public void setAccesses(Integer accesses) {
		this.accesses = accesses;
	}

	public Date getReleased() {
		return released;
	}

	public void setReleased(Date released) {
		this.released = released;
	}

	public Set<Channel> getChannels() {
		return channels;
	}

	public void setChannels(Set<Channel> channels) {
		this.channels = channels;
	}

	public Set<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}

	@Override
	public String toString() {
		return this.title == null ? super.toString() : this.title;
	}

}
