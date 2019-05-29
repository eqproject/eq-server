/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.product.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * 商品信息EntityExample
 * @author kaka
 * @version 2019.05.08
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

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

		public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
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

        public Criteria andBrandIsNull() {
            addCriterion("brand is null");
            return (Criteria) this;
        }

        public Criteria andBrandIsNotNull() {
            addCriterion("brand is not null");
            return (Criteria) this;
        }

        public Criteria andBrandEqualTo(String value) {
            addCriterion("brand =", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotEqualTo(String value) {
            addCriterion("brand <>", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandGreaterThan(String value) {
            addCriterion("brand >", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandGreaterThanOrEqualTo(String value) {
            addCriterion("brand >=", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandLessThan(String value) {
            addCriterion("brand <", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandLessThanOrEqualTo(String value) {
            addCriterion("brand <=", value, "brand");
            return (Criteria) this;
        }

		public Criteria andBrandLike(String value) {
            addCriterion("brand like", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotLike(String value) {
            addCriterion("brand not like", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandIn(List<String> values) {
            addCriterion("brand in", values, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotIn(List<String> values) {
            addCriterion("brand not in", values, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandBetween(String value1, String value2) {
            addCriterion("brand between", value1, value2, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotBetween(String value1, String value2) {
            addCriterion("brand not between", value1, value2, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandImgIsNull() {
            addCriterion("brand_img is null");
            return (Criteria) this;
        }

        public Criteria andBrandImgIsNotNull() {
            addCriterion("brand_img is not null");
            return (Criteria) this;
        }

        public Criteria andBrandImgEqualTo(String value) {
            addCriterion("brand_img =", value, "brandImg");
            return (Criteria) this;
        }

        public Criteria andBrandImgNotEqualTo(String value) {
            addCriterion("brand_img <>", value, "brandImg");
            return (Criteria) this;
        }

        public Criteria andBrandImgGreaterThan(String value) {
            addCriterion("brand_img >", value, "brandImg");
            return (Criteria) this;
        }

        public Criteria andBrandImgGreaterThanOrEqualTo(String value) {
            addCriterion("brand_img >=", value, "brandImg");
            return (Criteria) this;
        }

        public Criteria andBrandImgLessThan(String value) {
            addCriterion("brand_img <", value, "brandImg");
            return (Criteria) this;
        }

        public Criteria andBrandImgLessThanOrEqualTo(String value) {
            addCriterion("brand_img <=", value, "brandImg");
            return (Criteria) this;
        }

		public Criteria andBrandImgLike(String value) {
            addCriterion("brand_img like", value, "brandImg");
            return (Criteria) this;
        }

        public Criteria andBrandImgNotLike(String value) {
            addCriterion("brand_img not like", value, "brandImg");
            return (Criteria) this;
        }

        public Criteria andBrandImgIn(List<String> values) {
            addCriterion("brand_img in", values, "brandImg");
            return (Criteria) this;
        }

        public Criteria andBrandImgNotIn(List<String> values) {
            addCriterion("brand_img not in", values, "brandImg");
            return (Criteria) this;
        }

        public Criteria andBrandImgBetween(String value1, String value2) {
            addCriterion("brand_img between", value1, value2, "brandImg");
            return (Criteria) this;
        }

        public Criteria andBrandImgNotBetween(String value1, String value2) {
            addCriterion("brand_img not between", value1, value2, "brandImg");
            return (Criteria) this;
        }

        public Criteria andBrandDescriptionIsNull() {
            addCriterion("brand_description is null");
            return (Criteria) this;
        }

        public Criteria andBrandDescriptionIsNotNull() {
            addCriterion("brand_description is not null");
            return (Criteria) this;
        }

        public Criteria andBrandDescriptionEqualTo(String value) {
            addCriterion("brand_description =", value, "brandDescription");
            return (Criteria) this;
        }

        public Criteria andBrandDescriptionNotEqualTo(String value) {
            addCriterion("brand_description <>", value, "brandDescription");
            return (Criteria) this;
        }

        public Criteria andBrandDescriptionGreaterThan(String value) {
            addCriterion("brand_description >", value, "brandDescription");
            return (Criteria) this;
        }

        public Criteria andBrandDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("brand_description >=", value, "brandDescription");
            return (Criteria) this;
        }

        public Criteria andBrandDescriptionLessThan(String value) {
            addCriterion("brand_description <", value, "brandDescription");
            return (Criteria) this;
        }

        public Criteria andBrandDescriptionLessThanOrEqualTo(String value) {
            addCriterion("brand_description <=", value, "brandDescription");
            return (Criteria) this;
        }

		public Criteria andBrandDescriptionLike(String value) {
            addCriterion("brand_description like", value, "brandDescription");
            return (Criteria) this;
        }

        public Criteria andBrandDescriptionNotLike(String value) {
            addCriterion("brand_description not like", value, "brandDescription");
            return (Criteria) this;
        }

        public Criteria andBrandDescriptionIn(List<String> values) {
            addCriterion("brand_description in", values, "brandDescription");
            return (Criteria) this;
        }

        public Criteria andBrandDescriptionNotIn(List<String> values) {
            addCriterion("brand_description not in", values, "brandDescription");
            return (Criteria) this;
        }

        public Criteria andBrandDescriptionBetween(String value1, String value2) {
            addCriterion("brand_description between", value1, value2, "brandDescription");
            return (Criteria) this;
        }

        public Criteria andBrandDescriptionNotBetween(String value1, String value2) {
            addCriterion("brand_description not between", value1, value2, "brandDescription");
            return (Criteria) this;
        }

        public Criteria andBrandTeleIsNull() {
            addCriterion("brand_tele is null");
            return (Criteria) this;
        }

        public Criteria andBrandTeleIsNotNull() {
            addCriterion("brand_tele is not null");
            return (Criteria) this;
        }

        public Criteria andBrandTeleEqualTo(String value) {
            addCriterion("brand_tele =", value, "brandTele");
            return (Criteria) this;
        }

        public Criteria andBrandTeleNotEqualTo(String value) {
            addCriterion("brand_tele <>", value, "brandTele");
            return (Criteria) this;
        }

        public Criteria andBrandTeleGreaterThan(String value) {
            addCriterion("brand_tele >", value, "brandTele");
            return (Criteria) this;
        }

        public Criteria andBrandTeleGreaterThanOrEqualTo(String value) {
            addCriterion("brand_tele >=", value, "brandTele");
            return (Criteria) this;
        }

        public Criteria andBrandTeleLessThan(String value) {
            addCriterion("brand_tele <", value, "brandTele");
            return (Criteria) this;
        }

        public Criteria andBrandTeleLessThanOrEqualTo(String value) {
            addCriterion("brand_tele <=", value, "brandTele");
            return (Criteria) this;
        }

		public Criteria andBrandTeleLike(String value) {
            addCriterion("brand_tele like", value, "brandTele");
            return (Criteria) this;
        }

        public Criteria andBrandTeleNotLike(String value) {
            addCriterion("brand_tele not like", value, "brandTele");
            return (Criteria) this;
        }

        public Criteria andBrandTeleIn(List<String> values) {
            addCriterion("brand_tele in", values, "brandTele");
            return (Criteria) this;
        }

        public Criteria andBrandTeleNotIn(List<String> values) {
            addCriterion("brand_tele not in", values, "brandTele");
            return (Criteria) this;
        }

        public Criteria andBrandTeleBetween(String value1, String value2) {
            addCriterion("brand_tele between", value1, value2, "brandTele");
            return (Criteria) this;
        }

        public Criteria andBrandTeleNotBetween(String value1, String value2) {
            addCriterion("brand_tele not between", value1, value2, "brandTele");
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

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

		public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andReceiveIsNull() {
            addCriterion("receive is null");
            return (Criteria) this;
        }

        public Criteria andReceiveIsNotNull() {
            addCriterion("receive is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveEqualTo(String value) {
            addCriterion("receive =", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveNotEqualTo(String value) {
            addCriterion("receive <>", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveGreaterThan(String value) {
            addCriterion("receive >", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveGreaterThanOrEqualTo(String value) {
            addCriterion("receive >=", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveLessThan(String value) {
            addCriterion("receive <", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveLessThanOrEqualTo(String value) {
            addCriterion("receive <=", value, "receive");
            return (Criteria) this;
        }

		public Criteria andReceiveLike(String value) {
            addCriterion("receive like", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveNotLike(String value) {
            addCriterion("receive not like", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveIn(List<String> values) {
            addCriterion("receive in", values, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveNotIn(List<String> values) {
            addCriterion("receive not in", values, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveBetween(String value1, String value2) {
            addCriterion("receive between", value1, value2, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveNotBetween(String value1, String value2) {
            addCriterion("receive not between", value1, value2, "receive");
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

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(Long value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(Long value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(Long value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(Long value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(Long value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(Long value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<Long> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<Long> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(Long value1, Long value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(Long value1, Long value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
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

        public Criteria andUpdateByIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(Long value) {
            addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(Long value) {
            addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(Long value) {
            addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(Long value) {
            addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(Long value) {
            addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(Long value) {
            addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<Long> values) {
            addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<Long> values) {
            addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(Long value1, Long value2) {
            addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(Long value1, Long value2) {
            addCriterion("update_by not between", value1, value2, "updateBy");
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
            addCriterion("expiration_end >", value, "expirationEnd");
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