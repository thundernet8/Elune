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
    private String authorName;

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
    private Byte commentStatus;

    /**
     * 创建时间
     */
    private Integer createTime;

    /**
     * 更新时间
     */
    private Integer updateTime;

    /**
     * 最后回复时间
     */
    private Integer postTime;

    /**
     * 最后回复者用户名
     */
    private String poster;

    /**
     * 最后回复者ID
     */
    private Long posterId;

    /**
     * 帖子权重因子
     */
    private Integer factor;

    /**
     * 帖子正文(纯文本)
     */
    private String content;

    /**
     * 帖子内容(Html)
     */
    private String contentHtml;

    /**
     * 帖子内容(DraftJS编辑器原始数据)
     */
    private String contentRaw;

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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public Byte getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Byte commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPostTime() {
        return postTime;
    }

    public void setPostTime(Integer postTime) {
        this.postTime = postTime;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Long getPosterId() {
        return posterId;
    }

    public void setPosterId(Long posterId) {
        this.posterId = posterId;
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

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getContentRaw() {
        return contentRaw;
    }

    public void setContentRaw(String contentRaw) {
        this.contentRaw = contentRaw;
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
            && (this.getAuthorName() == null ? other.getAuthorName() == null : this.getAuthorName().equals(other.getAuthorName()))
            && (this.getAuthorId() == null ? other.getAuthorId() == null : this.getAuthorId().equals(other.getAuthorId()))
            && (this.getIsPinned() == null ? other.getIsPinned() == null : this.getIsPinned().equals(other.getIsPinned()))
            && (this.getIsEssence() == null ? other.getIsEssence() == null : this.getIsEssence().equals(other.getIsEssence()))
            && (this.getViewsCount() == null ? other.getViewsCount() == null : this.getViewsCount().equals(other.getViewsCount()))
            && (this.getUpvotesCount() == null ? other.getUpvotesCount() == null : this.getUpvotesCount().equals(other.getUpvotesCount()))
            && (this.getDownvotesCount() == null ? other.getDownvotesCount() == null : this.getDownvotesCount().equals(other.getDownvotesCount()))
            && (this.getFavoritesCount() == null ? other.getFavoritesCount() == null : this.getFavoritesCount().equals(other.getFavoritesCount()))
            && (this.getPostsCount() == null ? other.getPostsCount() == null : this.getPostsCount().equals(other.getPostsCount()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCommentStatus() == null ? other.getCommentStatus() == null : this.getCommentStatus().equals(other.getCommentStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getPostTime() == null ? other.getPostTime() == null : this.getPostTime().equals(other.getPostTime()))
            && (this.getPoster() == null ? other.getPoster() == null : this.getPoster().equals(other.getPoster()))
            && (this.getPosterId() == null ? other.getPosterId() == null : this.getPosterId().equals(other.getPosterId()))
            && (this.getFactor() == null ? other.getFactor() == null : this.getFactor().equals(other.getFactor()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getContentHtml() == null ? other.getContentHtml() == null : this.getContentHtml().equals(other.getContentHtml()))
            && (this.getContentRaw() == null ? other.getContentRaw() == null : this.getContentRaw().equals(other.getContentRaw()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCid() == null) ? 0 : getCid().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getAuthorName() == null) ? 0 : getAuthorName().hashCode());
        result = prime * result + ((getAuthorId() == null) ? 0 : getAuthorId().hashCode());
        result = prime * result + ((getIsPinned() == null) ? 0 : getIsPinned().hashCode());
        result = prime * result + ((getIsEssence() == null) ? 0 : getIsEssence().hashCode());
        result = prime * result + ((getViewsCount() == null) ? 0 : getViewsCount().hashCode());
        result = prime * result + ((getUpvotesCount() == null) ? 0 : getUpvotesCount().hashCode());
        result = prime * result + ((getDownvotesCount() == null) ? 0 : getDownvotesCount().hashCode());
        result = prime * result + ((getFavoritesCount() == null) ? 0 : getFavoritesCount().hashCode());
        result = prime * result + ((getPostsCount() == null) ? 0 : getPostsCount().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCommentStatus() == null) ? 0 : getCommentStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getPostTime() == null) ? 0 : getPostTime().hashCode());
        result = prime * result + ((getPoster() == null) ? 0 : getPoster().hashCode());
        result = prime * result + ((getPosterId() == null) ? 0 : getPosterId().hashCode());
        result = prime * result + ((getFactor() == null) ? 0 : getFactor().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getContentHtml() == null) ? 0 : getContentHtml().hashCode());
        result = prime * result + ((getContentRaw() == null) ? 0 : getContentRaw().hashCode());
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
        sb.append(", authorName=").append(authorName);
        sb.append(", authorId=").append(authorId);
        sb.append(", isPinned=").append(isPinned);
        sb.append(", isEssence=").append(isEssence);
        sb.append(", viewsCount=").append(viewsCount);
        sb.append(", upvotesCount=").append(upvotesCount);
        sb.append(", downvotesCount=").append(downvotesCount);
        sb.append(", favoritesCount=").append(favoritesCount);
        sb.append(", postsCount=").append(postsCount);
        sb.append(", status=").append(status);
        sb.append(", commentStatus=").append(commentStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", postTime=").append(postTime);
        sb.append(", poster=").append(poster);
        sb.append(", posterId=").append(posterId);
        sb.append(", factor=").append(factor);
        sb.append(", content=").append(content);
        sb.append(", contentHtml=").append(contentHtml);
        sb.append(", contentRaw=").append(contentRaw);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}