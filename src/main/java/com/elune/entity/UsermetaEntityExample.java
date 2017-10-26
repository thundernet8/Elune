package com.elune.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UsermetaEntityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public UsermetaEntityExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Long value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Long value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Long value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Long value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Long value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Long value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Long> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Long> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Long value1, Long value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Long value1, Long value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andMetaKeyIsNull() {
            addCriterion("meta_key is null");
            return (Criteria) this;
        }

        public Criteria andMetaKeyIsNotNull() {
            addCriterion("meta_key is not null");
            return (Criteria) this;
        }

        public Criteria andMetaKeyEqualTo(String value) {
            addCriterion("meta_key =", value, "metaKey");
            return (Criteria) this;
        }

        public Criteria andMetaKeyNotEqualTo(String value) {
            addCriterion("meta_key <>", value, "metaKey");
            return (Criteria) this;
        }

        public Criteria andMetaKeyGreaterThan(String value) {
            addCriterion("meta_key >", value, "metaKey");
            return (Criteria) this;
        }

        public Criteria andMetaKeyGreaterThanOrEqualTo(String value) {
            addCriterion("meta_key >=", value, "metaKey");
            return (Criteria) this;
        }

        public Criteria andMetaKeyLessThan(String value) {
            addCriterion("meta_key <", value, "metaKey");
            return (Criteria) this;
        }

        public Criteria andMetaKeyLessThanOrEqualTo(String value) {
            addCriterion("meta_key <=", value, "metaKey");
            return (Criteria) this;
        }

        public Criteria andMetaKeyLike(String value) {
            addCriterion("meta_key like", value, "metaKey");
            return (Criteria) this;
        }

        public Criteria andMetaKeyNotLike(String value) {
            addCriterion("meta_key not like", value, "metaKey");
            return (Criteria) this;
        }

        public Criteria andMetaKeyIn(List<String> values) {
            addCriterion("meta_key in", values, "metaKey");
            return (Criteria) this;
        }

        public Criteria andMetaKeyNotIn(List<String> values) {
            addCriterion("meta_key not in", values, "metaKey");
            return (Criteria) this;
        }

        public Criteria andMetaKeyBetween(String value1, String value2) {
            addCriterion("meta_key between", value1, value2, "metaKey");
            return (Criteria) this;
        }

        public Criteria andMetaKeyNotBetween(String value1, String value2) {
            addCriterion("meta_key not between", value1, value2, "metaKey");
            return (Criteria) this;
        }

        public Criteria andMetaValueIsNull() {
            addCriterion("meta_value is null");
            return (Criteria) this;
        }

        public Criteria andMetaValueIsNotNull() {
            addCriterion("meta_value is not null");
            return (Criteria) this;
        }

        public Criteria andMetaValueEqualTo(String value) {
            addCriterion("meta_value =", value, "metaValue");
            return (Criteria) this;
        }

        public Criteria andMetaValueNotEqualTo(String value) {
            addCriterion("meta_value <>", value, "metaValue");
            return (Criteria) this;
        }

        public Criteria andMetaValueGreaterThan(String value) {
            addCriterion("meta_value >", value, "metaValue");
            return (Criteria) this;
        }

        public Criteria andMetaValueGreaterThanOrEqualTo(String value) {
            addCriterion("meta_value >=", value, "metaValue");
            return (Criteria) this;
        }

        public Criteria andMetaValueLessThan(String value) {
            addCriterion("meta_value <", value, "metaValue");
            return (Criteria) this;
        }

        public Criteria andMetaValueLessThanOrEqualTo(String value) {
            addCriterion("meta_value <=", value, "metaValue");
            return (Criteria) this;
        }

        public Criteria andMetaValueLike(String value) {
            addCriterion("meta_value like", value, "metaValue");
            return (Criteria) this;
        }

        public Criteria andMetaValueNotLike(String value) {
            addCriterion("meta_value not like", value, "metaValue");
            return (Criteria) this;
        }

        public Criteria andMetaValueIn(List<String> values) {
            addCriterion("meta_value in", values, "metaValue");
            return (Criteria) this;
        }

        public Criteria andMetaValueNotIn(List<String> values) {
            addCriterion("meta_value not in", values, "metaValue");
            return (Criteria) this;
        }

        public Criteria andMetaValueBetween(String value1, String value2) {
            addCriterion("meta_value between", value1, value2, "metaValue");
            return (Criteria) this;
        }

        public Criteria andMetaValueNotBetween(String value1, String value2) {
            addCriterion("meta_value not between", value1, value2, "metaValue");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
