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
public class PostEntity implements Serializable {
    private Long id;

    /**
     * 话题ID
     */
    private Long tid;

    /**
     * 父级Post ID
     */
    private Long pid;

    /**
     * 评论回复作者
     */
    private String authorName;

    private Long authorId;

    /**
     * 话题作者
     */
    private String topicAuthorName;

    private Long topicAuthorId;

    private String ip;

    private String ua;

    /**
     * 点赞数
     */
    private Integer upvotesCount;

    /**
     * 点踩数
     */
    private Integer downvotesCount;

    /**
     * Post类型 1 - 评论 2 - 回复
     */
    private Byte type;

    /**
     * Post状态 0 - 删除 1 - 正常
     */
    private Byte status;

    /**
     * Post发布时间
     */
    private Integer createTime;

    /**
     * 评论回复内容(纯文本)
     */
    private String content;

    /**
     * 评论回复内容(Html)
     */
    private String contentHtml;

    /**
     * 评论回复内容(DraftJS编辑器原始数据)
     */
    private String contentRaw;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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

    public String getTopicAuthorName() {
        return topicAuthorName;
    }

    public void setTopicAuthorName(String topicAuthorName) {
        this.topicAuthorName = topicAuthorName;
    }

    public Long getTopicAuthorId() {
        return topicAuthorId;
    }

    public void setTopicAuthorId(Long topicAuthorId) {
        this.topicAuthorId = topicAuthorId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
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
        PostEntity other = (PostEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTid() == null ? other.getTid() == null : this.getTid().equals(other.getTid()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getAuthorName() == null ? other.getAuthorName() == null : this.getAuthorName().equals(other.getAuthorName()))
            && (this.getAuthorId() == null ? other.getAuthorId() == null : this.getAuthorId().equals(other.getAuthorId()))
            && (this.getTopicAuthorName() == null ? other.getTopicAuthorName() == null : this.getTopicAuthorName().equals(other.getTopicAuthorName()))
            && (this.getTopicAuthorId() == null ? other.getTopicAuthorId() == null : this.getTopicAuthorId().equals(other.getTopicAuthorId()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getUa() == null ? other.getUa() == null : this.getUa().equals(other.getUa()))
            && (this.getUpvotesCount() == null ? other.getUpvotesCount() == null : this.getUpvotesCount().equals(other.getUpvotesCount()))
            && (this.getDownvotesCount() == null ? other.getDownvotesCount() == null : this.getDownvotesCount().equals(other.getDownvotesCount()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getContentHtml() == null ? other.getContentHtml() == null : this.getContentHtml().equals(other.getContentHtml()))
            && (this.getContentRaw() == null ? other.getContentRaw() == null : this.getContentRaw().equals(other.getContentRaw()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTid() == null) ? 0 : getTid().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getAuthorName() == null) ? 0 : getAuthorName().hashCode());
        result = prime * result + ((getAuthorId() == null) ? 0 : getAuthorId().hashCode());
        result = prime * result + ((getTopicAuthorName() == null) ? 0 : getTopicAuthorName().hashCode());
        result = prime * result + ((getTopicAuthorId() == null) ? 0 : getTopicAuthorId().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getUa() == null) ? 0 : getUa().hashCode());
        result = prime * result + ((getUpvotesCount() == null) ? 0 : getUpvotesCount().hashCode());
        result = prime * result + ((getDownvotesCount() == null) ? 0 : getDownvotesCount().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
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
        sb.append(", tid=").append(tid);
        sb.append(", pid=").append(pid);
        sb.append(", authorName=").append(authorName);
        sb.append(", authorId=").append(authorId);
        sb.append(", topicAuthorName=").append(topicAuthorName);
        sb.append(", topicAuthorId=").append(topicAuthorId);
        sb.append(", ip=").append(ip);
        sb.append(", ua=").append(ua);
        sb.append(", upvotesCount=").append(upvotesCount);
        sb.append(", downvotesCount=").append(downvotesCount);
        sb.append(", type=").append(type);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", content=").append(content);
        sb.append(", contentHtml=").append(contentHtml);
        sb.append(", contentRaw=").append(contentRaw);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
