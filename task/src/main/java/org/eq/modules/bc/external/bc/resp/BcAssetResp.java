package org.eq.modules.bc.external.bc.resp;

public class BcAssetResp {
	private String assetCode;
	private String assetIssuer;
	private long amount;
	private String goodsId;
	private Integer assetDecimals;
	public String getAssetCode() {
		return assetCode;
	}
	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}
	public String getAssetIssuer() {
		return assetIssuer;
	}
	public void setAssetIssuer(String assetIssuer) {
		this.assetIssuer = assetIssuer;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getAssetDecimals() {
		return assetDecimals;
	}
	public void setAssetDecimals(Integer assetDecimals) {
		this.assetDecimals = assetDecimals;
	}
	
}
