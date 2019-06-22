/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * 商品基本信息管理EntityExample
 * @author kaka
 * @version 1.0.1
 */
public class ProductExample {

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductExample() {
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

        public Criteria andIdAllEqualTo(Long value) {
            addCriterion("p.id =", value, "id");
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

		public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andProductImgIsNull() {
            addCriterion("product_img is null");
            return (Criteria) this;
        }

        public Criteria andProductImgIsNotNull() {
            addCriterion("product_img is not null");
            return (Criteria) this;
        }

        public Criteria andProductImgEqualTo(String value) {
            addCriterion("product_img =", value, "productImg");
            return (Criteria) this;
        }

        public Criteria andProductImgNotEqualTo(String value) {
            addCriterion("product_img <>", value, "productImg");
            return (Criteria) this;
        }

        public Criteria andProductImgGreaterThan(String value) {
            addCriterion("product_img >", value, "productImg");
            return (Criteria) this;
        }

        public Criteria andProductImgGreaterThanOrEqualTo(String value) {
            addCriterion("product_img >=", value, "productImg");
            return (Criteria) this;
        }

        public Criteria andProductImgLessThan(String value) {
            addCriterion("product_img <", value, "productImg");
            return (Criteria) this;
        }

        public Criteria andProductImgLessThanOrEqualTo(String value) {
            addCriterion("product_img <=", value, "productImg");
            return (Criteria) this;
        }

		public Criteria andProductImgLike(String value) {
            addCriterion("product_img like", value, "productImg");
            return (Criteria) this;
        }

        public Criteria andProductImgNotLike(String value) {
            addCriterion("product_img not like", value, "productImg");
            return (Criteria) this;
        }

        public Criteria andProductImgIn(List<String> values) {
            addCriterion("product_img in", values, "productImg");
            return (Criteria) this;
        }

        public Criteria andProductImgNotIn(List<String> values) {
            addCriterion("product_img not in", values, "productImg");
            return (Criteria) this;
        }

        public Criteria andProductImgBetween(String value1, String value2) {
            addCriterion("product_img between", value1, value2, "productImg");
            return (Criteria) this;
        }

        public Criteria andProductImgNotBetween(String value1, String value2) {
            addCriterion("product_img not between", value1, value2, "productImg");
            return (Criteria) this;
        }

        public Criteria andProductAcceptIdIsNull() {
            addCriterion("product_accept_id is null");
            return (Criteria) this;
        }

        public Criteria andProductAcceptIdIsNotNull() {
            addCriterion("product_accept_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductAcceptIdEqualTo(Long value) {
            addCriterion("product_accept_id =", value, "productAcceptId");
            return (Criteria) this;
        }

        public Criteria andProductAcceptIdNotEqualTo(Long value) {
            addCriterion("product_accept_id <>", value, "productAcceptId");
            return (Criteria) this;
        }

        public Criteria andProductAcceptIdGreaterThan(Long value) {
            addCriterion("product_accept_id >", value, "productAcceptId");
            return (Criteria) this;
        }

        public Criteria andProductAcceptIdGreaterThanOrEqualTo(Long value) {
            addCriterion("product_accept_id >=", value, "productAcceptId");
            return (Criteria) this;
        }

        public Criteria andProductAcceptIdLessThan(Long value) {
            addCriterion("product_accept_id <", value, "productAcceptId");
            return (Criteria) this;
        }

        public Criteria andProductAcceptIdLessThanOrEqualTo(Long value) {
            addCriterion("product_accept_id <=", value, "productAcceptId");
            return (Criteria) this;
        }

        public Criteria andProductAcceptIdIn(List<Long> values) {
            addCriterion("product_accept_id in", values, "productAcceptId");
            return (Criteria) this;
        }

        public Criteria andProductAcceptIdNotIn(List<Long> values) {
            addCriterion("product_accept_id not in", values, "productAcceptId");
            return (Criteria) this;
        }

        public Criteria andProductAcceptIdBetween(Long value1, Long value2) {
            addCriterion("product_accept_id between", value1, value2, "productAcceptId");
            return (Criteria) this;
        }

        public Criteria andProductAcceptIdNotBetween(Long value1, Long value2) {
            addCriterion("product_accept_id not between", value1, value2, "productAcceptId");
            return (Criteria) this;
        }

        public Criteria andProductIssuerIdIsNull() {
            addCriterion("product_issuer_id is null");
            return (Criteria) this;
        }

        public Criteria andProductIssuerIdIsNotNull() {
            addCriterion("product_issuer_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductIssuerIdEqualTo(Long value) {
            addCriterion("product_issuer_id =", value, "productIssuerId");
            return (Criteria) this;
        }

        public Criteria andProductIssuerIdNotEqualTo(Long value) {
            addCriterion("product_issuer_id <>", value, "productIssuerId");
            return (Criteria) this;
        }

        public Criteria andProductIssuerIdGreaterThan(Long value) {
            addCriterion("product_issuer_id >", value, "productIssuerId");
            return (Criteria) this;
        }

        public Criteria andProductIssuerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("product_issuer_id >=", value, "productIssuerId");
            return (Criteria) this;
        }

        public Criteria andProductIssuerIdLessThan(Long value) {
            addCriterion("product_issuer_id <", value, "productIssuerId");
            return (Criteria) this;
        }

        public Criteria andProductIssuerIdLessThanOrEqualTo(Long value) {
            addCriterion("product_issuer_id <=", value, "productIssuerId");
            return (Criteria) this;
        }

        public Criteria andProductIssuerIdIn(List<Long> values) {
            addCriterion("product_issuer_id in", values, "productIssuerId");
            return (Criteria) this;
        }

        public Criteria andProductIssuerIdNotIn(List<Long> values) {
            addCriterion("product_issuer_id not in", values, "productIssuerId");
            return (Criteria) this;
        }

        public Criteria andProductIssuerIdBetween(Long value1, Long value2) {
            addCriterion("product_issuer_id between", value1, value2, "productIssuerId");
            return (Criteria) this;
        }

        public Criteria andProductIssuerIdNotBetween(Long value1, Long value2) {
            addCriterion("product_issuer_id not between", value1, value2, "productIssuerId");
            return (Criteria) this;
        }

        public Criteria andUnitPriceIsNull() {
            addCriterion("unit_price is null");
            return (Criteria) this;
        }

        public Criteria andUnitPriceIsNotNull() {
            addCriterion("unit_price is not null");
            return (Criteria) this;
        }

        public Criteria andUnitPriceEqualTo(Integer value) {
            addCriterion("unit_price =", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceNotEqualTo(Integer value) {
            addCriterion("unit_price <>", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceGreaterThan(Integer value) {
            addCriterion("unit_price >", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_price >=", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceLessThan(Integer value) {
            addCriterion("unit_price <", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceLessThanOrEqualTo(Integer value) {
            addCriterion("unit_price <=", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceIn(List<Integer> values) {
            addCriterion("unit_price in", values, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceNotIn(List<Integer> values) {
            addCriterion("unit_price not in", values, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceBetween(Integer value1, Integer value2) {
            addCriterion("unit_price between", value1, value2, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_price not between", value1, value2, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("sort not between", value1, value2, "sort");
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

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andExpirationStartIsNull() {
            addCriterion("expiration_start is null");
            return (Criteria) this;
        }

        public Criteria andExpirationStartIsNotNull() {
            addCriterion("expiration_start is not null");
            return (Criteria) this;
        }

        public Criteria andExpirationStartEqualTo(String value) {
            addCriterion("expiration_start =", value, "expirationStart");
            return (Criteria) this;
        }

        public Criteria andExpirationStartNotEqualTo(String value) {
            addCriterion("expiration_start <>", value, "expirationStart");
            return (Criteria) this;
        }

        public Criteria andExpirationStartGreaterThan(String value) {
            addCriterion("expiration_start >", value, "expirationStart");
            return (Criteria) this;
        }

        public Criteria andExpirationStartGreaterThanOrEqualTo(String value) {
            addCriterion("expiration_start >=", value, "expirationStart");
            return (Criteria) this;
        }

        public Criteria andExpirationStartLessThan(String value) {
            addCriterion("expiration_start <", value, "expirationStart");
            return (Criteria) this;
        }

        public Criteria andExpirationStartLessThanOrEqualTo(String value) {
            addCriterion("expiration_start <=", value, "expirationStart");
            return (Criteria) this;
        }

		public Criteria andExpirationStartLike(String value) {
            addCriterion("expiration_start like", value, "expirationStart");
            return (Criteria) this;
        }

        public Criteria andExpirationStartNotLike(String value) {
            addCriterion("expiration_start not like", value, "expirationStart");
            return (Criteria) this;
        }

        public Criteria andExpirationStartIn(List<String> values) {
            addCriterion("expiration_start in", values, "expirationStart");
            return (Criteria) this;
        }

        public Criteria andExpirationStartNotIn(List<String> values) {
            addCriterion("expiration_start not in", values, "expirationStart");
            return (Criteria) this;
        }

        public Criteria andExpirationStartBetween(String value1, String value2) {
            addCriterion("expiration_start between", value1, value2, "expirationStart");
            return (Criteria) this;
        }

        public Criteria andExpirationStartNotBetween(String value1, String value2) {
            addCriterion("expiration_start not between", value1, value2, "expirationStart");
            return (Criteria) this;
        }

        public Criteria andExpirationEndIsNull() {
            addCriterion("expiration_end is null");
            return (Criteria) this;
        }

        public Criteria andExpirationEndIsNotNull() {
            addCriterion("expiration_end is not null");
            return (Criteria) this;
        }

        public Criteria andExpirationEndEqualTo(String value) {
            addCriterion("expiration_end =", value, "expirationEnd");
            return (Criteria) this;
        }

        public Criteria andExpirationEndNotEqualTo(String value) {
            addCriterion("expiration_end <>", value, "expirationEnd");
            return (Criteria) this;
        }

        public Criteria andExpirationEndGreaterThan(String value) {
            addCriterion("( expiration_end is null or expiration_end >'"+value+"' )");
            return (Criteria) this;
        }

        public Criteria andExpirationEndGreaterThanOrEqualTo(String value) {
            addCriterion("expiration_end >=", value, "expirationEnd");
            return (Criteria) this;
        }

        public Criteria andExpirationEndLessThan(String value) {
            addCriterion("expiration_end <", value, "expirationEnd");
            return (Criteria) this;
        }

        public Criteria andExpirationEndLessThanOrEqualTo(String value) {
            addCriterion("expiration_end <=", value, "expirationEnd");
            return (Criteria) this;
        }

		public Criteria andExpirationEndLike(String value) {
            addCriterion("expiration_end like", value, "expirationEnd");
            return (Criteria) this;
        }

        public Criteria andExpirationEndNotLike(String value) {
            addCriterion("expiration_end not like", value, "expirationEnd");
            return (Criteria) this;
        }

        public Criteria andExpirationEndIn(List<String> values) {
            addCriterion("expiration_end in", values, "expirationEnd");
            return (Criteria) this;
        }

        public Criteria andExpirationEndNotIn(List<String> values) {
            addCriterion("expiration_end not in", values, "expirationEnd");
            return (Criteria) this;
        }

        public Criteria andExpirationEndBetween(String value1, String value2) {
            addCriterion("expiration_end between", value1, value2, "expirationEnd");
            return (Criteria) this;
        }

        public Criteria andExpirationEndNotBetween(String value1, String value2) {
            addCriterion("expiration_end not between", value1, value2, "expirationEnd");
            return (Criteria) this;
        }

        public Criteria andTagIdsIsNull() {
            addCriterion("tag_ids is null");
            return (Criteria) this;
        }

        public Criteria andTagIdsIsNotNull() {
            addCriterion("tag_ids is not null");
            return (Criteria) this;
        }

        public Criteria andTagIdsEqualTo(String value) {
            addCriterion("tag_ids =", value, "tagIds");
            return (Criteria) this;
        }

        public Criteria andTagIdsNotEqualTo(String value) {
            addCriterion("tag_ids <>", value, "tagIds");
            return (Criteria) this;
        }

        public Criteria andTagIdsGreaterThan(String value) {
            addCriterion("tag_ids >", value, "tagIds");
            return (Criteria) this;
        }

        public Criteria andTagIdsGreaterThanOrEqualTo(String value) {
            addCriterion("tag_ids >=", value, "tagIds");
            return (Criteria) this;
        }

        public Criteria andTagIdsLessThan(String value) {
            addCriterion("tag_ids <", value, "tagIds");
            return (Criteria) this;
        }

        public Criteria andTagIdsLessThanOrEqualTo(String value) {
            addCriterion("tag_ids <=", value, "tagIds");
            return (Criteria) this;
        }

		public Criteria andTagIdsLike(String value) {
            addCriterion("tag_ids like", value, "tagIds");
            return (Criteria) this;
        }

        public Criteria andTagIdsNotLike(String value) {
            addCriterion("tag_ids not like", value, "tagIds");
            return (Criteria) this;
        }

        public Criteria andTagIdsIn(List<String> values) {
            addCriterion("tag_ids in", values, "tagIds");
            return (Criteria) this;
        }

        public Criteria andTagIdsNotIn(List<String> values) {
            addCriterion("tag_ids not in", values, "tagIds");
            return (Criteria) this;
        }

        public Criteria andTagIdsBetween(String value1, String value2) {
            addCriterion("tag_ids between", value1, value2, "tagIds");
            return (Criteria) this;
        }

        public Criteria andTagIdsNotBetween(String value1, String value2) {
            addCriterion("tag_ids not between", value1, value2, "tagIds");
            return (Criteria) this;
        }

        public Criteria andTagNamesIsNull() {
            addCriterion("tag_names is null");
            return (Criteria) this;
        }

        public Criteria andTagNamesIsNotNull() {
            addCriterion("tag_names is not null");
            return (Criteria) this;
        }

        public Criteria andTagNamesEqualTo(String value) {
            addCriterion("tag_names =", value, "tagNames");
            return (Criteria) this;
        }

        public Criteria andTagNamesNotEqualTo(String value) {
            addCriterion("tag_names <>", value, "tagNames");
            return (Criteria) this;
        }

        public Criteria andTagNamesGreaterThan(String value) {
            addCriterion("tag_names >", value, "tagNames");
            return (Criteria) this;
        }

        public Criteria andTagNamesGreaterThanOrEqualTo(String value) {
            addCriterion("tag_names >=", value, "tagNames");
            return (Criteria) this;
        }

        public Criteria andTagNamesLessThan(String value) {
            addCriterion("tag_names <", value, "tagNames");
            return (Criteria) this;
        }

        public Criteria andTagNamesLessThanOrEqualTo(String value) {
            addCriterion("tag_names <=", value, "tagNames");
            return (Criteria) this;
        }

		public Criteria andTagNamesLike(String value) {
            addCriterion("tag_names like", value, "tagNames");
            return (Criteria) this;
        }

        public Criteria andTagNamesNotLike(String value) {
            addCriterion("tag_names not like", value, "tagNames");
            return (Criteria) this;
        }

        public Criteria andTagNamesIn(List<String> values) {
            addCriterion("tag_names in", values, "tagNames");
            return (Criteria) this;
        }

        public Criteria andTagNamesNotIn(List<String> values) {
            addCriterion("tag_names not in", values, "tagNames");
            return (Criteria) this;
        }

        public Criteria andTagNamesBetween(String value1, String value2) {
            addCriterion("tag_names between", value1, value2, "tagNames");
            return (Criteria) this;
        }

        public Criteria andTagNamesNotBetween(String value1, String value2) {
            addCriterion("tag_names not between", value1, value2, "tagNames");
            return (Criteria) this;
        }

        public Criteria andExtendInfoIsNull() {
            addCriterion("extend_info is null");
            return (Criteria) this;
        }

        public Criteria andExtendInfoIsNotNull() {
            addCriterion("extend_info is not null");
            return (Criteria) this;
        }

        public Criteria andExtendInfoEqualTo(String value) {
            addCriterion("extend_info =", value, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoNotEqualTo(String value) {
            addCriterion("extend_info <>", value, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoGreaterThan(String value) {
            addCriterion("extend_info >", value, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoGreaterThanOrEqualTo(String value) {
            addCriterion("extend_info >=", value, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoLessThan(String value) {
            addCriterion("extend_info <", value, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoLessThanOrEqualTo(String value) {
            addCriterion("extend_info <=", value, "extendInfo");
            return (Criteria) this;
        }

		public Criteria andExtendInfoLike(String value) {
            addCriterion("extend_info like", value, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoNotLike(String value) {
            addCriterion("extend_info not like", value, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoIn(List<String> values) {
            addCriterion("extend_info in", values, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoNotIn(List<String> values) {
            addCriterion("extend_info not in", values, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoBetween(String value1, String value2) {
            addCriterion("extend_info between", value1, value2, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoNotBetween(String value1, String value2) {
            addCriterion("extend_info not between", value1, value2, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNull() {
            addCriterion("remarks is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {
            addCriterion("remarks is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(String value) {
            addCriterion("remarks =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(String value) {
            addCriterion("remarks <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThan(String value) {
            addCriterion("remarks >", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("remarks >=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThan(String value) {
            addCriterion("remarks <", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThanOrEqualTo(String value) {
            addCriterion("remarks <=", value, "remarks");
            return (Criteria) this;
        }

		public Criteria andRemarksLike(String value) {
            addCriterion("remarks like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(String value) {
            addCriterion("remarks not like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(List<String> values) {
            addCriterion("remarks in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(List<String> values) {
            addCriterion("remarks not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(String value1, String value2) {
            addCriterion("remarks between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(String value1, String value2) {
            addCriterion("remarks not between", value1, value2, "remarks");
            return (Criteria) this;
        }
    }

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