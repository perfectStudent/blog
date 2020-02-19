package cn.wmkfe.blog.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

public class Link {
    private Long id;
    @NotEmpty(message = "网站名称不能为空")
    private String websiteName;
    @NotEmpty(message = "链接地址不能为空")
    private String urlAddress;
    @NotEmpty(message = "友联图片不能为空")
    private String linkLogo;

    public String getLinkLogo() {
        return linkLogo;
    }

    public void setLinkLogo(String linkLogo) {
        this.linkLogo = linkLogo;
    }

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", websiteName='" + websiteName + '\'' +
                ", urlAddress='" + urlAddress + '\'' +
                ", linkLogo='" + linkLogo + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
