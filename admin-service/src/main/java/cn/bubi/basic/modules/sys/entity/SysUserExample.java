/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户表EntityExample
 *
 * @author JoinHan
 * @version 0.0.1
 */
public class SysUserExample {

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysUserExample() {
        this.oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {

        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {

        return this.orderByClause;
    }

    public void setDistinct(boolean distinct) {

        this.distinct = distinct;
    }

    public boolean isDistinct() {

        return this.distinct;
    }

    public List<Criteria> getOredCriteria() {

        return this.oredCriteria;
    }

    public void or(Criteria criteria) {

        this.oredCriteria.add(criteria);
    }

    public Criteria or() {

        Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {

        Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {

        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {

        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }

    protected abstract static class GeneratedCriteria {

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            this.criteria = new ArrayList<>();
        }

        public boolean isValid() {

            return this.criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {

            return this.criteria;
        }

        public List<Criterion> getCriteria() {

            return this.criteria;
        }

        protected void addCriterion(String condition) {

            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            this.criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {

            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            this.criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {

            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            this.criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {

            this.addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {

            this.addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {

            this.addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {

            this.addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {

            this.addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {

            this.addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {

            this.addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {

            this.addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {

            this.addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {

            this.addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {

            this.addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {

            this.addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAreaIdIsNull() {

            this.addCriterion("area_id is null");
            return (Criteria) this;
        }

        public Criteria andAreaIdIsNotNull() {

            this.addCriterion("area_id is not null");
            return (Criteria) this;
        }

        public Criteria andAreaIdEqualTo(Long value) {

            this.addCriterion("area_id =", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotEqualTo(Long value) {

            this.addCriterion("area_id <>", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdGreaterThan(Long value) {

            this.addCriterion("area_id >", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdGreaterThanOrEqualTo(Long value) {

            this.addCriterion("area_id >=", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdLessThan(Long value) {

            this.addCriterion("area_id <", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdLessThanOrEqualTo(Long value) {

            this.addCriterion("area_id <=", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdIn(List<Long> values) {

            this.addCriterion("area_id in", values, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotIn(List<Long> values) {

            this.addCriterion("area_id not in", values, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdBetween(Long value1, Long value2) {

            this.addCriterion("area_id between", value1, value2, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotBetween(Long value1, Long value2) {

            this.addCriterion("area_id not between", value1, value2, "areaId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdIsNull() {

            this.addCriterion("office_id is null");
            return (Criteria) this;
        }

        public Criteria andOfficeIdIsNotNull() {

            this.addCriterion("office_id is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeIdEqualTo(Long value) {

            this.addCriterion("office_id =", value, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdNotEqualTo(Long value) {

            this.addCriterion("office_id <>", value, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdGreaterThan(Long value) {

            this.addCriterion("office_id >", value, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdGreaterThanOrEqualTo(Long value) {

            this.addCriterion("office_id >=", value, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdLessThan(Long value) {

            this.addCriterion("office_id <", value, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdLessThanOrEqualTo(Long value) {

            this.addCriterion("office_id <=", value, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdIn(List<Long> values) {

            this.addCriterion("office_id in", values, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdNotIn(List<Long> values) {

            this.addCriterion("office_id not in", values, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdBetween(Long value1, Long value2) {

            this.addCriterion("office_id between", value1, value2, "officeId");
            return (Criteria) this;
        }

        public Criteria andOfficeIdNotBetween(Long value1, Long value2) {

            this.addCriterion("office_id not between", value1, value2, "officeId");
            return (Criteria) this;
        }

        public Criteria andDepartIdIsNull() {

            this.addCriterion("depart_id is null");
            return (Criteria) this;
        }

        public Criteria andDepartIdIsNotNull() {

            this.addCriterion("depart_id is not null");
            return (Criteria) this;
        }

        public Criteria andDepartIdEqualTo(Long value) {

            this.addCriterion("depart_id =", value, "departId");
            return (Criteria) this;
        }

        public Criteria andDepartIdNotEqualTo(Long value) {

            this.addCriterion("depart_id <>", value, "departId");
            return (Criteria) this;
        }

        public Criteria andDepartIdGreaterThan(Long value) {

            this.addCriterion("depart_id >", value, "departId");
            return (Criteria) this;
        }

        public Criteria andDepartIdGreaterThanOrEqualTo(Long value) {

            this.addCriterion("depart_id >=", value, "departId");
            return (Criteria) this;
        }

        public Criteria andDepartIdLessThan(Long value) {

            this.addCriterion("depart_id <", value, "departId");
            return (Criteria) this;
        }

        public Criteria andDepartIdLessThanOrEqualTo(Long value) {

            this.addCriterion("depart_id <=", value, "departId");
            return (Criteria) this;
        }

        public Criteria andDepartIdIn(List<Long> values) {

            this.addCriterion("depart_id in", values, "departId");
            return (Criteria) this;
        }

        public Criteria andDepartIdNotIn(List<Long> values) {

            this.addCriterion("depart_id not in", values, "departId");
            return (Criteria) this;
        }

        public Criteria andDepartIdBetween(Long value1, Long value2) {

            this.addCriterion("depart_id between", value1, value2, "departId");
            return (Criteria) this;
        }

        public Criteria andDepartIdNotBetween(Long value1, Long value2) {

            this.addCriterion("depart_id not between", value1, value2, "departId");
            return (Criteria) this;
        }

        public Criteria andLnmIsNull() {

            this.addCriterion("lnm is null");
            return (Criteria) this;
        }

        public Criteria andLnmIsNotNull() {

            this.addCriterion("lnm is not null");
            return (Criteria) this;
        }

        public Criteria andLnmEqualTo(String value) {

            this.addCriterion("lnm =", value, "lnm");
            return (Criteria) this;
        }

        public Criteria andLnmNotEqualTo(String value) {

            this.addCriterion("lnm <>", value, "lnm");
            return (Criteria) this;
        }

        public Criteria andLnmGreaterThan(String value) {

            this.addCriterion("lnm >", value, "lnm");
            return (Criteria) this;
        }

        public Criteria andLnmGreaterThanOrEqualTo(String value) {

            this.addCriterion("lnm >=", value, "lnm");
            return (Criteria) this;
        }

        public Criteria andLnmLessThan(String value) {

            this.addCriterion("lnm <", value, "lnm");
            return (Criteria) this;
        }

        public Criteria andLnmLessThanOrEqualTo(String value) {

            this.addCriterion("lnm <=", value, "lnm");
            return (Criteria) this;
        }

        public Criteria andLnmLike(String value) {

            this.addCriterion("lnm like", value, "lnm");
            return (Criteria) this;
        }

        public Criteria andLnmNotLike(String value) {

            this.addCriterion("lnm not like", value, "lnm");
            return (Criteria) this;
        }

        public Criteria andLnmIn(List<String> values) {

            this.addCriterion("lnm in", values, "lnm");
            return (Criteria) this;
        }

        public Criteria andLnmNotIn(List<String> values) {

            this.addCriterion("lnm not in", values, "lnm");
            return (Criteria) this;
        }

        public Criteria andLnmBetween(String value1, String value2) {

            this.addCriterion("lnm between", value1, value2, "lnm");
            return (Criteria) this;
        }

        public Criteria andLnmNotBetween(String value1, String value2) {

            this.addCriterion("lnm not between", value1, value2, "lnm");
            return (Criteria) this;
        }

        public Criteria andPwIsNull() {

            this.addCriterion("pw is null");
            return (Criteria) this;
        }

        public Criteria andPwIsNotNull() {

            this.addCriterion("pw is not null");
            return (Criteria) this;
        }

        public Criteria andPwEqualTo(String value) {

            this.addCriterion("pw =", value, "pw");
            return (Criteria) this;
        }

        public Criteria andPwNotEqualTo(String value) {

            this.addCriterion("pw <>", value, "pw");
            return (Criteria) this;
        }

        public Criteria andPwGreaterThan(String value) {

            this.addCriterion("pw >", value, "pw");
            return (Criteria) this;
        }

        public Criteria andPwGreaterThanOrEqualTo(String value) {

            this.addCriterion("pw >=", value, "pw");
            return (Criteria) this;
        }

        public Criteria andPwLessThan(String value) {

            this.addCriterion("pw <", value, "pw");
            return (Criteria) this;
        }

        public Criteria andPwLessThanOrEqualTo(String value) {

            this.addCriterion("pw <=", value, "pw");
            return (Criteria) this;
        }

        public Criteria andPwLike(String value) {

            this.addCriterion("pw like", value, "pw");
            return (Criteria) this;
        }

        public Criteria andPwNotLike(String value) {

            this.addCriterion("pw not like", value, "pw");
            return (Criteria) this;
        }

        public Criteria andPwIn(List<String> values) {

            this.addCriterion("pw in", values, "pw");
            return (Criteria) this;
        }

        public Criteria andPwNotIn(List<String> values) {

            this.addCriterion("pw not in", values, "pw");
            return (Criteria) this;
        }

        public Criteria andPwBetween(String value1, String value2) {

            this.addCriterion("pw between", value1, value2, "pw");
            return (Criteria) this;
        }

        public Criteria andPwNotBetween(String value1, String value2) {

            this.addCriterion("pw not between", value1, value2, "pw");
            return (Criteria) this;
        }

        public Criteria andNmIsNull() {

            this.addCriterion("nm is null");
            return (Criteria) this;
        }

        public Criteria andNmIsNotNull() {

            this.addCriterion("nm is not null");
            return (Criteria) this;
        }

        public Criteria andNmEqualTo(String value) {

            this.addCriterion("nm =", value, "nm");
            return (Criteria) this;
        }

        public Criteria andNmNotEqualTo(String value) {

            this.addCriterion("nm <>", value, "nm");
            return (Criteria) this;
        }

        public Criteria andNmGreaterThan(String value) {

            this.addCriterion("nm >", value, "nm");
            return (Criteria) this;
        }

        public Criteria andNmGreaterThanOrEqualTo(String value) {

            this.addCriterion("nm >=", value, "nm");
            return (Criteria) this;
        }

        public Criteria andNmLessThan(String value) {

            this.addCriterion("nm <", value, "nm");
            return (Criteria) this;
        }

        public Criteria andNmLessThanOrEqualTo(String value) {

            this.addCriterion("nm <=", value, "nm");
            return (Criteria) this;
        }

        public Criteria andNmLike(String value) {

            this.addCriterion("nm like", value, "nm");
            return (Criteria) this;
        }

        public Criteria andNmNotLike(String value) {

            this.addCriterion("nm not like", value, "nm");
            return (Criteria) this;
        }

        public Criteria andNmIn(List<String> values) {

            this.addCriterion("nm in", values, "nm");
            return (Criteria) this;
        }

        public Criteria andNmNotIn(List<String> values) {

            this.addCriterion("nm not in", values, "nm");
            return (Criteria) this;
        }

        public Criteria andNmBetween(String value1, String value2) {

            this.addCriterion("nm between", value1, value2, "nm");
            return (Criteria) this;
        }

        public Criteria andNmNotBetween(String value1, String value2) {

            this.addCriterion("nm not between", value1, value2, "nm");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {

            this.addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {

            this.addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(String value) {

            this.addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(String value) {

            this.addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(String value) {

            this.addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(String value) {

            this.addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(String value) {

            this.addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(String value) {

            this.addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLike(String value) {

            this.addCriterion("sex like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotLike(String value) {

            this.addCriterion("sex not like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<String> values) {

            this.addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<String> values) {

            this.addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(String value1, String value2) {

            this.addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(String value1, String value2) {

            this.addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {

            this.addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {

            this.addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {

            this.addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {

            this.addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {

            this.addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {

            this.addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {

            this.addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {

            this.addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {

            this.addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {

            this.addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {

            this.addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {

            this.addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andTelIsNull() {

            this.addCriterion("tel is null");
            return (Criteria) this;
        }

        public Criteria andTelIsNotNull() {

            this.addCriterion("tel is not null");
            return (Criteria) this;
        }

        public Criteria andTelEqualTo(String value) {

            this.addCriterion("tel =", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotEqualTo(String value) {

            this.addCriterion("tel <>", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelGreaterThan(String value) {

            this.addCriterion("tel >", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelGreaterThanOrEqualTo(String value) {

            this.addCriterion("tel >=", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelLessThan(String value) {

            this.addCriterion("tel <", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelLessThanOrEqualTo(String value) {

            this.addCriterion("tel <=", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelLike(String value) {

            this.addCriterion("tel like", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotLike(String value) {

            this.addCriterion("tel not like", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelIn(List<String> values) {

            this.addCriterion("tel in", values, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotIn(List<String> values) {

            this.addCriterion("tel not in", values, "tel");
            return (Criteria) this;
        }

        public Criteria andTelBetween(String value1, String value2) {

            this.addCriterion("tel between", value1, value2, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotBetween(String value1, String value2) {

            this.addCriterion("tel not between", value1, value2, "tel");
            return (Criteria) this;
        }

        public Criteria andNoIdIsNull() {

            this.addCriterion("no_id is null");
            return (Criteria) this;
        }

        public Criteria andNoIdIsNotNull() {

            this.addCriterion("no_id is not null");
            return (Criteria) this;
        }

        public Criteria andNoIdEqualTo(Long value) {

            this.addCriterion("no_id =", value, "noId");
            return (Criteria) this;
        }

        public Criteria andNoIdNotEqualTo(Long value) {

            this.addCriterion("no_id <>", value, "noId");
            return (Criteria) this;
        }

        public Criteria andNoIdGreaterThan(Long value) {

            this.addCriterion("no_id >", value, "noId");
            return (Criteria) this;
        }

        public Criteria andNoIdGreaterThanOrEqualTo(Long value) {

            this.addCriterion("no_id >=", value, "noId");
            return (Criteria) this;
        }

        public Criteria andNoIdLessThan(Long value) {

            this.addCriterion("no_id <", value, "noId");
            return (Criteria) this;
        }

        public Criteria andNoIdLessThanOrEqualTo(Long value) {

            this.addCriterion("no_id <=", value, "noId");
            return (Criteria) this;
        }

        public Criteria andNoIdIn(List<Long> values) {

            this.addCriterion("no_id in", values, "noId");
            return (Criteria) this;
        }

        public Criteria andNoIdNotIn(List<Long> values) {

            this.addCriterion("no_id not in", values, "noId");
            return (Criteria) this;
        }

        public Criteria andNoIdBetween(Long value1, Long value2) {

            this.addCriterion("no_id between", value1, value2, "noId");
            return (Criteria) this;
        }

        public Criteria andNoIdNotBetween(Long value1, Long value2) {

            this.addCriterion("no_id not between", value1, value2, "noId");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {

            this.addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {

            this.addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {

            this.addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {

            this.addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {

            this.addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {

            this.addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {

            this.addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {

            this.addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {

            this.addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {

            this.addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {

            this.addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {

            this.addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andPhotoHeadIsNull() {

            this.addCriterion("photo_head is null");
            return (Criteria) this;
        }

        public Criteria andPhotoHeadIsNotNull() {

            this.addCriterion("photo_head is not null");
            return (Criteria) this;
        }

        public Criteria andPhotoHeadEqualTo(String value) {

            this.addCriterion("photo_head =", value, "photoHead");
            return (Criteria) this;
        }

        public Criteria andPhotoHeadNotEqualTo(String value) {

            this.addCriterion("photo_head <>", value, "photoHead");
            return (Criteria) this;
        }

        public Criteria andPhotoHeadGreaterThan(String value) {

            this.addCriterion("photo_head >", value, "photoHead");
            return (Criteria) this;
        }

        public Criteria andPhotoHeadGreaterThanOrEqualTo(String value) {

            this.addCriterion("photo_head >=", value, "photoHead");
            return (Criteria) this;
        }

        public Criteria andPhotoHeadLessThan(String value) {

            this.addCriterion("photo_head <", value, "photoHead");
            return (Criteria) this;
        }

        public Criteria andPhotoHeadLessThanOrEqualTo(String value) {

            this.addCriterion("photo_head <=", value, "photoHead");
            return (Criteria) this;
        }

        public Criteria andPhotoHeadLike(String value) {

            this.addCriterion("photo_head like", value, "photoHead");
            return (Criteria) this;
        }

        public Criteria andPhotoHeadNotLike(String value) {

            this.addCriterion("photo_head not like", value, "photoHead");
            return (Criteria) this;
        }

        public Criteria andPhotoHeadIn(List<String> values) {

            this.addCriterion("photo_head in", values, "photoHead");
            return (Criteria) this;
        }

        public Criteria andPhotoHeadNotIn(List<String> values) {

            this.addCriterion("photo_head not in", values, "photoHead");
            return (Criteria) this;
        }

        public Criteria andPhotoHeadBetween(String value1, String value2) {

            this.addCriterion("photo_head between", value1, value2, "photoHead");
            return (Criteria) this;
        }

        public Criteria andPhotoHeadNotBetween(String value1, String value2) {

            this.addCriterion("photo_head not between", value1, value2, "photoHead");
            return (Criteria) this;
        }

        public Criteria andLoginIpIsNull() {

            this.addCriterion("login_ip is null");
            return (Criteria) this;
        }

        public Criteria andLoginIpIsNotNull() {

            this.addCriterion("login_ip is not null");
            return (Criteria) this;
        }

        public Criteria andLoginIpEqualTo(String value) {

            this.addCriterion("login_ip =", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpNotEqualTo(String value) {

            this.addCriterion("login_ip <>", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpGreaterThan(String value) {

            this.addCriterion("login_ip >", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpGreaterThanOrEqualTo(String value) {

            this.addCriterion("login_ip >=", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpLessThan(String value) {

            this.addCriterion("login_ip <", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpLessThanOrEqualTo(String value) {

            this.addCriterion("login_ip <=", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpLike(String value) {

            this.addCriterion("login_ip like", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpNotLike(String value) {

            this.addCriterion("login_ip not like", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpIn(List<String> values) {

            this.addCriterion("login_ip in", values, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpNotIn(List<String> values) {

            this.addCriterion("login_ip not in", values, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpBetween(String value1, String value2) {

            this.addCriterion("login_ip between", value1, value2, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpNotBetween(String value1, String value2) {

            this.addCriterion("login_ip not between", value1, value2, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginDateIsNull() {

            this.addCriterion("login_date is null");
            return (Criteria) this;
        }

        public Criteria andLoginDateIsNotNull() {

            this.addCriterion("login_date is not null");
            return (Criteria) this;
        }

        public Criteria andLoginDateEqualTo(Date value) {

            this.addCriterion("login_date =", value, "loginDate");
            return (Criteria) this;
        }

        public Criteria andLoginDateNotEqualTo(Date value) {

            this.addCriterion("login_date <>", value, "loginDate");
            return (Criteria) this;
        }

        public Criteria andLoginDateGreaterThan(Date value) {

            this.addCriterion("login_date >", value, "loginDate");
            return (Criteria) this;
        }

        public Criteria andLoginDateGreaterThanOrEqualTo(Date value) {

            this.addCriterion("login_date >=", value, "loginDate");
            return (Criteria) this;
        }

        public Criteria andLoginDateLessThan(Date value) {

            this.addCriterion("login_date <", value, "loginDate");
            return (Criteria) this;
        }

        public Criteria andLoginDateLessThanOrEqualTo(Date value) {

            this.addCriterion("login_date <=", value, "loginDate");
            return (Criteria) this;
        }

        public Criteria andLoginDateIn(List<Date> values) {

            this.addCriterion("login_date in", values, "loginDate");
            return (Criteria) this;
        }

        public Criteria andLoginDateNotIn(List<Date> values) {

            this.addCriterion("login_date not in", values, "loginDate");
            return (Criteria) this;
        }

        public Criteria andLoginDateBetween(Date value1, Date value2) {

            this.addCriterion("login_date between", value1, value2, "loginDate");
            return (Criteria) this;
        }

        public Criteria andLoginDateNotBetween(Date value1, Date value2) {

            this.addCriterion("login_date not between", value1, value2, "loginDate");
            return (Criteria) this;
        }

        public Criteria andJobIsNull() {

            this.addCriterion("job is null");
            return (Criteria) this;
        }

        public Criteria andJobIsNotNull() {

            this.addCriterion("job is not null");
            return (Criteria) this;
        }

        public Criteria andJobEqualTo(String value) {

            this.addCriterion("job =", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotEqualTo(String value) {

            this.addCriterion("job <>", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobGreaterThan(String value) {

            this.addCriterion("job >", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobGreaterThanOrEqualTo(String value) {

            this.addCriterion("job >=", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLessThan(String value) {

            this.addCriterion("job <", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLessThanOrEqualTo(String value) {

            this.addCriterion("job <=", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLike(String value) {

            this.addCriterion("job like", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotLike(String value) {

            this.addCriterion("job not like", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobIn(List<String> values) {

            this.addCriterion("job in", values, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotIn(List<String> values) {

            this.addCriterion("job not in", values, "job");
            return (Criteria) this;
        }

        public Criteria andJobBetween(String value1, String value2) {

            this.addCriterion("job between", value1, value2, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotBetween(String value1, String value2) {

            this.addCriterion("job not between", value1, value2, "job");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {

            this.addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {

            this.addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(Long value) {

            this.addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(Long value) {

            this.addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(Long value) {

            this.addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(Long value) {

            this.addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(Long value) {

            this.addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(Long value) {

            this.addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<Long> values) {

            this.addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<Long> values) {

            this.addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(Long value1, Long value2) {

            this.addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(Long value1, Long value2) {

            this.addCriterion("create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {

            this.addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {

            this.addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {

            this.addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {

            this.addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {

            this.addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {

            this.addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {

            this.addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {

            this.addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {

            this.addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {

            this.addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {

            this.addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {

            this.addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNull() {

            this.addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {

            this.addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(Long value) {

            this.addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(Long value) {

            this.addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(Long value) {

            this.addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(Long value) {

            this.addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(Long value) {

            this.addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(Long value) {

            this.addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<Long> values) {

            this.addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<Long> values) {

            this.addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(Long value1, Long value2) {

            this.addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(Long value1, Long value2) {

            this.addCriterion("update_by not between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {

            this.addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {

            this.addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {

            this.addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {

            this.addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {

            this.addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {

            this.addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {

            this.addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {

            this.addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {

            this.addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {

            this.addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {

            this.addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {

            this.addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNull() {

            this.addCriterion("remarks is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {

            this.addCriterion("remarks is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(String value) {

            this.addCriterion("remarks =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(String value) {

            this.addCriterion("remarks <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThan(String value) {

            this.addCriterion("remarks >", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThanOrEqualTo(String value) {

            this.addCriterion("remarks >=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThan(String value) {

            this.addCriterion("remarks <", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThanOrEqualTo(String value) {

            this.addCriterion("remarks <=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLike(String value) {

            this.addCriterion("remarks like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(String value) {

            this.addCriterion("remarks not like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(List<String> values) {

            this.addCriterion("remarks in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(List<String> values) {

            this.addCriterion("remarks not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(String value1, String value2) {

            this.addCriterion("remarks between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(String value1, String value2) {

            this.addCriterion("remarks not between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNull() {

            this.addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {

            this.addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(String value) {

            this.addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(String value) {

            this.addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(String value) {

            this.addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(String value) {

            this.addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(String value) {

            this.addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(String value) {

            this.addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLike(String value) {

            this.addCriterion("del_flag like", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotLike(String value) {

            this.addCriterion("del_flag not like", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<String> values) {

            this.addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<String> values) {

            this.addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(String value1, String value2) {

            this.addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(String value1, String value2) {

            this.addCriterion("del_flag not between", value1, value2, "delFlag");
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

            return this.condition;
        }

        public Object getValue() {

            return this.value;
        }

        public Object getSecondValue() {

            return this.secondValue;
        }

        public boolean isNoValue() {

            return this.noValue;
        }

        public boolean isSingleValue() {

            return this.singleValue;
        }

        public boolean isBetweenValue() {

            return this.betweenValue;
        }

        public boolean isListValue() {

            return this.listValue;
        }

        public String getTypeHandler() {

            return this.typeHandler;
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