package org.lanqiao.util;

import java.util.UUID;

public class UUIDString {

	public static String getId(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0,5);
	}
}