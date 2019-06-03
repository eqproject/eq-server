package org.eq.modules.bc.enums;

public enum BcTxExceptionStatusEnum {
	INIT(0,"未处理"),
	FINISH(1, "已处理"),
	;
	private Integer code;
	private String name;
	
	private BcTxExceptionStatusEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}
	
}
