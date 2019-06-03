package org.eq.modules.bc.common;

import java.util.UUID;

public class UUIDGenerator {
	
	public static String getUUID32() {
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}

}
