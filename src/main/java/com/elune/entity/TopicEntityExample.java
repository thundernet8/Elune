package com.elune.entity;

import java.util.ArrayList;
import java.util.List;

public class TopicEntityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public TopicEntityExample() {
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

        public Criteria andCidIsNull() {
            addCriterion("cid is null");
            return (Criteria) this;
        }

        public Criteria andCidIsNotNull() {
            addCriterion("cid is not null");
            return (Criteria) this;
        }

        public Criteria andCidEqualTo(Integer value) {
            addCriterion("cid =", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidNotEqualTo(Integer value) {
            addCriterion("cid <>", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidGreaterThan(Integer value) {
            addCriterion("cid >", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidGreaterThanOrEqualTo(Integer value) {
            addCriterion("cid >=", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidLessThan(Integer value) {
            addCriterion("cid <", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidLessThanOrEqualTo(Integer value) {
            addCriterion("cid <=", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidIn(List<Integer> values) {
            addCriterion("cid in", values, "cid");
            return (Criteria) this;
        }

        public Criteria andCidNotIn(List<Integer> values) {
            addCriterion("cid not in", values, "cid");
            return (Criteria) this;
        }

        public Criteria andCidBetween(Integer value1, Integer value2) {
            addCriterion("cid between", value1, value2, "cid");
            return (Criteria) this;
        }

        public Criteria andCidNotBetween(Integer value1, Integer value2) {
            addCriterion("cid not between", value1, value2, "cid");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andAuthorIsNull() {
            addCriterion("author is null");
            return (Criteria) this;
        }

        public Criteria andAuthorIsNotNull() {
            addCriterion("author is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorEqualTo(String value) {
            addCriterion("author =", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotEqualTo(String value) {
            addCriterion("author <>", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorGreaterThan(String value) {
            addCriterion("author >", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorGreaterThanOrEqualTo(String value) {
            addCriterion("author >=", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorLessThan(String value) {
            addCriterion("author <", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorLessThanOrEqualTo(String value) {
            addCriterion("author <=", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorLike(String value) {
            addCriterion("author like", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotLike(String value) {
            addCriterion("author not like", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorIn(List<String> values) {
            addCriterion("author in", values, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotIn(List<String> values) {
            addCriterion("author not in", values, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorBetween(String value1, String value2) {
            addCriterion("author between", value1, value2, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotBetween(String value1, String value2) {
            addCriterion("author not between", value1, value2, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorIdIsNull() {
            addCriterion("author_id is null");
            return (Criteria) this;
        }

        public Criteria andAuthorIdIsNotNull() {
            addCriterion("author_id is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorIdEqualTo(Long value) {
            addCriterion("author_id =", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdNotEqualTo(Long value) {
            addCriterion("author_id <>", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdGreaterThan(Long value) {
            addCriterion("author_id >", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdGreaterThanOrEqualTo(Long value) {
            addCriterion("author_id >=", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdLessThan(Long value) {
            addCriterion("author_id <", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdLessThanOrEqualTo(Long value) {
            addCriterion("author_id <=", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdIn(List<Long> values) {
            addCriterion("author_id in", values, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdNotIn(List<Long> values) {
            addCriterion("author_id not in", values, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdBetween(Long value1, Long value2) {
            addCriterion("author_id between", value1, value2, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdNotBetween(Long value1, Long value2) {
            addCriterion("author_id not between", value1, value2, "authorId");
            return (Criteria) this;
        }

        public Criteria andIsPinnedIsNull() {
            addCriterion("is_pinned is null");
            return (Criteria) this;
        }

        public Criteria andIsPinnedIsNotNull() {
            addCriterion("is_pinned is not null");
            return (Criteria) this;
        }

        public Criteria andIsPinnedEqualTo(Byte value) {
            addCriterion("is_pinned =", value, "isPinned");
            return (Criteria) this;
        }

        public Criteria andIsPinnedNotEqualTo(Byte value) {
            addCriterion("is_pinned <>", value, "isPinned");
            return (Criteria) this;
        }

        public Criteria andIsPinnedGreaterThan(Byte value) {
            addCriterion("is_pinned >", value, "isPinned");
            return (Criteria) this;
        }

        public Criteria andIsPinnedGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_pinned >=", value, "isPinned");
            return (Criteria) this;
        }

        public Criteria andIsPinnedLessThan(Byte value) {
            addCriterion("is_pinned <", value, "isPinned");
            return (Criteria) this;
        }

        public Criteria andIsPinnedLessThanOrEqualTo(Byte value) {
            addCriterion("is_pinned <=", value, "isPinned");
            return (Criteria) this;
        }

        public Criteria andIsPinnedIn(List<Byte> values) {
            addCriterion("is_pinned in", values, "isPinned");
            return (Criteria) this;
        }

        public Criteria andIsPinnedNotIn(List<Byte> values) {
            addCriterion("is_pinned not in", values, "isPinned");
            return (Criteria) this;
        }

        public Criteria andIsPinnedBetween(Byte value1, Byte value2) {
            addCriterion("is_pinned between", value1, value2, "isPinned");
            return (Criteria) this;
        }

        public Criteria andIsPinnedNotBetween(Byte value1, Byte value2) {
            addCriterion("is_pinned not between", value1, value2, "isPinned");
            return (Criteria) this;
        }

        public Criteria andIsEssenceIsNull() {
            addCriterion("is_essence is null");
            return (Criteria) this;
        }

        public Criteria andIsEssenceIsNotNull() {
            addCriterion("is_essence is not null");
            return (Criteria) this;
        }

        public Criteria andIsEssenceEqualTo(Byte value) {
            addCriterion("is_essence =", value, "isEssence");
            return (Criteria) this;
        }

        public Criteria andIsEssenceNotEqualTo(Byte value) {
            addCriterion("is_essence <>", value, "isEssence");
            return (Criteria) this;
        }

        public Criteria andIsEssenceGreaterThan(Byte value) {
            addCriterion("is_essence >", value, "isEssence");
            return (Criteria) this;
        }

        public Criteria andIsEssenceGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_essence >=", value, "isEssence");
            return (Criteria) this;
        }

        public Criteria andIsEssenceLessThan(Byte value) {
            addCriterion("is_essence <", value, "isEssence");
            return (Criteria) this;
        }

        public Criteria andIsEssenceLessThanOrEqualTo(Byte value) {
            addCriterion("is_essence <=", value, "isEssence");
            return (Criteria) this;
        }

        public Criteria andIsEssenceIn(List<Byte> values) {
            addCriterion("is_essence in", values, "isEssence");
            return (Criteria) this;
        }

        public Criteria andIsEssenceNotIn(List<Byte> values) {
            addCriterion("is_essence not in", values, "isEssence");
            return (Criteria) this;
        }

        public Criteria andIsEssenceBetween(Byte value1, Byte value2) {
            addCriterion("is_essence between", value1, value2, "isEssence");
            return (Criteria) this;
        }

        public Criteria andIsEssenceNotBetween(Byte value1, Byte value2) {
            addCriterion("is_essence not between", value1, value2, "isEssence");
            return (Criteria) this;
        }

        public Criteria andViewsCountIsNull() {
            addCriterion("views_count is null");
            return (Criteria) this;
        }

        public Criteria andViewsCountIsNotNull() {
            addCriterion("views_count is not null");
            return (Criteria) this;
        }

        public Criteria andViewsCountEqualTo(Integer value) {
            addCriterion("views_count =", value, "viewsCount");
            return (Criteria) this;
        }

        public Criteria andViewsCountNotEqualTo(Integer value) {
            addCriterion("views_count <>", value, "viewsCount");
            return (Criteria) this;
        }

        public Criteria andViewsCountGreaterThan(Integer value) {
            addCriterion("views_count >", value, "viewsCount");
            return (Criteria) this;
        }

        public Criteria andViewsCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("views_count >=", value, "viewsCount");
            return (Criteria) this;
        }

        public Criteria andViewsCountLessThan(Integer value) {
            addCriterion("views_count <", value, "viewsCount");
            return (Criteria) this;
        }

        public Criteria andViewsCountLessThanOrEqualTo(Integer value) {
            addCriterion("views_count <=", value, "viewsCount");
            return (Criteria) this;
        }

        public Criteria andViewsCountIn(List<Integer> values) {
            addCriterion("views_count in", values, "viewsCount");
            return (Criteria) this;
        }

        public Criteria andViewsCountNotIn(List<Integer> values) {
            addCriterion("views_count not in", values, "viewsCount");
            return (Criteria) this;
        }

        public Criteria andViewsCountBetween(Integer value1, Integer value2) {
            addCriterion("views_count between", value1, value2, "viewsCount");
            return (Criteria) this;
        }

        public Criteria andViewsCountNotBetween(Integer value1, Integer value2) {
            addCriterion("views_count not between", value1, value2, "viewsCount");
            return (Criteria) this;
        }

        public Criteria andUpvotesCountIsNull() {
            addCriterion("upvotes_count is null");
            return (Criteria) this;
        }

        public Criteria andUpvotesCountIsNotNull() {
            addCriterion("upvotes_count is not null");
            return (Criteria) this;
        }

        public Criteria andUpvotesCountEqualTo(Integer value) {
            addCriterion("upvotes_count =", value, "upvotesCount");
            return (Criteria) this;
        }

        public Criteria andUpvotesCountNotEqualTo(Integer value) {
            addCriterion("upvotes_count <>", value, "upvotesCount");
            return (Criteria) this;
        }

        public Criteria andUpvotesCountGreaterThan(Integer value) {
            addCriterion("upvotes_count >", value, "upvotesCount");
            return (Criteria) this;
        }

        public Criteria andUpvotesCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("upvotes_count >=", value, "upvotesCount");
            return (Criteria) this;
        }

        public Criteria andUpvotesCountLessThan(Integer value) {
            addCriterion("upvotes_count <", value, "upvotesCount");
            return (Criteria) this;
        }

        public Criteria andUpvotesCountLessThanOrEqualTo(Integer value) {
            addCriterion("upvotes_count <=", value, "upvotesCount");
            return (Criteria) this;
        }

        public Criteria andUpvotesCountIn(List<Integer> values) {
            addCriterion("upvotes_count in", values, "upvotesCount");
            return (Criteria) this;
        }

        public Criteria andUpvotesCountNotIn(List<Integer> values) {
            addCriterion("upvotes_count not in", values, "upvotesCount");
            return (Criteria) this;
        }

        public Criteria andUpvotesCountBetween(Integer value1, Integer value2) {
            addCriterion("upvotes_count between", value1, value2, "upvotesCount");
            return (Criteria) this;
        }

        public Criteria andUpvotesCountNotBetween(Integer value1, Integer value2) {
            addCriterion("upvotes_count not between", value1, value2, "upvotesCount");
            return (Criteria) this;
        }

        public Criteria andDownvotesCountIsNull() {
            addCriterion("downvotes_count is null");
            return (Criteria) this;
        }

        public Criteria andDownvotesCountIsNotNull() {
            addCriterion("downvotes_count is not null");
            return (Criteria) this;
        }

        public Criteria andDownvotesCountEqualTo(Integer value) {
            addCriterion("downvotes_count =", value, "downvotesCount");
            return (Criteria) this;
        }

        public Criteria andDownvotesCountNotEqualTo(Integer value) {
            addCriterion("downvotes_count <>", value, "downvotesCount");
            return (Criteria) this;
        }

        public Criteria andDownvotesCountGreaterThan(Integer value) {
            addCriterion("downvotes_count >", value, "downvotesCount");
            return (Criteria) this;
        }

        public Criteria andDownvotesCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("downvotes_count >=", value, "downvotesCount");
            return (Criteria) this;
        }

        public Criteria andDownvotesCountLessThan(Integer value) {
            addCriterion("downvotes_count <", value, "downvotesCount");
            return (Criteria) this;
        }

        public Criteria andDownvotesCountLessThanOrEqualTo(Integer value) {
            addCriterion("downvotes_count <=", value, "downvotesCount");
            return (Criteria) this;
        }

        public Criteria andDownvotesCountIn(List<Integer> values) {
            addCriterion("downvotes_count in", values, "downvotesCount");
            return (Criteria) this;
        }

        public Criteria andDownvotesCountNotIn(List<Integer> values) {
            addCriterion("downvotes_count not in", values, "downvotesCount");
            return (Criteria) this;
        }

        public Criteria andDownvotesCountBetween(Integer value1, Integer value2) {
            addCriterion("downvotes_count between", value1, value2, "downvotesCount");
            return (Criteria) this;
        }

        public Criteria andDownvotesCountNotBetween(Integer value1, Integer value2) {
            addCriterion("downvotes_count not between", value1, value2, "downvotesCount");
            return (Criteria) this;
        }

        public Criteria andFavoritesCountIsNull() {
            addCriterion("favorites_count is null");
            return (Criteria) this;
        }

        public Criteria andFavoritesCountIsNotNull() {
            addCriterion("favorites_count is not null");
            return (Criteria) this;
        }

        public Criteria andFavoritesCountEqualTo(Integer value) {
            addCriterion("favorites_count =", value, "favoritesCount");
            return (Criteria) this;
        }

        public Criteria andFavoritesCountNotEqualTo(Integer value) {
            addCriterion("favorites_count <>", value, "favoritesCount");
            return (Criteria) this;
        }

        public Criteria andFavoritesCountGreaterThan(Integer value) {
            addCriterion("favorites_count >", value, "favoritesCount");
            return (Criteria) this;
        }

        public Criteria andFavoritesCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("favorites_count >=", value, "favoritesCount");
            return (Criteria) this;
        }

        public Criteria andFavoritesCountLessThan(Integer value) {
            addCriterion("favorites_count <", value, "favoritesCount");
            return (Criteria) this;
        }

        public Criteria andFavoritesCountLessThanOrEqualTo(Integer value) {
            addCriterion("favorites_count <=", value, "favoritesCount");
            return (Criteria) this;
        }

        public Criteria andFavoritesCountIn(List<Integer> values) {
            addCriterion("favorites_count in", values, "favoritesCount");
            return (Criteria) this;
        }

        public Criteria andFavoritesCountNotIn(List<Integer> values) {
            addCriterion("favorites_count not in", values, "favoritesCount");
            return (Criteria) this;
        }

        public Criteria andFavoritesCountBetween(Integer value1, Integer value2) {
            addCriterion("favorites_count between", value1, value2, "favoritesCount");
            return (Criteria) this;
        }

        public Criteria andFavoritesCountNotBetween(Integer value1, Integer value2) {
            addCriterion("favorites_count not between", value1, value2, "favoritesCount");
            return (Criteria) this;
        }

        public Criteria andPostsCountIsNull() {
            addCriterion("posts_count is null");
            return (Criteria) this;
        }

        public Criteria andPostsCountIsNotNull() {
            addCriterion("posts_count is not null");
            return (Criteria) this;
        }

        public Criteria andPostsCountEqualTo(Integer value) {
            addCriterion("posts_count =", value, "postsCount");
            return (Criteria) this;
        }

        public Criteria andPostsCountNotEqualTo(Integer value) {
            addCriterion("posts_count <>", value, "postsCount");
            return (Criteria) this;
        }

        public Criteria andPostsCountGreaterThan(Integer value) {
            addCriterion("posts_count >", value, "postsCount");
            return (Criteria) this;
        }

        public Criteria andPostsCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("posts_count >=", value, "postsCount");
            return (Criteria) this;
        }

        public Criteria andPostsCountLessThan(Integer value) {
            addCriterion("posts_count <", value, "postsCount");
            return (Criteria) this;
        }

        public Criteria andPostsCountLessThanOrEqualTo(Integer value) {
            addCriterion("posts_count <=", value, "postsCount");
            return (Criteria) this;
        }

        public Criteria andPostsCountIn(List<Integer> values) {
            addCriterion("posts_count in", values, "postsCount");
            return (Criteria) this;
        }

        public Criteria andPostsCountNotIn(List<Integer> values) {
            addCriterion("posts_count not in", values, "postsCount");
            return (Criteria) this;
        }

        public Criteria andPostsCountBetween(Integer value1, Integer value2) {
            addCriterion("posts_count between", value1, value2, "postsCount");
            return (Criteria) this;
        }

        public Criteria andPostsCountNotBetween(Integer value1, Integer value2) {
            addCriterion("posts_count not between", value1, value2, "postsCount");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCommentStatusIsNull() {
            addCriterion("comment_status is null");
            return (Criteria) this;
        }

        public Criteria andCommentStatusIsNotNull() {
            addCriterion("comment_status is not null");
            return (Criteria) this;
        }

        public Criteria andCommentStatusEqualTo(Byte value) {
            addCriterion("comment_status =", value, "commentStatus");
            return (Criteria) this;
        }

        public Criteria andCommentStatusNotEqualTo(Byte value) {
            addCriterion("comment_status <>", value, "commentStatus");
            return (Criteria) this;
        }

        public Criteria andCommentStatusGreaterThan(Byte value) {
            addCriterion("comment_status >", value, "commentStatus");
            return (Criteria) this;
        }

        public Criteria andCommentStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("comment_status >=", value, "commentStatus");
            return (Criteria) this;
        }

        public Criteria andCommentStatusLessThan(Byte value) {
            addCriterion("comment_status <", value, "commentStatus");
            return (Criteria) this;
        }

        public Criteria andCommentStatusLessThanOrEqualTo(Byte value) {
            addCriterion("comment_status <=", value, "commentStatus");
            return (Criteria) this;
        }

        public Criteria andCommentStatusIn(List<Byte> values) {
            addCriterion("comment_status in", values, "commentStatus");
            return (Criteria) this;
        }

        public Criteria andCommentStatusNotIn(List<Byte> values) {
            addCriterion("comment_status not in", values, "commentStatus");
            return (Criteria) this;
        }

        public Criteria andCommentStatusBetween(Byte value1, Byte value2) {
            addCriterion("comment_status between", value1, value2, "commentStatus");
            return (Criteria) this;
        }

        public Criteria andCommentStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("comment_status not between", value1, value2, "commentStatus");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Integer value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Integer value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Integer value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Integer value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Integer value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Integer> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Integer> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Integer value1, Integer value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Integer value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Integer value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Integer value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Integer value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Integer value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Integer> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Integer> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Integer value1, Integer value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeIsNull() {
            addCriterion("post_time is null");
            return (Criteria) this;
        }

        public Criteria andPostTimeIsNotNull() {
            addCriterion("post_time is not null");
            return (Criteria) this;
        }

        public Criteria andPostTimeEqualTo(Integer value) {
            addCriterion("post_time =", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeNotEqualTo(Integer value) {
            addCriterion("post_time <>", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeGreaterThan(Integer value) {
            addCriterion("post_time >", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("post_time >=", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeLessThan(Integer value) {
            addCriterion("post_time <", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeLessThanOrEqualTo(Integer value) {
            addCriterion("post_time <=", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeIn(List<Integer> values) {
            addCriterion("post_time in", values, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeNotIn(List<Integer> values) {
            addCriterion("post_time not in", values, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeBetween(Integer value1, Integer value2) {
            addCriterion("post_time between", value1, value2, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("post_time not between", value1, value2, "postTime");
            return (Criteria) this;
        }

        public Criteria andFactorIsNull() {
            addCriterion("factor is null");
            return (Criteria) this;
        }

        public Criteria andFactorIsNotNull() {
            addCriterion("factor is not null");
            return (Criteria) this;
        }

        public Criteria andFactorEqualTo(Integer value) {
            addCriterion("factor =", value, "factor");
            return (Criteria) this;
        }

        public Criteria andFactorNotEqualTo(Integer value) {
            addCriterion("factor <>", value, "factor");
            return (Criteria) this;
        }

        public Criteria andFactorGreaterThan(Integer value) {
            addCriterion("factor >", value, "factor");
            return (Criteria) this;
        }

        public Criteria andFactorGreaterThanOrEqualTo(Integer value) {
            addCriterion("factor >=", value, "factor");
            return (Criteria) this;
        }

        public Criteria andFactorLessThan(Integer value) {
            addCriterion("factor <", value, "factor");
            return (Criteria) this;
        }

        public Criteria andFactorLessThanOrEqualTo(Integer value) {
            addCriterion("factor <=", value, "factor");
            return (Criteria) this;
        }

        public Criteria andFactorIn(List<Integer> values) {
            addCriterion("factor in", values, "factor");
            return (Criteria) this;
        }

        public Criteria andFactorNotIn(List<Integer> values) {
            addCriterion("factor not in", values, "factor");
            return (Criteria) this;
        }

        public Criteria andFactorBetween(Integer value1, Integer value2) {
            addCriterion("factor between", value1, value2, "factor");
            return (Criteria) this;
        }

        public Criteria andFactorNotBetween(Integer value1, Integer value2) {
            addCriterion("factor not between", value1, value2, "factor");
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