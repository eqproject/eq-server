package org.eq.modules.product.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.util.WebClientUtil;
import org.eq.modules.product.vo.TicketPlatProductRes;
import org.eq.modules.product.vo.TicketPlatTokenRes;
import org.eq.modules.product.vo.TicketProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
@SuppressWarnings("all")
public class ProductLoadService {

    private static Logger logger = LoggerFactory.getLogger(ProductLoadService.class);

    /**
     * Redis 操作
     */
    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${tick.url}")
    private  String TICKET_URL;

    @Value("${tick.appid}")
    private  String APPID;


    @Value("${tick.appkey}")
    private  String APPKEY ;


    /**
     * 存放区块链 TOKEN
     */
    private  static final String  PRODUCT_LOAD_TOKEN_KEY = "VOUCHER_TOKEN_KEY";


    /**
     * 获取区块链平台商品信息
     * @return
     * TODO 实现区块链同步商品接口
     */
    public  Map<String,TicketProductVO> getTicketUserProduct(String address){
        Map<String,TicketProductVO> result = new HashMap<>();
        if("KAKA".equals(address)){
            TicketProductVO ticketProductVO = new TicketProductVO();
            ticketProductVO.setVoucherId("TICKET01");
            ticketProductVO.setTrancheId("TRANCEID01");
            ticketProductVO.setVoucherName("区块链商品名称");
            ticketProductVO.setFaceValue("1000");
            ticketProductVO.setBalance("1000");
            ticketProductVO.setDescription("区块链商品简介");
            String key = ticketProductVO.getVoucherId()+"_"+ticketProductVO.getTrancheId();
            result.put(key,ticketProductVO);

            ticketProductVO = new TicketProductVO();
            ticketProductVO.setVoucherId("TICKET02");
            ticketProductVO.setTrancheId("TRANCEID02");
            ticketProductVO.setVoucherName("区块链商品名称");
            ticketProductVO.setFaceValue("1000");
            ticketProductVO.setBalance("1000");
            ticketProductVO.setDescription("区块链商品简介");
            key = ticketProductVO.getVoucherId()+"_"+ticketProductVO.getTrancheId();
            result.put(key,ticketProductVO);

            ticketProductVO = new TicketProductVO();
            ticketProductVO.setVoucherId("TICKET03");
            ticketProductVO.setTrancheId("TRANCEID03");
            ticketProductVO.setVoucherName("区块链商品名称");
            ticketProductVO.setFaceValue("1000");
            ticketProductVO.setBalance("1000");
            ticketProductVO.setDescription("区块链商品简介");
            key = ticketProductVO.getVoucherId()+"_"+ticketProductVO.getTrancheId();
            result.put(key,ticketProductVO);

            ticketProductVO = new TicketProductVO();
            ticketProductVO.setVoucherId("TICKET05");
            ticketProductVO.setTrancheId("TRANCEID05");
            ticketProductVO.setVoucherName("区块链商品名称");
            ticketProductVO.setFaceValue("12");
            ticketProductVO.setBalance("24");
            ticketProductVO.setDescription("区块链商品简介");
            key = ticketProductVO.getVoucherId()+"_"+ticketProductVO.getTrancheId();
            result.put(key,ticketProductVO);
        }else{
            result = loadUserProduct(address);
        }
        return result;
    }

    /**
     * 加载用户商品信息
     * @param address
     * @return
     */
    private Map<String,TicketProductVO> loadUserProduct(String address){
        Map<String,TicketProductVO>  result = new HashMap<>();
        String token = getToken();
        if(StringUtil.isEmpty(token)){
            return result;
        }
        String url = TICKET_URL+"/voucher/v1/account/balance";
        JSONObject params = new JSONObject();
        params.put("pageSize",10);
        params.put("accessToken",token);
        params.put("address",address);
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
            List<TicketProductVO> pageDate = ticketData.getVoucherList();
            for(TicketProductVO temp : pageDate){
                String tranchId  = temp.getTrancheId();
                if(StringUtils.isEmpty(tranchId)){
                    tranchId = "0";
                }
                String key  = temp.getVoucherId()+"_"+tranchId;
                result.put(key,temp);
            }
            pageStart++;
        }
        return  result;
    }


    /**
     * 获取token
     * @return
     */
    private  String getToken(){
        String tokenKen = (String)redisTemplate.opsForValue().get(PRODUCT_LOAD_TOKEN_KEY);
        if(!StringUtil.isEmpty(tokenKen)){
            tokenKen="";
            //return tokenKen;
        }
        String tokenUrl = TICKET_URL+"/auth/accessToken";
        JSONObject params = new JSONObject();
        params.put("appId",APPID);
        params.put("appKey",APPKEY);
        int time = 0;
        try{
            int i = 3;
            while(i>0 && StringUtil.isEmpty(tokenKen)){
                String result = WebClientUtil.synchPostForPayload(tokenUrl,params);
                if(StringUtil.isEmpty(result)){
                    continue;
                }
                TicketPlatTokenRes ticketPlatTokenRes = JSONObject.parseObject(result,TicketPlatTokenRes.class);
                TicketPlatTokenRes.Meta meta = ticketPlatTokenRes==null?null:ticketPlatTokenRes.getMeta();
                if(meta==null || meta.getCode()==null ||  meta.getCode()!=0){
                    continue;
                }
                tokenKen = ticketPlatTokenRes.getData().getAccessToken();
                time = ticketPlatTokenRes.getData().getExpiresIn();
            }
        }catch (Exception e){
            logger.error("获取Token异常",e);
        }
        if(time>0 && !StringUtil.isEmpty(tokenKen)){
            time = time>100? (time-100) : time;
            //redisTemplate.opsForValue().set(PRODUCT_LOAD_TOKEN_KEY,tokenKen,time, TimeUnit.SECONDS);
        }
        return  tokenKen;
    }




}
