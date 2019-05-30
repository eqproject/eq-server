package org.eq.modules.common.entitys;

import java.io.Serializable;
import java.util.List;

/**
 * API 返回公共实体
 *
 */
@SuppressWarnings("all")
public class ResponseData<T> implements Serializable {

    /**
     * 接口返回状态
     * @see  org.eq.modules.common.enums.ResponseStateEnum;
     */
    private String status;

    /**
     * 接口错误信息
     */
    private String errMsg;

    /**
     * 实体
     */
    private T data;

    /**
     * 返回结果实体集合
     */
    private List<T> datas;



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
