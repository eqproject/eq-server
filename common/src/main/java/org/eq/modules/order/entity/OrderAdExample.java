/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * 广告订单EntityExample
 * @author kaka
 * @version 1.0.1
 */
public class OrderAdExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderAdExample() {
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


        public Criteria andIdEqualToForUpdate(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdEqualToForAll(Long value) {
            addCriterion("OA.id =", value, "id");
            return (Criteria) this;
        }



        public Criteria andUserIdEqualToForUpdate(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }
        public Criteria andUserIdEqualToForAll(Long value) {
            addCriterion("OA.user_id =", value, "userId");
            return (Criteria) this;
        }


        public Criteria andOrderNoEqualToForUpdate(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }
        public Criteria andOrderNoEqualToForAll(String value) {
            addCriterion("OA.order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualToForUpdate(Long value) {
            addCriterion("product_id =", value, "productId");
            return (Criteria) this;
        }
        public Criteria andProductIdEqualToForAll(Long value) {
            addCriterion("OA.product_id =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdInForUpdate(List<Long> values) {
            addCriterion("product_id in", values, "productId");
            return (Criteria) this;
        }
        public Criteria andProductIdInForAll(List<Long> values) {
            addCriterion("OA.product_id in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductNumEqualToForUpdate(Integer value) {
            addCriterion("product_num =", value, "productNum");
            return (Criteria) this;
        }
        public Criteria andProductNumEqualToForAll(Integer value) {
            addCriterion("OA.product_num =", value, "productNum");
            return (Criteria) this;
        }

        public Criteria andTradingNumEqualToForUpdate(Integer value) {
            addCriterion("trading_num =", value, "tradingNum");
            return (Criteria) this;
        }
        public Criteria andTradingNumEqualToForAll(Integer value) {
            addCriterion("OA.trading_num =", value, "tradingNum");
            return (Criteria) this;
        }

        public Criteria andTradedNumEqualToForUpdate(Integer value) {
            addCriterion("traded_num =", value, "tradedNum");
            return (Criteria) this;
        }
        public Criteria andTradedNumEqualToForAll(Integer value) {
            addCriterion("OA.traded_num =", value, "tradedNum");
            return (Criteria) this;
        }

        public Criteria andTypeEqualToForUpdate(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }
        public Criteria andTypeEqualToForAll(Integer value) {
            addCriterion("OA.type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andStatusEqualToForUpdate(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }
        public Criteria andStatusEqualToForALL(Integer value) {
            addCriterion("OA.status =", value, "status");
            return (Criteria) this;
        }


        public Criteria andStatusNotEqualToForUpdate(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }
        public Criteria andStatusNotEqualToForAll(Integer value) {
            addCriterion("OA.status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusInForUpdate(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }
        public Criteria andStatusInForAll(List<Integer> values) {
            addCriterion("OA.status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotInForUpdate(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }
        public Criteria andStatusNotInForAll(List<Integer> values) {
            addCriterion("OA.status not in", values, "status");
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