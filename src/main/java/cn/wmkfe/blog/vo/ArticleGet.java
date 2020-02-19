package cn.wmkfe.blog.vo;

public class ArticleGet {
    private String title;
    private Integer typeId;
    private Integer tagId;
    private String  archivesDate;

    public void setArchivesDate(String archivesDate) {
        this.archivesDate = archivesDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "ArticleGet{" +
                "title='" + title + '\'' +
                ", typeId=" + typeId +
                ", tagId=" + tagId +
                ", archivesDate='" + archivesDate + '\'' +
                '}';
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}
