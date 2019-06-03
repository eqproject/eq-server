package org.eq.modules.bc.enums;

public enum BcStatusEnum {
	INIT(0,"初始化"),
	PROCESS(1, "处理中"),
	SUCCESS(2,"成功"),
	FAIL(3,"失败"),
	;
	private Integer code;
	private String name;
	
	private BcStatusEnum(Integer code, String name) {
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
