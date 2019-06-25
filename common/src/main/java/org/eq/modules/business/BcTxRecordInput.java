package org.eq.modules.business;

import com.alibaba.fastjson.JSONObject;
import org.eq.modules.bc.entity.BcTxRecord;
import org.eq.modules.product.entity.ProductAll;

/**
 * @author : gb 2019/6/25
 */
public class BcTxRecordInput {

    public static String build(ProductAll product, String toAddress, int amount) {
        JSONObject input = new JSONObject();
        input.put("method", "transfer");
        JSONObject params = new JSONObject();
        params.put("skuId", product.getTicketid());
        params.put("trancheId", product.getTrancheid());
        params.put("to", toAddress);
        params.put("value", Integer.toString(amount));
        input.put("params", params);
        return input.toJSONString();
    }
}
