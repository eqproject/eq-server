package org.eq.modules.common.factory;

import org.apache.commons.collections.CollectionUtils;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.enums.ResponseStateEnum;

import java.util.List;

/**
 * 返回对象构造工厂
 * @author  kaka
 * @date  2019
 */
public class ResponseFactory<T> {


    /**
     * 返回单个结果实体
     * @param date
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> success(T date){
        ResponseData<T> result = initSuccess();
        if(date!=null){
            result.setData(date);
        }
        return result;
    }


    /**
     * 返回集合
     * @param dates
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> success(List<T> dates){
        ResponseData<T> result = initSuccess();
        if(!CollectionUtils.isEmpty(dates)){
            result.setDatas(dates);
        }
        return result;
    }


    /**
     * 返回错误实体
     * @param errMsg
     * @param status
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> error(String errMsg,String status){
        ResponseData<T> result = initError(errMsg);
        result.setStatus(status);
        return result;
    }


    /**
     * 参数错误构建方法
     * @param errMsg 错误信息
     * @return
     */
    public static  ResponseData paramsError(String errMsg){
        ResponseData result = initError(errMsg);
        result.setStatus(ResponseStateEnum.ERROR_PARAMS.getStatus());
        return result;
    }


    /**
     * 验证不通过 可包含用户信息
     * @param errMsg 错误信息
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> signError(String errMsg){
        ResponseData<T> result = initError(errMsg);
        result.setStatus(ResponseStateEnum.ERROR_SIGN.getStatus());
        return result;
    }


    /**
     * 构造成功实体
     * @param <T>
     * @return
     */
    private  static <T> ResponseData<T> initSuccess(){
        ResponseData<T> result = new ResponseData<>();
        result.setStatus(ResponseStateEnum.SUCCESS.getStatus());
        result.setErrMsg("");
        return result;
    }


    /**
     * 构造错误信息实体
     * @param <T>
     * @return
     */
    private  static <T> ResponseData<T> initError(String msg){
        ResponseData<T> result = new ResponseData<>();
        result.setErrMsg(msg);
        return result;
    }



}
