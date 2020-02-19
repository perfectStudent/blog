package cn.wmkfe.blog.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

public class Article {
    private String id;
    @NotEmpty(message = "文章标题不能为空")
    private String title;                  //标题
    @NotEmpty(message = "文章简介不能为空")
    private String introduction;           //简介
    @NotEmpty(message = "文章封面不能为空")
    private String firstPicture;           //封面
    @NotNull(message = "置顶必须选择其一")
    private Boolean putTop;                //置顶
    @NotNull(message = "状态必须选择其一")
    private Boolean status;                //状态
    @NotNull(message = "开启评论必须选择其一")
    private Boolean commentable;           //开启评论

    private Integer viewCount;             //访问量

    private Date createTime;                //创建时间

    private Date updateTime;                //更新时间
    @NotNull(message = "分类必选其一")
    private Integer typeId;                 //分类关联

    private Integer userId;                 //用户关联
    @NotEmpty(message = "文章内容不能为空")
    private String content;                 //内容

    private String contentMd;              //编辑器内容

    private Type type;                      //分类

    private  User user;                     //用户

    private List<TagMap> tagMapList;        //中间表

    private List<Tag> tagList;              //多标签

    private List<Comment> commentList;     //评论

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<TagMap> getTagMapList() {
        return tagMapList;
    }

    public void setTagMapList(List<TagMap> tagMapList) {
        this.tagMapList = tagMapList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture == null ? null : firstPicture.trim();
    }

    public Boolean getPutTop() {
        return putTop;
    }

    public void setPutTop(Boolean putTop) {
        this.putTop = putTop;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getCommentable() {
        return commentable;
    }

    public void setCommentable(Boolean commentable) {
        this.commentable = commentable;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getContentMd() {
        return contentMd;
    }

    public void setContentMd(String contentMd) {
        this.contentMd = contentMd;
    }
}