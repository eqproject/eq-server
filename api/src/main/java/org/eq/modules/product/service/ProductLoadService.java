package org.eq.modules.product.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.eq.basic.common.util.WebClientUtil;
import org.eq.modules.product.vo.TicketPlatProductRes;
import org.eq.modules.product.vo.TicketPlatTokenRes;
import org.eq.modules.product.vo.TicketProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("all")
public class ProductLoadService {

    private static Logger logger = LoggerFactory.getLogger(ProductLoadService.class);

    @Value("${tick.url}")
    private  String TICKET_URL;
    @Value("${tick.appid}")
    private  String APPID;
    @Value("${tick.appkey}")
    private  String APPKEY ;


    /**
     * 获取token
     * @return
     */
    private  String getToken(){
        String tokenUrl = TICKET_URL+"/auth/accessToken";
        JSONObject params = new JSONObject();
        params.put("appId",APPID);
        params.put("appKey",APPKEY);
        String token = "";
        try{
            int i = 3;
            while(i>0 && StringUtil.isEmpty(token)){
                String result = WebClientUtil.synchPostForPayload(tokenUrl,params);
                if(StringUtil.isEmpty(result)){
                    continue;
                }
                TicketPlatTokenRes ticketPlatTokenRes = JSONObject.parseObject(result,TicketPlatTokenRes.class);
                TicketPlatTokenRes.Meta meta = ticketPlatTokenRes==null?null:ticketPlatTokenRes.getMeta();
                if(meta==null || meta.getCode()==null ||  meta.getCode()!=0){
                    continue;
                }
                token = ticketPlatTokenRes.getData().getAccessToken();
            }
        }catch (Exception e){
            logger.error("获取Token异常",e);
        }
        return  token;
    }


    /**
     * 获取区块链平台商品信息
     * @return
     * TODO 实现区块链同步商品接口
     */
    public  Map<String,TicketProductVO> getTicketUserProduct(String address){
        Map<String,TicketProductVO> result = new HashMap<>();
        if("KAKA".equals(address)){
            TicketProductVO ticketProductVO = new TicketProductVO();
            ticketProductVO.setTicketId("TICKET01");
            ticketProductVO.setTrancheId("TRANCEID01");
            ticketProductVO.setTicketName("区块链商品名称");
            ticketProductVO.setTicketFaceValue("1000");
            ticketProductVO.setBalance("1000");
            ticketProductVO.setTicketDesc("区块链商品简介");
            String key = ticketProductVO.getTicketId()+"_"+ticketProductVO.getTrancheId();
            result.put(key,ticketProductVO);

            ticketProductVO = new TicketProductVO();
            ticketProductVO.setTicketId("TICKET02");
            ticketProductVO.setTrancheId("TRANCEID02");
            ticketProductVO.setTicketName("区块链商品名称");
            ticketProductVO.setTicketFaceValue("1000");
            ticketProductVO.setBalance("1000");
            ticketProductVO.setTicketDesc("区块链商品简介");
            key = ticketProductVO.getTicketId()+"_"+ticketProductVO.getTrancheId();
            result.put(key,ticketProductVO);

            ticketProductVO = new TicketProductVO();
            ticketProductVO.setTicketId("TICKET03");
            ticketProductVO.setTrancheId("TRANCEID03");
            ticketProductVO.setTicketName("区块链商品名称");
            ticketProductVO.setTicketFaceValue("1000");
            ticketProductVO.setBalance("1000");
            ticketProductVO.setTicketDesc("区块链商品简介");
            key = ticketProductVO.getTicketId()+"_"+ticketProductVO.getTrancheId();


            ticketProductVO = new TicketProductVO();
            ticketProductVO.setTicketId("TICKET05");
            ticketProductVO.setTrancheId("TRANCEID05");
            ticketProductVO.setTicketName("区块链商品名称");
            ticketProductVO.setTicketFaceValue("12");
            ticketProductVO.setBalance("24");
            ticketProductVO.setTicketDesc("区块链商品简介");
            key = ticketProductVO.getTicketId()+"_"+ticketProductVO.getTrancheId();

            result.put(key,ticketProductVO);
        }
        return result;
    }

    private Map<String,TicketProductVO> pageList(){
        Map<String,TicketProductVO>  result = new HashMap<>();
        String token = getToken();
        if(StringUtil.isEmpty(token)){
            logger.error("获取Token 失败");
            return result;
        }
        System.out.println(token);

        String url = TICKET_URL+"/voucher/v1/list";
        JSONObject params = new JSONObject();
        params.put("pageSize",10);
        params.put("accessToken",token);
        int pageStart =1;
        while(true){
            params.put("pageStart",pageStart);
            TicketPlatProductRes  ticketPlatProductRes = null;
            try{
                String productResult = WebClientUtil.synchPostForPayload(url,params);
                if(!StringUtil.isEmpty(productResult)){
                    ticketPlatProductRes = JSONObject.parseObject(productResult,TicketPlatProductRes.class);
                }
            }catch (Exception e){
                logger.error("获取商品信息异常",e);
                e.printStackTrace();
            }
            Integer code = -1;
            if(ticketPlatProductRes ==null || ticketPlatProductRes.getMeta()==null){
                logger.error("获取商品信息异常");
            }
            code = ticketPlatProductRes.getMeta().getCode();
            if(code.intValue()!=0){
                break;
            }

            TicketPlatProductRes.TicketData  ticketData =  ticketPlatProductRes.getData();
            if(ticketData==null || CollectionUtils.isEmpty(ticketData.getVoucherList())){
                break;
            }
            pageStart++;
        }
        return  result;

    }




    public static void main(String[] args) {
        System.out.println("1");
    }





}
