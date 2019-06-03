package org.eq.modules.bc.external.accountCenter.resp;

public class AccCenterSignBlobResp {

	private String publicKey;
    private String blob;
    private String signBlob;
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getBlob() {
		return blob;
	}
	public void setBlob(String blob) {
		this.blob = blob;
	}
	public String getSignBlob() {
		return signBlob;
	}
	public void setSignBlob(String signBlob) {
		this.signBlob = signBlob;
	}
    
}
