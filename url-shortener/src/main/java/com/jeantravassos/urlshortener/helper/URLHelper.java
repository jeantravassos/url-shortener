package com.jeantravassos.urlshortener.helper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class URLHelper {

	private URLHelper() {
		throw new IllegalStateException("Helper class");
	}
	
	private static final int RADIX = 16;
	private static final int HASH_LENGTH = 32;
	private static final String MD5_ALGORITHM = "MD5";

	public static String shorteningURL(
			String originalURL, 
			int start, 
			int end) 
	{
		String md5 = getMd5(originalURL);
		String base64 = getBase64(md5);
		String hash = base64.replace('/', '_').replace('+', '-');

		return hash.substring(start, end + 1);
	}
	
	private static String getBase64(String md5) {
		return Base64.getEncoder().encodeToString(md5.getBytes());
	}
	
	private static String getMd5(String originalURL) {
		try {
			byte[] message = MessageDigest.getInstance(MD5_ALGORITHM).digest(originalURL.getBytes());

			String hash = new BigInteger(1, message).toString(RADIX);
			hash = hash.length() < HASH_LENGTH ? "0".concat(hash) : hash;

			return hash;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 is not available", e);
		}
	}
}
