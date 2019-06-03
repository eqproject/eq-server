package org.eq.modules.bc.external.accountCenter.resp;

import java.util.List;

public class AccountCenterSignBlobListResp {
	
	private String blob;
	private List<ACSignBlobVO> signBlobList;
	public String getBlob() {
		return blob;
	}
	public void setBlob(String blob) {
		this.blob = blob;
	}
	public List<ACSignBlobVO> getSignBlobList() {
		return signBlobList;
	}
	public void setSignBlobList(List<ACSignBlobVO> signBlobList) {
		this.signBlobList = signBlobList;
	}
	
}
