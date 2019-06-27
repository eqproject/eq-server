/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.entity;

import org.apache.commons.collections.CollectionUtils;
import org.eq.modules.enums.OrderTradeStateEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单交易EntityExample
 * @author yufei.sun
 * @version 1.0
 */
public class OrderTradeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderTradeExample() {
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

        public Criteria andIdEqualToForAll(Long value) {
            addCriterion("OT.id =", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdEqualToForUpdate(Long value) {
            addCriterion("id =", value, "id");
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

        public Criteria andSellUserIdIsNull() {
            addCriterion("sell_user_id is null");
            return (Criteria) this;
        }

        public Criteria andSellUserIdIsNotNull() {
            addCriterion("sell_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andSellUserIdEqualTo(Long value) {
            addCriterion("sell_user_id =", value, "sellUserId");
            return (Criteria) this;
        }

        public Criteria andSellUserIdNotEqualTo(Long value) {
            addCriterion("sell_user_id <>", value, "sellUserId");
            return (Criteria) this;
        }

        public Criteria andSellUserIdGreaterThan(Long value) {
            addCriterion("sell_user_id >", value, "sellUserId");
            return (Criteria) this;
        }

        public Criteria andSellUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("sell_user_id >=", value, "sellUserId");
            return (Criteria) this;
        }

        public Criteria andSellUserIdLessThan(Long value) {
            addCriterion("sell_user_id <", value, "sellUserId");
            return (Criteria) this;
        }

        public Criteria andSellUserIdLessThanOrEqualTo(Long value) {
            addCriterion("sell_user_id <=", value, "sellUserId");
            return (Criteria) this;
        }

        public Criteria andSellUserIdIn(List<Long> values) {
            addCriterion("sell_user_id in", values, "sellUserId");
            return (Criteria) this;
        }

        public Criteria andSellUserIdNotIn(List<Long> values) {
            addCriterion("sell_user_id not in", values, "sellUserId");
            return (Criteria) this;
        }

        public Criteria andSellUserIdBetween(Long value1, Long value2) {
            addCriterion("sell_user_id between", value1, value2, "sellUserId");
            return (Criteria) this;
        }

        public Criteria andSellUserIdNotBetween(Long value1, Long value2) {
            addCriterion("sell_user_id not between", value1, value2, "sellUserId");
            return (Criteria) this;
        }

        public Criteria andBuyUserIdIsNull() {
            addCriterion("buy_user_id is null");
            return (Criteria) this;
        }

        public Criteria andBuyUserIdIsNotNull() {
            addCriterion("buy_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andBuyUserIdEqualTo(Long value) {
            addCriterion("buy_user_id =", value, "buyUserId");
            return (Criteria) this;
        }

        public Criteria andNoWaitBuyUserIdForAll(Long value,List<Integer> status) {
            StringBuilder stateBuffer = new StringBuilder("(-1 ");
            if(!CollectionUtils.isEmpty(status)){
                for(Integer state : status){
                    stateBuffer.append(",").append(state);
                }
            }
            stateBuffer.append( " )");

            addCriterion(" (  OT.status in  "+ stateBuffer+ " and  buy_user_id =  "+ value  +")");
            return (Criteria) this;
        }


        public Criteria andAllUserIdEqualTo(long value) {
            addCriterion("(buy_user_id = " + value + " or  sell_user_id = " + value + " )");
            return (Criteria) this;
        }

        public Criteria andBuyUserIdNotEqualTo(Long value) {
            addCriterion("buy_user_id <>", value, "buyUserId");
            return (Criteria) this;
        }

        public Criteria andBuyUserIdGreaterThan(Long value) {
            addCriterion("buy_user_id >", value, "buyUserId");
            return (Criteria) this;
        }

        public Criteria andBuyUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("buy_user_id >=", value, "buyUserId");
            return (Criteria) this;
        }

        public Criteria andBuyUserIdLessThan(Long value) {
            addCriterion("buy_user_id <", value, "buyUserId");
            return (Criteria) this;
        }

        public Criteria andBuyUserIdLessThanOrEqualTo(Long value) {
            addCriterion("buy_user_id <=", value, "buyUserId");
            return (Criteria) this;
        }

        public Criteria andBuyUserIdIn(List<Long> values) {
            addCriterion("buy_user_id in", values, "buyUserId");
            return (Criteria) this;
        }

        public Criteria andBuyUserIdNotIn(List<Long> values) {
            addCriterion("buy_user_id not in", values, "buyUserId");
            return (Criteria) this;
        }

        public Criteria andBuyUserIdBetween(Long value1, Long value2) {
            addCriterion("buy_user_id between", value1, value2, "buyUserId");
            return (Criteria) this;
        }

        public Criteria andBuyUserIdNotBetween(Long value1, Long value2) {
            addCriterion("buy_user_id not between", value1, value2, "buyUserId");
            return (Criteria) this;
        }

        public Criteria andAdNoIsNull() {
            addCriterion("ad_no is null");
            return (Criteria) this;
        }

        public Criteria andAdNoIsNotNull() {
            addCriterion("ad_no is not null");
            return (Criteria) this;
        }

        public Criteria andAdNoEqualTo(String value) {
            addCriterion("ad_no =", value, "adNo");
            return (Criteria) this;
        }

        public Criteria andAdNoNotEqualTo(String value) {
            addCriterion("ad_no <>", value, "adNo");
            return (Criteria) this;
        }

        public Criteria andAdNoGreaterThan(String value) {
            addCriterion("ad_no >", value, "adNo");
            return (Criteria) this;
        }

        public Criteria andAdNoGreaterThanOrEqualTo(String value) {
            addCriterion("ad_no >=", value, "adNo");
            return (Criteria) this;
        }

        public Criteria andAdNoLessThan(String value) {
            addCriterion("ad_no <", value, "adNo");
            return (Criteria) this;
        }

        public Criteria andAdNoLessThanOrEqualTo(String value) {
            addCriterion("ad_no <=", value, "adNo");
            return (Criteria) this;
        }

        public Criteria andAdNoLike(String value) {
            addCriterion("ad_no like", value, "adNo");
            return (Criteria) this;
        }

        public Criteria andAdNoNotLike(String value) {
            addCriterion("ad_no not like", value, "adNo");
            return (Criteria) this;
        }

        public Criteria andAdNoIn(List<String> values) {
            addCriterion("ad_no in", values, "adNo");
            return (Criteria) this;
        }

        public Criteria andAdNoNotIn(List<String> values) {
            addCriterion("ad_no not in", values, "adNo");
            return (Criteria) this;
        }

        public Criteria andAdNoBetween(String value1, String value2) {
            addCriterion("ad_no between", value1, value2, "adNo");
            return (Criteria) this;
        }

        public Criteria andAdNoNotBetween(String value1, String value2) {
            addCriterion("ad_no not between", value1, value2, "adNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoIsNull() {
            addCriterion("trade_no is null");
            return (Criteria) this;
        }

        public Criteria andTradeNoIsNotNull() {
            addCriterion("trade_no is not null");
            return (Criteria) this;
        }

        public Criteria andTradeNoEqualTo(String value) {
            addCriterion("trade_no =", value, "tradeNo");
            return (Criteria) this;
        }
        public Criteria andTradeNoEqualToForAll(String value) {
            addCriterion("OT.trade_no =", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoNotEqualTo(String value) {
            addCriterion("trade_no <>", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoGreaterThan(String value) {
            addCriterion("trade_no >", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoGreaterThanOrEqualTo(String value) {
            addCriterion("trade_no >=", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoLessThan(String value) {
            addCriterion("trade_no <", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoLessThanOrEqualTo(String value) {
            addCriterion("trade_no <=", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoLike(String value) {
            addCriterion("trade_no like", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoNotLike(String value) {
            addCriterion("trade_no not like", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoIn(List<String> values) {
            addCriterion("trade_no in", values, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoNotIn(List<String> values) {
            addCriterion("trade_no not in", values, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoBetween(String value1, String value2) {
            addCriterion("trade_no between", value1, value2, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoNotBetween(String value1, String value2) {
            addCriterion("trade_no not between", value1, value2, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNull() {
            addCriterion("product_id is null");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("product_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(Long value) {
            addCriterion("product_id =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotEqualTo(Long value) {
            addCriterion("product_id <>", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThan(Long value) {
            addCriterion("product_id >", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(Long value) {
            addCriterion("product_id >=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThan(Long value) {
            addCriterion("product_id <", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThanOrEqualTo(Long value) {
            addCriterion("product_id <=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdIn(List<Long> values) {
            addCriterion("product_id in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotIn(List<Long> values) {
            addCriterion("product_id not in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdBetween(Long value1, Long value2) {
            addCriterion("product_id between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotBetween(Long value1, Long value2) {
            addCriterion("product_id not between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andOrderNumIsNull() {
            addCriterion("order_num is null");
            return (Criteria) this;
        }

        public Criteria andOrderNumIsNotNull() {
            addCriterion("order_num is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNumEqualTo(Integer value) {
            addCriterion("order_num =", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotEqualTo(Integer value) {
            addCriterion("order_num <>", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThan(Integer value) {
            addCriterion("order_num >", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_num >=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThan(Integer value) {
            addCriterion("order_num <", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThanOrEqualTo(Integer value) {
            addCriterion("order_num <=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumIn(List<Integer> values) {
            addCriterion("order_num in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotIn(List<Integer> values) {
            addCriterion("order_num not in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumBetween(Integer value1, Integer value2) {
            addCriterion("order_num between", value1, value2, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotBetween(Integer value1, Integer value2) {
            addCriterion("order_num not between", value1, value2, "orderNum");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
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

        public Criteria andStatusEqualToForUpdate(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }
        public Criteria andStatusEqualToForAll(Integer value) {
            addCriterion("OT.status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }



        public Criteria andStatusInForUpdate(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }
        public Criteria andStatusInForAll(List<Integer> values) {
            addCriterion("OT.status in", values, "status");
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

        public Criteria andBlockchainStatusIsNull() {
            addCriterion("blockchain_status is null");
            return (Criteria) this;
        }

        public Criteria andBlockchainStatusIsNotNull() {
            addCriterion("blockchain_status is not null");
            return (Criteria) this;
        }

        public Criteria andBlockchainStatusEqualTo(Integer value) {
            addCriterion("blockchain_status =", value, "blockchainStatus");
            return (Criteria) this;
        }

        public Criteria andBlockchainStatusNotEqualTo(Integer value) {
            addCriterion("blockchain_status <>", value, "blockchainStatus");
            return (Criteria) this;
        }

        public Criteria andBlockchainStatusGreaterThan(Integer value) {
            addCriterion("blockchain_status >", value, "blockchainStatus");
            return (Criteria) this;
        }

        public Criteria andBlockchainStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("blockchain_status >=", value, "blockchainStatus");
            return (Criteria) this;
        }

        public Criteria andBlockchainStatusLessThan(Integer value) {
            addCriterion("blockchain_status <", value, "blockchainStatus");
            return (Criteria) this;
        }

        public Criteria andBlockchainStatusLessThanOrEqualTo(Integer value) {
            addCriterion("blockchain_status <=", value, "blockchainStatus");
            return (Criteria) this;
        }

        public Criteria andBlockchainStatusIn(List<Integer> values) {
            addCriterion("blockchain_status in", values, "blockchainStatus");
            return (Criteria) this;
        }

        public Criteria andBlockchainStatusNotIn(List<Integer> values) {
            addCriterion("blockchain_status not in", values, "blockchainStatus");
            return (Criteria) this;
        }

        public Criteria andBlockchainStatusBetween(Integer value1, Integer value2) {
            addCriterion("blockchain_status between", value1, value2, "blockchainStatus");
            return (Criteria) this;
        }

        public Criteria andBlockchainStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("blockchain_status not between", value1, value2, "blockchainStatus");
            return (Criteria) this;
        }

        public Criteria andSalePriceIsNull() {
            addCriterion("sale_price is null");
            return (Criteria) this;
        }

        public Criteria andSalePriceIsNotNull() {
            addCriterion("sale_price is not null");
            return (Criteria) this;
        }

        public Criteria andSalePriceEqualTo(Integer value) {
            addCriterion("sale_price =", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceNotEqualTo(Integer value) {
            addCriterion("sale_price <>", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceGreaterThan(Integer value) {
            addCriterion("sale_price >", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("sale_price >=", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceLessThan(Integer value) {
            addCriterion("sale_price <", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceLessThanOrEqualTo(Integer value) {
            addCriterion("sale_price <=", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceIn(List<Integer> values) {
            addCriterion("sale_price in", values, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceNotIn(List<Integer> values) {
            addCriterion("sale_price not in", values, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceBetween(Integer value1, Integer value2) {
            addCriterion("sale_price between", value1, value2, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceNotBetween(Integer value1, Integer value2) {
            addCriterion("sale_price not between", value1, value2, "salePrice");
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

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Integer value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Integer value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Integer value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Integer value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Integer value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Integer> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Integer> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Integer value1, Integer value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andRemindPayIsNull() {
            addCriterion("remind_pay is null");
            return (Criteria) this;
        }

        public Criteria andRemindPayIsNotNull() {
            addCriterion("remind_pay is not null");
            return (Criteria) this;
        }

        public Criteria andRemindPayEqualTo(Integer value) {
            addCriterion("remind_pay =", value, "remindPay");
            return (Criteria) this;
        }

        public Criteria andRemindPayNotEqualTo(Integer value) {
            addCriterion("remind_pay <>", value, "remindPay");
            return (Criteria) this;
        }

        public Criteria andRemindPayGreaterThan(Integer value) {
            addCriterion("remind_pay >", value, "remindPay");
            return (Criteria) this;
        }

        public Criteria andRemindPayGreaterThanOrEqualTo(Integer value) {
            addCriterion("remind_pay >=", value, "remindPay");
            return (Criteria) this;
        }

        public Criteria andRemindPayLessThan(Integer value) {
            addCriterion("remind_pay <", value, "remindPay");
            return (Criteria) this;
        }

        public Criteria andRemindPayLessThanOrEqualTo(Integer value) {
            addCriterion("remind_pay <=", value, "remindPay");
            return (Criteria) this;
        }

        public Criteria andRemindPayIn(List<Integer> values) {
            addCriterion("remind_pay in", values, "remindPay");
            return (Criteria) this;
        }

        public Criteria andRemindPayNotIn(List<Integer> values) {
            addCriterion("remind_pay not in", values, "remindPay");
            return (Criteria) this;
        }

        public Criteria andRemindPayBetween(Integer value1, Integer value2) {
            addCriterion("remind_pay between", value1, value2, "remindPay");
            return (Criteria) this;
        }

        public Criteria andRemindPayNotBetween(Integer value1, Integer value2) {
            addCriterion("remind_pay not between", value1, value2, "remindPay");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNull() {
            addCriterion("finish_time is null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNotNull() {
            addCriterion("finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeEqualTo(Date value) {
            addCriterion("finish_time =", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotEqualTo(Date value) {
            addCriterion("finish_time <>", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThan(Date value) {
            addCriterion("finish_time >", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("finish_time >=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThan(Date value) {
            addCriterion("finish_time <", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThanOrEqualTo(Date value) {
            addCriterion("finish_time <=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIn(List<Date> values) {
            addCriterion("finish_time in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotIn(List<Date> values) {
            addCriterion("finish_time not in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeBetween(Date value1, Date value2) {
            addCriterion("finish_time between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotBetween(Date value1, Date value2) {
            addCriterion("finish_time not between", value1, value2, "finishTime");
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

        public Criteria andCreateDateLessThanForUpdate(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }
        public Criteria andCreateDateLessThanForALL(Date value) {
            addCriterion("OT.create_date <", value, "createDate");
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

        public Criteria andUpdateDateLessThanForUpdate(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }
        public Criteria andUpdateDateLessThanForAll(Date value) {
            addCriterion("OT.update_date <", value, "updateDate");
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

        public Criteria andUpdateDateBetweenForUpdate(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetweenForAll(Date value1, Date value2) {
            addCriterion("OT.update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andTxIdIsNull() {
            addCriterion("tx_id is null");
            return (Criteria) this;
        }

        public Criteria andTxIdIsNotNull() {
            addCriterion("tx_id is not null");
            return (Criteria) this;
        }

        public Criteria andTxIdEqualTo(Long value) {
            addCriterion("tx_id =", value, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdNotEqualTo(Long value) {
            addCriterion("tx_id <>", value, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdGreaterThan(Long value) {
            addCriterion("tx_id >", value, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdGreaterThanOrEqualTo(Long value) {
            addCriterion("tx_id >=", value, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdLessThan(Long value) {
            addCriterion("tx_id <", value, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdLessThanOrEqualTo(Long value) {
            addCriterion("tx_id <=", value, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdIn(List<Long> values) {
            addCriterion("tx_id in", values, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdNotIn(List<Long> values) {
            addCriterion("tx_id not in", values, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdBetween(Long value1, Long value2) {
            addCriterion("tx_id between", value1, value2, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdNotBetween(Long value1, Long value2) {
            addCriterion("tx_id not between", value1, value2, "txId");
            return (Criteria) this;
        }

        public Criteria andProductNameLikeForAll(String value) {
            addCriterion("PP.name like  ", "%" + value + "%", "productName");
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