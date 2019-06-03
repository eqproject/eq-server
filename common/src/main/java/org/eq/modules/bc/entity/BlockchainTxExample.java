/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.bc.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * 区块链任务EntityExample
 * @author gb
 * @version 2019-06-03
 */
public class BlockchainTxExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BlockchainTxExample() {
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

        public Criteria andBcErrorCodeIsNull() {
            addCriterion("bc_error_code is null");
            return (Criteria) this;
        }

        public Criteria andBcErrorCodeIsNotNull() {
            addCriterion("bc_error_code is not null");
            return (Criteria) this;
        }

        public Criteria andBcErrorCodeEqualTo(String value) {
            addCriterion("bc_error_code =", value, "bcErrorCode");
            return (Criteria) this;
        }

        public Criteria andBcErrorCodeNotEqualTo(String value) {
            addCriterion("bc_error_code <>", value, "bcErrorCode");
            return (Criteria) this;
        }

        public Criteria andBcErrorCodeGreaterThan(String value) {
            addCriterion("bc_error_code >", value, "bcErrorCode");
            return (Criteria) this;
        }

        public Criteria andBcErrorCodeGreaterThanOrEqualTo(String value) {
            addCriterion("bc_error_code >=", value, "bcErrorCode");
            return (Criteria) this;
        }

        public Criteria andBcErrorCodeLessThan(String value) {
            addCriterion("bc_error_code <", value, "bcErrorCode");
            return (Criteria) this;
        }

        public Criteria andBcErrorCodeLessThanOrEqualTo(String value) {
            addCriterion("bc_error_code <=", value, "bcErrorCode");
            return (Criteria) this;
        }

		public Criteria andBcErrorCodeLike(String value) {
            addCriterion("bc_error_code like", value, "bcErrorCode");
            return (Criteria) this;
        }

        public Criteria andBcErrorCodeNotLike(String value) {
            addCriterion("bc_error_code not like", value, "bcErrorCode");
            return (Criteria) this;
        }

        public Criteria andBcErrorCodeIn(List<String> values) {
            addCriterion("bc_error_code in", values, "bcErrorCode");
            return (Criteria) this;
        }

        public Criteria andBcErrorCodeNotIn(List<String> values) {
            addCriterion("bc_error_code not in", values, "bcErrorCode");
            return (Criteria) this;
        }

        public Criteria andBcErrorCodeBetween(String value1, String value2) {
            addCriterion("bc_error_code between", value1, value2, "bcErrorCode");
            return (Criteria) this;
        }

        public Criteria andBcErrorCodeNotBetween(String value1, String value2) {
            addCriterion("bc_error_code not between", value1, value2, "bcErrorCode");
            return (Criteria) this;
        }

        public Criteria andBcErrorMsgIsNull() {
            addCriterion("bc_error_msg is null");
            return (Criteria) this;
        }

        public Criteria andBcErrorMsgIsNotNull() {
            addCriterion("bc_error_msg is not null");
            return (Criteria) this;
        }

        public Criteria andBcErrorMsgEqualTo(String value) {
            addCriterion("bc_error_msg =", value, "bcErrorMsg");
            return (Criteria) this;
        }

        public Criteria andBcErrorMsgNotEqualTo(String value) {
            addCriterion("bc_error_msg <>", value, "bcErrorMsg");
            return (Criteria) this;
        }

        public Criteria andBcErrorMsgGreaterThan(String value) {
            addCriterion("bc_error_msg >", value, "bcErrorMsg");
            return (Criteria) this;
        }

        public Criteria andBcErrorMsgGreaterThanOrEqualTo(String value) {
            addCriterion("bc_error_msg >=", value, "bcErrorMsg");
            return (Criteria) this;
        }

        public Criteria andBcErrorMsgLessThan(String value) {
            addCriterion("bc_error_msg <", value, "bcErrorMsg");
            return (Criteria) this;
        }

        public Criteria andBcErrorMsgLessThanOrEqualTo(String value) {
            addCriterion("bc_error_msg <=", value, "bcErrorMsg");
            return (Criteria) this;
        }

		public Criteria andBcErrorMsgLike(String value) {
            addCriterion("bc_error_msg like", value, "bcErrorMsg");
            return (Criteria) this;
        }

        public Criteria andBcErrorMsgNotLike(String value) {
            addCriterion("bc_error_msg not like", value, "bcErrorMsg");
            return (Criteria) this;
        }

        public Criteria andBcErrorMsgIn(List<String> values) {
            addCriterion("bc_error_msg in", values, "bcErrorMsg");
            return (Criteria) this;
        }

        public Criteria andBcErrorMsgNotIn(List<String> values) {
            addCriterion("bc_error_msg not in", values, "bcErrorMsg");
            return (Criteria) this;
        }

        public Criteria andBcErrorMsgBetween(String value1, String value2) {
            addCriterion("bc_error_msg between", value1, value2, "bcErrorMsg");
            return (Criteria) this;
        }

        public Criteria andBcErrorMsgNotBetween(String value1, String value2) {
            addCriterion("bc_error_msg not between", value1, value2, "bcErrorMsg");
            return (Criteria) this;
        }

        public Criteria andBizTypeIsNull() {
            addCriterion("biz_type is null");
            return (Criteria) this;
        }

        public Criteria andBizTypeIsNotNull() {
            addCriterion("biz_type is not null");
            return (Criteria) this;
        }

        public Criteria andBizTypeEqualTo(Integer value) {
            addCriterion("biz_type =", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeNotEqualTo(Integer value) {
            addCriterion("biz_type <>", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeGreaterThan(Integer value) {
            addCriterion("biz_type >", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("biz_type >=", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeLessThan(Integer value) {
            addCriterion("biz_type <", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeLessThanOrEqualTo(Integer value) {
            addCriterion("biz_type <=", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeIn(List<Integer> values) {
            addCriterion("biz_type in", values, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeNotIn(List<Integer> values) {
            addCriterion("biz_type not in", values, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeBetween(Integer value1, Integer value2) {
            addCriterion("biz_type between", value1, value2, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("biz_type not between", value1, value2, "bizType");
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

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
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

        public Criteria andTxBlobIsNull() {
            addCriterion("tx_blob is null");
            return (Criteria) this;
        }

        public Criteria andTxBlobIsNotNull() {
            addCriterion("tx_blob is not null");
            return (Criteria) this;
        }

        public Criteria andTxBlobEqualTo(String value) {
            addCriterion("tx_blob =", value, "txBlob");
            return (Criteria) this;
        }

        public Criteria andTxBlobNotEqualTo(String value) {
            addCriterion("tx_blob <>", value, "txBlob");
            return (Criteria) this;
        }

        public Criteria andTxBlobGreaterThan(String value) {
            addCriterion("tx_blob >", value, "txBlob");
            return (Criteria) this;
        }

        public Criteria andTxBlobGreaterThanOrEqualTo(String value) {
            addCriterion("tx_blob >=", value, "txBlob");
            return (Criteria) this;
        }

        public Criteria andTxBlobLessThan(String value) {
            addCriterion("tx_blob <", value, "txBlob");
            return (Criteria) this;
        }

        public Criteria andTxBlobLessThanOrEqualTo(String value) {
            addCriterion("tx_blob <=", value, "txBlob");
            return (Criteria) this;
        }

		public Criteria andTxBlobLike(String value) {
            addCriterion("tx_blob like", value, "txBlob");
            return (Criteria) this;
        }

        public Criteria andTxBlobNotLike(String value) {
            addCriterion("tx_blob not like", value, "txBlob");
            return (Criteria) this;
        }

        public Criteria andTxBlobIn(List<String> values) {
            addCriterion("tx_blob in", values, "txBlob");
            return (Criteria) this;
        }

        public Criteria andTxBlobNotIn(List<String> values) {
            addCriterion("tx_blob not in", values, "txBlob");
            return (Criteria) this;
        }

        public Criteria andTxBlobBetween(String value1, String value2) {
            addCriterion("tx_blob between", value1, value2, "txBlob");
            return (Criteria) this;
        }

        public Criteria andTxBlobNotBetween(String value1, String value2) {
            addCriterion("tx_blob not between", value1, value2, "txBlob");
            return (Criteria) this;
        }

        public Criteria andTxFeeIsNull() {
            addCriterion("tx_fee is null");
            return (Criteria) this;
        }

        public Criteria andTxFeeIsNotNull() {
            addCriterion("tx_fee is not null");
            return (Criteria) this;
        }

        public Criteria andTxFeeEqualTo(String value) {
            addCriterion("tx_fee =", value, "txFee");
            return (Criteria) this;
        }

        public Criteria andTxFeeNotEqualTo(String value) {
            addCriterion("tx_fee <>", value, "txFee");
            return (Criteria) this;
        }

        public Criteria andTxFeeGreaterThan(String value) {
            addCriterion("tx_fee >", value, "txFee");
            return (Criteria) this;
        }

        public Criteria andTxFeeGreaterThanOrEqualTo(String value) {
            addCriterion("tx_fee >=", value, "txFee");
            return (Criteria) this;
        }

        public Criteria andTxFeeLessThan(String value) {
            addCriterion("tx_fee <", value, "txFee");
            return (Criteria) this;
        }

        public Criteria andTxFeeLessThanOrEqualTo(String value) {
            addCriterion("tx_fee <=", value, "txFee");
            return (Criteria) this;
        }

		public Criteria andTxFeeLike(String value) {
            addCriterion("tx_fee like", value, "txFee");
            return (Criteria) this;
        }

        public Criteria andTxFeeNotLike(String value) {
            addCriterion("tx_fee not like", value, "txFee");
            return (Criteria) this;
        }

        public Criteria andTxFeeIn(List<String> values) {
            addCriterion("tx_fee in", values, "txFee");
            return (Criteria) this;
        }

        public Criteria andTxFeeNotIn(List<String> values) {
            addCriterion("tx_fee not in", values, "txFee");
            return (Criteria) this;
        }

        public Criteria andTxFeeBetween(String value1, String value2) {
            addCriterion("tx_fee between", value1, value2, "txFee");
            return (Criteria) this;
        }

        public Criteria andTxFeeNotBetween(String value1, String value2) {
            addCriterion("tx_fee not between", value1, value2, "txFee");
            return (Criteria) this;
        }

        public Criteria andTxHashIsNull() {
            addCriterion("tx_hash is null");
            return (Criteria) this;
        }

        public Criteria andTxHashIsNotNull() {
            addCriterion("tx_hash is not null");
            return (Criteria) this;
        }

        public Criteria andTxHashEqualTo(String value) {
            addCriterion("tx_hash =", value, "txHash");
            return (Criteria) this;
        }

        public Criteria andTxHashNotEqualTo(String value) {
            addCriterion("tx_hash <>", value, "txHash");
            return (Criteria) this;
        }

        public Criteria andTxHashGreaterThan(String value) {
            addCriterion("tx_hash >", value, "txHash");
            return (Criteria) this;
        }

        public Criteria andTxHashGreaterThanOrEqualTo(String value) {
            addCriterion("tx_hash >=", value, "txHash");
            return (Criteria) this;
        }

        public Criteria andTxHashLessThan(String value) {
            addCriterion("tx_hash <", value, "txHash");
            return (Criteria) this;
        }

        public Criteria andTxHashLessThanOrEqualTo(String value) {
            addCriterion("tx_hash <=", value, "txHash");
            return (Criteria) this;
        }

		public Criteria andTxHashLike(String value) {
            addCriterion("tx_hash like", value, "txHash");
            return (Criteria) this;
        }

        public Criteria andTxHashNotLike(String value) {
            addCriterion("tx_hash not like", value, "txHash");
            return (Criteria) this;
        }

        public Criteria andTxHashIn(List<String> values) {
            addCriterion("tx_hash in", values, "txHash");
            return (Criteria) this;
        }

        public Criteria andTxHashNotIn(List<String> values) {
            addCriterion("tx_hash not in", values, "txHash");
            return (Criteria) this;
        }

        public Criteria andTxHashBetween(String value1, String value2) {
            addCriterion("tx_hash between", value1, value2, "txHash");
            return (Criteria) this;
        }

        public Criteria andTxHashNotBetween(String value1, String value2) {
            addCriterion("tx_hash not between", value1, value2, "txHash");
            return (Criteria) this;
        }

        public Criteria andTxInitiatorAddressIsNull() {
            addCriterion("tx_initiator_address is null");
            return (Criteria) this;
        }

        public Criteria andTxInitiatorAddressIsNotNull() {
            addCriterion("tx_initiator_address is not null");
            return (Criteria) this;
        }

        public Criteria andTxInitiatorAddressEqualTo(String value) {
            addCriterion("tx_initiator_address =", value, "txInitiatorAddress");
            return (Criteria) this;
        }

        public Criteria andTxInitiatorAddressNotEqualTo(String value) {
            addCriterion("tx_initiator_address <>", value, "txInitiatorAddress");
            return (Criteria) this;
        }

        public Criteria andTxInitiatorAddressGreaterThan(String value) {
            addCriterion("tx_initiator_address >", value, "txInitiatorAddress");
            return (Criteria) this;
        }

        public Criteria andTxInitiatorAddressGreaterThanOrEqualTo(String value) {
            addCriterion("tx_initiator_address >=", value, "txInitiatorAddress");
            return (Criteria) this;
        }

        public Criteria andTxInitiatorAddressLessThan(String value) {
            addCriterion("tx_initiator_address <", value, "txInitiatorAddress");
            return (Criteria) this;
        }

        public Criteria andTxInitiatorAddressLessThanOrEqualTo(String value) {
            addCriterion("tx_initiator_address <=", value, "txInitiatorAddress");
            return (Criteria) this;
        }

		public Criteria andTxInitiatorAddressLike(String value) {
            addCriterion("tx_initiator_address like", value, "txInitiatorAddress");
            return (Criteria) this;
        }

        public Criteria andTxInitiatorAddressNotLike(String value) {
            addCriterion("tx_initiator_address not like", value, "txInitiatorAddress");
            return (Criteria) this;
        }

        public Criteria andTxInitiatorAddressIn(List<String> values) {
            addCriterion("tx_initiator_address in", values, "txInitiatorAddress");
            return (Criteria) this;
        }

        public Criteria andTxInitiatorAddressNotIn(List<String> values) {
            addCriterion("tx_initiator_address not in", values, "txInitiatorAddress");
            return (Criteria) this;
        }

        public Criteria andTxInitiatorAddressBetween(String value1, String value2) {
            addCriterion("tx_initiator_address between", value1, value2, "txInitiatorAddress");
            return (Criteria) this;
        }

        public Criteria andTxInitiatorAddressNotBetween(String value1, String value2) {
            addCriterion("tx_initiator_address not between", value1, value2, "txInitiatorAddress");
            return (Criteria) this;
        }

        public Criteria andTxStatusIsNull() {
            addCriterion("tx_status is null");
            return (Criteria) this;
        }

        public Criteria andTxStatusIsNotNull() {
            addCriterion("tx_status is not null");
            return (Criteria) this;
        }

        public Criteria andTxStatusEqualTo(Integer value) {
            addCriterion("tx_status =", value, "txStatus");
            return (Criteria) this;
        }

        public Criteria andTxStatusNotEqualTo(Integer value) {
            addCriterion("tx_status <>", value, "txStatus");
            return (Criteria) this;
        }

        public Criteria andTxStatusGreaterThan(Integer value) {
            addCriterion("tx_status >", value, "txStatus");
            return (Criteria) this;
        }

        public Criteria andTxStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("tx_status >=", value, "txStatus");
            return (Criteria) this;
        }

        public Criteria andTxStatusLessThan(Integer value) {
            addCriterion("tx_status <", value, "txStatus");
            return (Criteria) this;
        }

        public Criteria andTxStatusLessThanOrEqualTo(Integer value) {
            addCriterion("tx_status <=", value, "txStatus");
            return (Criteria) this;
        }

        public Criteria andTxStatusIn(List<Integer> values) {
            addCriterion("tx_status in", values, "txStatus");
            return (Criteria) this;
        }

        public Criteria andTxStatusNotIn(List<Integer> values) {
            addCriterion("tx_status not in", values, "txStatus");
            return (Criteria) this;
        }

        public Criteria andTxStatusBetween(Integer value1, Integer value2) {
            addCriterion("tx_status between", value1, value2, "txStatus");
            return (Criteria) this;
        }

        public Criteria andTxStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("tx_status not between", value1, value2, "txStatus");
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

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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