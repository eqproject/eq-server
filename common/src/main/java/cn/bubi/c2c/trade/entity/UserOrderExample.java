/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.trade.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 管理用户个人订单EntityExample
 * @author kaka
 * @version 1.0.0
 */
public class UserOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserOrderExample() {
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
            addCriterion(" op.id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion(" op.id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion(" op.id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdEqualToForUpdate(Long value) {
            addCriterion(" id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion(" op.id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion(" op.id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion(" op.id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion(" op.id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion(" op.id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion(" op.id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion(" op.id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion(" op.id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion(" op.id not between", value1, value2, "id");
            return (Criteria) this;
        }


        public Criteria andOrderNoEqualTo(String value) {
            addCriterion(" op.order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(Long value) {
            addCriterion(" op.product_id =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductNumIsNull() {
            addCriterion("product_num is null");
            return (Criteria) this;
        }

        public Criteria andProductNumIsNotNull() {
            addCriterion("product_num is not null");
            return (Criteria) this;
        }

        public Criteria andProductNumEqualTo(Integer value) {
            addCriterion("product_num =", value, "productNum");
            return (Criteria) this;
        }

        public Criteria andProductNumNotEqualTo(Integer value) {
            addCriterion("product_num <>", value, "productNum");
            return (Criteria) this;
        }

        public Criteria andProductNumGreaterThan(Integer value) {
            addCriterion("product_num >", value, "productNum");
            return (Criteria) this;
        }

        public Criteria andProductNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("product_num >=", value, "productNum");
            return (Criteria) this;
        }

        public Criteria andProductNumLessThan(Integer value) {
            addCriterion("product_num <", value, "productNum");
            return (Criteria) this;
        }

        public Criteria andProductNumLessThanOrEqualTo(Integer value) {
            addCriterion("product_num <=", value, "productNum");
            return (Criteria) this;
        }

        public Criteria andProductNumIn(List<Integer> values) {
            addCriterion("product_num in", values, "productNum");
            return (Criteria) this;
        }

        public Criteria andProductNumNotIn(List<Integer> values) {
            addCriterion("product_num not in", values, "productNum");
            return (Criteria) this;
        }

        public Criteria andProductNumBetween(Integer value1, Integer value2) {
            addCriterion("product_num between", value1, value2, "productNum");
            return (Criteria) this;
        }

        public Criteria andProductNumNotBetween(Integer value1, Integer value2) {
            addCriterion("product_num not between", value1, value2, "productNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumIsNull() {
            addCriterion("trade_num is null");
            return (Criteria) this;
        }

        public Criteria andTradeNumIsNotNull() {
            addCriterion("trade_num is not null");
            return (Criteria) this;
        }

        public Criteria andTradeNumEqualTo(Integer value) {
            addCriterion("trade_num =", value, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumNotEqualTo(Integer value) {
            addCriterion("trade_num <>", value, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumGreaterThan(Integer value) {
            addCriterion("trade_num >", value, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("trade_num >=", value, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumLessThan(Integer value) {
            addCriterion("trade_num <", value, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumLessThanOrEqualTo(Integer value) {
            addCriterion("trade_num <=", value, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumIn(List<Integer> values) {
            addCriterion("trade_num in", values, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumNotIn(List<Integer> values) {
            addCriterion("trade_num not in", values, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumBetween(Integer value1, Integer value2) {
            addCriterion("trade_num between", value1, value2, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumNotBetween(Integer value1, Integer value2) {
            addCriterion("trade_num not between", value1, value2, "tradeNum");
            return (Criteria) this;
        }

       
        public Criteria andTypeEqualTo(Integer value) {
            addCriterion(" op.type =", value, "type");
            return (Criteria) this;
        }

      
        public Criteria andStatusEqualTo(Integer value) {
            addCriterion(" op.status =", value, "status");
            return (Criteria) this;
        }
        public Criteria andStatusEqualToForUpdate(Integer value) {
            addCriterion(" status =", value, "status");
            return (Criteria) this;
        }
       

        public Criteria andPriceIsNull() {
            addCriterion(" op.price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion(" op.price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(Integer value) {
            addCriterion(" op.price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(Integer value) {
            addCriterion(" op.price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(Integer value) {
            addCriterion(" op.price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion(" op.price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(Integer value) {
            addCriterion(" op.price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(Integer value) {
            addCriterion(" op.price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<Integer> values) {
            addCriterion(" op.price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<Integer> values) {
            addCriterion(" op.price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(Integer value1, Integer value2) {
            addCriterion(" op.price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(Integer value1, Integer value2) {
            addCriterion(" op.price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion(" op.amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion(" op.amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Long value) {
            addCriterion(" op.amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Long value) {
            addCriterion(" op.amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Long value) {
            addCriterion(" op.amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Long value) {
            addCriterion(" op.amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Long value) {
            addCriterion(" op.amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Long value) {
            addCriterion(" op.amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Long> values) {
            addCriterion("op.amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Long> values) {
            addCriterion("op.amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Long value1, Long value2) {
            addCriterion("op.amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Long value1, Long value2) {
            addCriterion("op.amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        
        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
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

        public Criteria andCancelDescIsNull() {
            addCriterion("cancel_desc is null");
            return (Criteria) this;
        }

        public Criteria andCancelDescIsNotNull() {
            addCriterion("cancel_desc is not null");
            return (Criteria) this;
        }

        public Criteria andCancelDescEqualTo(String value) {
            addCriterion("cancel_desc =", value, "cancelDesc");
            return (Criteria) this;
        }

        public Criteria andCancelDescNotEqualTo(String value) {
            addCriterion("cancel_desc <>", value, "cancelDesc");
            return (Criteria) this;
        }

        public Criteria andCancelDescGreaterThan(String value) {
            addCriterion("cancel_desc >", value, "cancelDesc");
            return (Criteria) this;
        }

        public Criteria andCancelDescGreaterThanOrEqualTo(String value) {
            addCriterion("cancel_desc >=", value, "cancelDesc");
            return (Criteria) this;
        }

        public Criteria andCancelDescLessThan(String value) {
            addCriterion("cancel_desc <", value, "cancelDesc");
            return (Criteria) this;
        }

        public Criteria andCancelDescLessThanOrEqualTo(String value) {
            addCriterion("cancel_desc <=", value, "cancelDesc");
            return (Criteria) this;
        }

		public Criteria andCancelDescLike(String value) {
            addCriterion("cancel_desc like", value, "cancelDesc");
            return (Criteria) this;
        }

        public Criteria andCancelDescNotLike(String value) {
            addCriterion("cancel_desc not like", value, "cancelDesc");
            return (Criteria) this;
        }

        public Criteria andCancelDescIn(List<String> values) {
            addCriterion("cancel_desc in", values, "cancelDesc");
            return (Criteria) this;
        }

        public Criteria andCancelDescNotIn(List<String> values) {
            addCriterion("cancel_desc not in", values, "cancelDesc");
            return (Criteria) this;
        }

        public Criteria andCancelDescBetween(String value1, String value2) {
            addCriterion("cancel_desc between", value1, value2, "cancelDesc");
            return (Criteria) this;
        }

        public Criteria andCancelDescNotBetween(String value1, String value2) {
            addCriterion("cancel_desc not between", value1, value2, "cancelDesc");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("op.create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("op.create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(Long value) {
            addCriterion("op.create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(Long value) {
            addCriterion("op.create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(Long value) {
            addCriterion("op.create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(Long value) {
            addCriterion("op.create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(Long value) {
            addCriterion("op.create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(Long value) {
            addCriterion("op.create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<Long> values) {
            addCriterion("op.create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<Long> values) {
            addCriterion("op.create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(Long value1, Long value2) {
            addCriterion("op.create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(Long value1, Long value2) {
            addCriterion("op.create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("op.create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("op.create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("op.create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("op.create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("op.create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("op.create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("op.create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("op.create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("op.create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("op.create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("op.create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("op.create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNull() {
            addCriterion("op.update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("op.update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(Long value) {
            addCriterion("op.update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(Long value) {
            addCriterion("op.update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(Long value) {
            addCriterion("op.update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(Long value) {
            addCriterion("op.update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(Long value) {
            addCriterion("op.update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(Long value) {
            addCriterion("op.update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<Long> values) {
            addCriterion("op.update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<Long> values) {
            addCriterion("op.update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(Long value1, Long value2) {
            addCriterion("op.update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(Long value1, Long value2) {
            addCriterion("op.update_by not between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("op.update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("op.update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("op.update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("op.update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("op.update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("op.update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("op.update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("op.update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("op.update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("op.update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("op.update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("op.update_date not between", value1, value2, "updateDate");
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
        public Criteria andProductCodeEqualTo(String value) {
            addCriterion("p.code =", value, "productCode");
            return (Criteria) this;
        }
        public Criteria andProductNameLike(String value) {
            addCriterion("p.name like ", "%"+value+"%", "productName");
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