package com.elune.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class TopicEntity implements Serializable {
    private Long id;

    /**
     * 所属频道
     */
    private Integer cid;

    /**
     * 话题标题
     */
    private String title;

    /**
     * 作者用户名
     */
    private String author;

    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * 是否置顶 0 - 否 1 - 是
     */
    private Byte isPinned;

    /**
     * 是否精华帖 0 - 否 1 - 是
     */
    private Byte isEssence;

    /**
     * 阅读数
     */
    private Integer viewsCount;

    /**
     * 点赞数
     */
    private Integer upvotesCount;

    /**
     * 点踩数
     */
    private Integer downvotesCount;

    /**
     * 收藏数
     */
    private Integer favoritesCount;

    /**
     * 评论回复数
     */
    private Integer postsCount;

    /**
     * 帖子状态 0 - 删除 1 - 正常
     */
    private Byte status;

    /**
     * 评论状态 0 - 锁定禁止评论 1 - 正常
     */
    private Byte comment_status;

    /**
     * 创建时间
     */
    private Integer create_time;

    /**
     * 更新时间
     */
    private Integer update_time;

    /**
     * 最后回复时间
     */
    private Integer post_time;

    /**
     * 帖子权重因子
     */
    private Integer factor;

    /**
     * 帖子正文
     */
    private String content;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Byte getIsPinned() {
        return isPinned;
    }

    public void setIsPinned(Byte isPinned) {
        this.isPinned = isPinned;
    }

    public Byte getIsEssence() {
        return isEssence;
    }

    public void setIsEssence(Byte isEssence) {
        this.isEssence = isEssence;
    }

    public Integer getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(Integer viewsCount) {
        this.viewsCount = viewsCount;
    }

    public Integer getUpvotesCount() {
        return upvotesCount;
    }

    public void setUpvotesCount(Integer upvotesCount) {
        this.upvotesCount = upvotesCount;
    }

    public Integer getDownvotesCount() {
        return downvotesCount;
    }

    public void setDownvotesCount(Integer downvotesCount) {
        this.downvotesCount = downvotesCount;
    }

    public Integer getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(Integer favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public Integer getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(Integer postsCount) {
        this.postsCount = postsCount;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getComment_status() {
        return comment_status;
    }

    public void setComment_status(Byte comment_status) {
        this.comment_status = comment_status;
    }

    public Integer getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Integer create_time) {
        this.create_time = create_time;
    }

    public Integer getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Integer update_time) {
        this.update_time = update_time;
    }

    public Integer getPost_time() {
        return post_time;
    }

    public void setPost_time(Integer post_time) {
        this.post_time = post_time;
    }

    public Integer getFactor() {
        return factor;
    }

    public void setFactor(Integer factor) {
        this.factor = factor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TopicEntity other = (TopicEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCid() == null ? other.getCid() == null : this.getCid().equals(other.getCid()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getAuthor() == null ? other.getAuthor() == null : this.getAuthor().equals(other.getAuthor()))
            && (this.getAuthorId() == null ? other.getAuthorId() == null : this.getAuthorId().equals(other.getAuthorId()))
            && (this.getIsPinned() == null ? other.getIsPinned() == null : this.getIsPinned().equals(other.getIsPinned()))
            && (this.getIsEssence() == null ? other.getIsEssence() == null : this.getIsEssence().equals(other.getIsEssence()))
            && (this.getViewsCount() == null ? other.getViewsCount() == null : this.getViewsCount().equals(other.getViewsCount()))
            && (this.getUpvotesCount() == null ? other.getUpvotesCount() == null : this.getUpvotesCount().equals(other.getUpvotesCount()))
            && (this.getDownvotesCount() == null ? other.getDownvotesCount() == null : this.getDownvotesCount().equals(other.getDownvotesCount()))
            && (this.getFavoritesCount() == null ? other.getFavoritesCount() == null : this.getFavoritesCount().equals(other.getFavoritesCount()))
            && (this.getPostsCount() == null ? other.getPostsCount() == null : this.getPostsCount().equals(other.getPostsCount()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getComment_status() == null ? other.getComment_status() == null : this.getComment_status().equals(other.getComment_status()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getPost_time() == null ? other.getPost_time() == null : this.getPost_time().equals(other.getPost_time()))
            && (this.getFactor() == null ? other.getFactor() == null : this.getFactor().equals(other.getFactor()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCid() == null) ? 0 : getCid().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getAuthor() == null) ? 0 : getAuthor().hashCode());
        result = prime * result + ((getAuthorId() == null) ? 0 : getAuthorId().hashCode());
        result = prime * result + ((getIsPinned() == null) ? 0 : getIsPinned().hashCode());
        result = prime * result + ((getIsEssence() == null) ? 0 : getIsEssence().hashCode());
        result = prime * result + ((getViewsCount() == null) ? 0 : getViewsCount().hashCode());
        result = prime * result + ((getUpvotesCount() == null) ? 0 : getUpvotesCount().hashCode());
        result = prime * result + ((getDownvotesCount() == null) ? 0 : getDownvotesCount().hashCode());
        result = prime * result + ((getFavoritesCount() == null) ? 0 : getFavoritesCount().hashCode());
        result = prime * result + ((getPostsCount() == null) ? 0 : getPostsCount().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getComment_status() == null) ? 0 : getComment_status().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getPost_time() == null) ? 0 : getPost_time().hashCode());
        result = prime * result + ((getFactor() == null) ? 0 : getFactor().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", cid=").append(cid);
        sb.append(", title=").append(title);
        sb.append(", author=").append(author);
        sb.append(", authorId=").append(authorId);
        sb.append(", isPinned=").append(isPinned);
        sb.append(", isEssence=").append(isEssence);
        sb.append(", viewsCount=").append(viewsCount);
        sb.append(", upvotesCount=").append(upvotesCount);
        sb.append(", downvotesCount=").append(downvotesCount);
        sb.append(", favoritesCount=").append(favoritesCount);
        sb.append(", postsCount=").append(postsCount);
        sb.append(", status=").append(status);
        sb.append(", comment_status=").append(comment_status);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_time=").append(update_time);
        sb.append(", post_time=").append(post_time);
        sb.append(", factor=").append(factor);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}