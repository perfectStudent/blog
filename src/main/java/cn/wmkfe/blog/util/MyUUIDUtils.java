package cn.wmkfe.blog.util;

import java.util.UUID;

public class MyUUIDUtils {
	
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-","");
	}
}
