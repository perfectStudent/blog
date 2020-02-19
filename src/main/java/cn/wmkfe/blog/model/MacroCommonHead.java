package cn.wmkfe.blog.model;

public class MacroCommonHead {
    public String title;
    public String keywords="http://www.wmkfe.com/";
    public String description;
    public String preconnect;
    public String siteName;
    public String url;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreconnect() {
        return preconnect;
    }

    public void setPreconnect(String preconnect) {
        this.preconnect = preconnect;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
