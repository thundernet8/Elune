package com.elune.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Touchumind
 */
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TopicEntityWithBLOBs extends TopicEntity implements Serializable {
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
        TopicEntityWithBLOBs other = (TopicEntityWithBLOBs) that;
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
            && (this.getCommentStatus() == null ? other.getCommentStatus() == null : this.getCommentStatus().equals(other.getCommentStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getPostTime() == null ? other.getPostTime() == null : this.getPostTime().equals(other.getPostTime()))
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
        result = prime * result + ((getCommentStatus() == null) ? 0 : getCommentStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getPostTime() == null) ? 0 : getPostTime().hashCode());
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
        sb.append(", content=").append(content);
        sb.append(", contentHtml=").append(contentHtml);
        sb.append(", contentRaw=").append(contentRaw);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
