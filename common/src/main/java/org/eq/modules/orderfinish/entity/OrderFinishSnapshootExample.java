/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.orderfinish.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户完成订单EntityExample
 * @author gb
 * @version 2019-06-02
 */
public class OrderFinishSnapshootExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderFinishSnapshootExample() {
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


        public Criteria andIdEqualToForAll(Long value) {
            addCriterion("OFS.id =", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdEqualToForUpdate(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andSellUserIdEqualToForAll(long value) {
            addCriterion("OFS.sell_user_id =", value, "sellUserId");
            return (Criteria) this;
        }
        public Criteria andSellUserIdEqualToForUpdate(long value) {
            addCriterion("sell_user_id =", value, "sellUserId");
            return (Criteria) this;
        }
        public Criteria andBuyUserIdEqualToForAll(long value) {
            addCriterion("OFS.buy_user_id =", value, "buyUserId");
            return (Criteria) this;
        }

        public Criteria andAllUserIdEqualToForAll(long value) {
            addCriterion("( OFS.buy_user_id = "+value +" or  OFS.sell_user_id = "+value + " )" );
            return (Criteria) this;
        }

        public Criteria andBuyUserIdEqualToForUpdate(long value) {
            addCriterion("buy_user_id =", value, "buyUserId");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualToForAll(Long value) {
            addCriterion("OFS.product_id =", value, "productId");
            return (Criteria) this;
        }
        public Criteria andProductIdEqualToForUpdate(Long value) {
            addCriterion("product_id =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andTypeEqualToForAll(Integer value) {
            addCriterion("OFS.type =", value, "type");
            return (Criteria) this;
        }
        public Criteria andTypeEqualToForUpdate(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andStatusEqualToForAll(Integer value) {
            addCriterion("OFS.status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusEqualToForUpdate(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualToForAll(String value) {
            addCriterion("OFS.order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoEqualToForAll(String value) {
            addCriterion("OFS.trade_no =", value, "tradeNo");
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