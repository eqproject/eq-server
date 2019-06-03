package org.eq.modules.bc.external.bc.enums;

public enum StatusEnum {
	
	SUCCESS(0, "成功"),
	FAIL(1, "失败"),
	PROCESSING(-1, "处理中"),
    ;
	
	
    private final Integer code;
    private final String msg;

    private StatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

	public String getMsg() {
		return msg;
	}
    
}
