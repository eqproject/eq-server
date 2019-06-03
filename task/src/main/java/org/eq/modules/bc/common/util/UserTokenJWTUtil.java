package org.eq.modules.bc.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.bumo.mall.talent.dto.resp.user.AccountLoginResp;
public class UserTokenJWTUtil {
	private static final String SECRET = "XX#$%()(#*!()!WR<><HHSMNsda24bubiDSFDdsfdvcbnmii;124fdf>?N<:{LWPW";
    private static Algorithm ALGORITHM;
    private static Map<String, Object> HEADER_CLAIMS = new HashMap<>();

    private static final String ISSUER = "XBYP";
	/**
	 * 超时时间限制
	 */
    private static final long EXP_TIME_USER_TOKEN = TimeUnit.SECONDS.toMillis(Long.parseLong(Config.getProperty("access.token.expire.time")));

    static{
        HEADER_CLAIMS.put("typ", "JWT");
        HEADER_CLAIMS.put("alg", "HS256");
        try {
            ALGORITHM = Algorithm.HMAC256(SECRET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private static AccountLoginResp encode(String fingerprint){
    	Map<String,String> params = new HashMap<String,String>();
    	params.put("fingerprint", fingerprint);
        return encode(params);
    }
    
    public static AccountLoginResp encode(Map<String,String> params){
    	Builder builder = JWT.create()
                .withHeader(HEADER_CLAIMS)
                .withIssuer(ISSUER)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP_TIME_USER_TOKEN));
    	
    	if(params != null && params.size() > 0){
    		params.forEach((k,v) -> {
    			builder.withClaim(k, v);
    		});
    	}
    	
        String token = builder.sign(ALGORITHM);
        
        return new AccountLoginResp(token, EXP_TIME_USER_TOKEN);
    }

    public static AccountLoginResp buildUserToken(String fingerprint){
    	return encode(fingerprint);
    }
    public static String getUnionId(String userToken){
    	String fingerprintStr = null;
    	try{
    		Map<String,String> result = decode(userToken);
			fingerprintStr =  result.get("fingerprint");
    	} catch (Exception e) {
    		e.printStackTrace();
			return null;
		}
    	return fingerprintStr;
    }
    public static Map<String,String> decode(String token){
        DecodedJWT jwt;
		try {
			JWTVerifier verifier = JWT.require(ALGORITHM)
					.withIssuer(ISSUER)
					.build(); //Reusable verifier instance
			jwt = verifier.verify(token);
			Map<String,Claim> claimsMap = jwt.getClaims();
			
			Map<String,String> resultMap = new HashMap<String,String>();
			if(!claimsMap.isEmpty()){
				claimsMap.forEach((k,v) -> {
					if(!"iss".equals(k) && !"exp".equals(k)){
						resultMap.put(k, v.asString());
					}
		    	});
			}
			return resultMap;
		} catch (JWTVerificationException e) {
			e.printStackTrace();
			return null;
		}
    }

}
