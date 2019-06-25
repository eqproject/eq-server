package org.eq.modules.bc.external.bc.req;

import lombok.Data;

@Data
public class BcTransferReq {
	
	private String fromAddress;
	private String toAddress;
	private Long amount;
	private Long fee;
	private String metadata;
	private String issuer;
	private int txType;
	private String input;
	private String contractAddress;
}
