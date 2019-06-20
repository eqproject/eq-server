package org.eq.modules.product.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.eq.basic.common.util.DateUtil;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.bc.common.log.Logger;
import org.eq.modules.bc.common.log.LoggerFactory;
import org.eq.modules.bc.common.util.StringUtil;
import org.eq.modules.bc.common.util.WebClientUtil;
import org.eq.modules.enums.OrderAdStateEnum;
import org.eq.modules.enums.OrderAdTypeEnum;
import org.eq.modules.enums.ProductStateEnum;
import org.eq.modules.order.dao.OrderAdLogMapper;
import org.eq.modules.order.dao.OrderAdMapper;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.entity.OrderAdExample;
import org.eq.modules.order.entity.OrderAdLog;
import org.eq.modules.product.dao.*;
import org.eq.modules.product.entity.*;
import org.eq.modules.product.entitys.TicketPlatProductRes;
import org.eq.modules.product.entitys.TicketPlatTokenRes;
import org.eq.modules.product.entitys.TicketProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 商品过期任务类
 * @author  kaka
 * @date  20190606
 */
@SuppressWarnings("all")
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductLoadBiz {

    private static Logger logger = LoggerFactory.getLogger(ProductLoadBiz.class);
    /**
     * 商品
     */
    @Autowired
    private final ProductMapper productMapper;


    @Autowired
    private final ProductAcceptMapper productAcceptMapper;

    @Autowired
    private  final ProductBlockchainMapper productBlockchainMapper;

    @Autowired
    private final ProductIssuerMapper productIssuerMapper;

    @Value("${tick.url}")
    private  String TICKET_URL;
    @Value("${tick.appid}")
    private  String APPID;
    @Value("${tick.appkey}")
    private  String APPKEY ;



    /**
     * 获取区块链平台商品
     * @return
     */
    public List<TicketProduct> listProduct() {
       /* List<TicketProduct> result = new ArrayList<>();

        String txt ="{\"contractAddress\":\"合约地址\",\"ticketId\":\"10001\",\"ticketName\":\"定时任务券\",\"ticketIcon\":\"url\",\"ticketDesc\":\"此卡用于定时任务同步\",\"ticketIssuer\":{\"address\":\"发行方区块链地址\",\"icon\":\"\",\"name\":\"京东方\"},\"ticketAcceptance\":{\"address\":\"承兑方区块链地址\",\"icon\":\"\",\"name\":\"海马科技\"},\"ticketFaceValue\":\"600\",\"ticketSpe\":[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"42\"}],\"startTime\":\"2019-01-22\",\"endTime\":\"2029-01-22\",\"trancheId\":\"定时任务分组\"}";
        TicketProduct ticketProduct = JSONObject.parseObject(txt,TicketProduct.class);
        result.add(ticketProduct);
        return result;*/
        return pageList();
    }


    /**
     * 通过券ID 和分组ID 获取券信息
     * @param ticketId
     * @param trancheId
     * @return
     */
    public ProductBlockchain getProductBlockchain(String  ticketId,String trancheId){
        ProductBlockchainExample productBlockchainExample =  getExampleFromEntity(ticketId, trancheId);
        List<ProductBlockchain> result =  productBlockchainMapper.selectByExample(productBlockchainExample);
        if(CollectionUtils.isEmpty(result)){
            return null;
        }
        return result.get(0);
    }

    /**
     * 插入商品信息
     * @param ticketProduct
     * @return
     */
    @Transactional
    public long insertProduct(TicketProduct ticketProduct){
        long productId = 0L;
        if(ticketProduct ==null){
            return  productId;
        }
        long acceptId =0;
        long issuerId =0;
        //插入 productAcceptMapper
        TicketProduct.VoucherAcceptance ticketAcceptance = null;
        if(!CollectionUtils.isEmpty(ticketProduct.getVoucherAcceptance())){
            ticketAcceptance = ticketProduct.getVoucherAcceptance().get(0);
        }
        if(ticketAcceptance!=null){
            ProductAccept productAccept = new ProductAccept();
            productAccept.setName(ticketAcceptance.getName());
            productAccept.setAddress(ticketAcceptance.getAddress());
            productAccept.setIcon(ticketAcceptance.getIcon());
            productAccept.setCreateDate(new Date());
            productAccept.setUpdateDate(new Date());
            long resultId = productAcceptMapper.insertSelective(productAccept);
            if(resultId<=0){
                logger.error("插入承兑方信息异常");
                return productId;
            }
            acceptId =  productAccept.getId();
        }

        //插入 productAcceptMapper
        TicketProduct.VoucherIssuer ticketIssuer = ticketProduct.getVoucherIssuer();
        if(ticketIssuer!=null){
            ProductIssuer productIssuer = new ProductIssuer();
            productIssuer.setName(ticketIssuer.getName());
            productIssuer.setAddress(ticketIssuer.getAddress());
            productIssuer.setIcon(ticketIssuer.getIcon());
            productIssuer.setCreateDate(new Date());
            productIssuer.setUpdateDate(new Date());
            long resultId = productIssuerMapper.insertSelective(productIssuer);
            if(resultId<=0){
                logger.error("插入发行方信息异常");
                return productId;
            }
            issuerId  = productIssuer.getId();
        }
        Product product = new Product();
        product.setName(ticketProduct.getVoucherName());
        product.setProductImg(ticketProduct.getVoucherIcon());
        product.setProductAcceptId(acceptId);
        product.setProductIssuerId(issuerId);
        product.setUnitPrice(Integer.valueOf(ticketProduct.getFaceValue()));
        product.setSort(1);
        product.setStatus(ProductStateEnum.DEFAULT.getState());
        product.setCreateDate(new Date());
        product.setUpdateDate(new Date());
        if(!"-1".equals(ticketProduct.getStartTime())){
            product.setExpirationStart(ticketProduct.getStartTime());
        }
        if(!"-1".equals(ticketProduct.getEndTime())){
            product.setExpirationEnd(ticketProduct.getEndTime());
        }
        //{"receive":"京东E卡提货说明","ticketDesc":"E卡说明"}
        JSONObject extend = new JSONObject();
        extend.put("receive","");
        extend.put("ticketDesc",ticketProduct.getDescription());
        product.setExtendInfo(extend.toJSONString());
        int inserNum = productMapper.insertSelective(product);
        if(inserNum<=0){
            return productId;
        }
        ProductBlockchain productBlockchain = new ProductBlockchain();
        productBlockchain.setTrancheid(ticketProduct.getTrancheId());
        productBlockchain.setAssetCode("未知");
        productBlockchain.setAssetIssuer("不确定");
        productBlockchain.setContractAddress(ticketProduct.getContractAddress());
        productBlockchain.setTicketid(ticketProduct.getVoucherId());
        productBlockchain.setProductId(product.getId());
        productBlockchain.setCreateDate(new Date());
        productBlockchain.setUpdateDate(new Date());
        inserNum = productBlockchainMapper.insertSelective(productBlockchain);
        if(inserNum>0){
            productId = product.getId();
        }
        return productId;
    }

    private  List<TicketProduct> pageList(){
        List<TicketProduct>  result = new ArrayList<>();
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
            result.addAll(ticketData.getVoucherList());
            pageStart++;
        }
        return  result;

    }


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
     * 获取查询实例
     * @param ticketid 券Id
     * @param trancheid 区块链分组id
     * @return
     */
    private ProductBlockchainExample getExampleFromEntity(String ticketid,String trancheid) {
        ProductBlockchainExample example = new ProductBlockchainExample();
        ProductBlockchainExample.Criteria ca = example.or();
        ca.andTicketidEqualTo(ticketid);
        ca.andTrancheidEqualTo(trancheid);
        return example;
    }





}
