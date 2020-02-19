package cn.wmkfe.blog.model;

public class Statistics {
    public int articleCount;
    public int commentCount;
    public int viewCount;

    @Override
    public String toString() {
        return "Statistics{" +
                "articleCount=" + articleCount +
                ", commentCount=" + commentCount +
                ", viewCount=" + viewCount +
                '}';
    }

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
