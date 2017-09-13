/**
 * Elune - Lightweight Forum Powered by Razor.
 * Copyright (C) 2017, Touchumind<chinash2010@gmail.com>
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.elune.entity;

import java.io.Serializable;

/**
 * @author Touchumind
 */
public class UserEntity implements Serializable {
    private Long id;

    private String password;

    private String username;

    private String nickname;

    private String email;

    /**
     * 用户个人主页
     */
    private String url;

    /**
     * 注册时间
     */
    private Integer joinTime;

    /**
     * 上次登录等操作时间
     */
    private Integer lastSeen;

    private Boolean status;

    private String bio;

    /**
     * 用户头像地址
     */
    private String avatar;

    /**
     * 记录更新时间
     */
    private Integer updateTime;

    /**
     * 用户角色 0-系统管理员 1-管理员 2-9保留区间 10-普通用户
     */
    private Byte roleId;

    /**
     * 发布的主题数量
     */
    private Integer topicsCount;

    /**
     * 发布的回复评论数量
     */
    private Integer postsCount;

    /**
     * 发布的文章数量
     */
    private Integer articlesCount;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Integer joinTime) {
        this.joinTime = joinTime;
    }

    public Integer getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Integer lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getRoleId() {
        return roleId;
    }

    public void setRoleId(Byte roleId) {
        this.roleId = roleId;
    }

    public Integer getTopicsCount() {
        return topicsCount;
    }

    public void setTopicsCount(Integer topicsCount) {
        this.topicsCount = topicsCount;
    }

    public Integer getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(Integer postsCount) {
        this.postsCount = postsCount;
    }

    public Integer getArticlesCount() {
        return articlesCount;
    }

    public void setArticlesCount(Integer articlesCount) {
        this.articlesCount = articlesCount;
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
        UserEntity other = (UserEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getJoinTime() == null ? other.getJoinTime() == null : this.getJoinTime().equals(other.getJoinTime()))
            && (this.getLastSeen() == null ? other.getLastSeen() == null : this.getLastSeen().equals(other.getLastSeen()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getBio() == null ? other.getBio() == null : this.getBio().equals(other.getBio()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getTopicsCount() == null ? other.getTopicsCount() == null : this.getTopicsCount().equals(other.getTopicsCount()))
            && (this.getPostsCount() == null ? other.getPostsCount() == null : this.getPostsCount().equals(other.getPostsCount()))
            && (this.getArticlesCount() == null ? other.getArticlesCount() == null : this.getArticlesCount().equals(other.getArticlesCount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getJoinTime() == null) ? 0 : getJoinTime().hashCode());
        result = prime * result + ((getLastSeen() == null) ? 0 : getLastSeen().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getBio() == null) ? 0 : getBio().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getTopicsCount() == null) ? 0 : getTopicsCount().hashCode());
        result = prime * result + ((getPostsCount() == null) ? 0 : getPostsCount().hashCode());
        result = prime * result + ((getArticlesCount() == null) ? 0 : getArticlesCount().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", password=").append(password);
        sb.append(", username=").append(username);
        sb.append(", nickname=").append(nickname);
        sb.append(", email=").append(email);
        sb.append(", url=").append(url);
        sb.append(", joinTime=").append(joinTime);
        sb.append(", lastSeen=").append(lastSeen);
        sb.append(", status=").append(status);
        sb.append(", bio=").append(bio);
        sb.append(", avatar=").append(avatar);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", roleId=").append(roleId);
        sb.append(", topicsCount=").append(topicsCount);
        sb.append(", postsCount=").append(postsCount);
        sb.append(", articlesCount=").append(articlesCount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
