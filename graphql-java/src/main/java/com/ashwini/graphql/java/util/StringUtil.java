package com.ashwini.graphql.java.util;

import java.util.Random;

public class StringUtil {

	public static String createRandomString() {
		Random random = new Random();
		return random.ints(97, 122)
				.limit(20)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
	}

}
