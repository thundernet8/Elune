package com.elune.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class UserlogEntityWithBLOBs extends UserlogEntity implements Serializable {
    /**
     * 操作之前的值
     */
    private String before;

    /**
     * 操作之后的值
     */
    private String after;

    private static final long serialVersionUID = 1L;

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
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
        UserlogEntityWithBLOBs other = (UserlogEntityWithBLOBs) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getUa() == null ? other.getUa() == null : this.getUa().equals(other.getUa()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getBefore() == null ? other.getBefore() == null : this.getBefore().equals(other.getBefore()))
            && (this.getAfter() == null ? other.getAfter() == null : this.getAfter().equals(other.getAfter()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getUa() == null) ? 0 : getUa().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getBefore() == null) ? 0 : getBefore().hashCode());
        result = prime * result + ((getAfter() == null) ? 0 : getAfter().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", before=").append(before);
        sb.append(", after=").append(after);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}