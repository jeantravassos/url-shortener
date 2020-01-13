package com.jeantravassos.urlshortener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.jeantravassos.urlshortener.helper.URLHelper;

public class URLHelperTest {

	@Test
	public void identicUrlsShouldResultEqualHash() {
		String url = "www.jeantravassos.com";
		int startIndex = 0;
		int endIndex = 5;
		
		String hash1 = URLHelper.shorteningURL(url, startIndex, endIndex);
		String hash2 = URLHelper.shorteningURL(url, startIndex, endIndex);
		
		assertEquals(hash1, hash2);
	}
	
	@Test
	public void differentURLsShouldResultNotEqualsHashes() {
		String urlJ = "www.jeantravassos.com";
		String urlI = "www.internet.com";
		int startIndex = 0;
		int endIndex = 5;
		
		String hash1 = URLHelper.shorteningURL(urlJ, startIndex, endIndex);
		String hash2 = URLHelper.shorteningURL(urlI, startIndex, endIndex);
		
		assertNotEquals(hash1, hash2);
	}
	
	@Test
	public void hashSizeMustBeConsistentWithStartAndEndIndexes() {
		String url = "www.jeantravassos.com";
		int startIndex = 0;
		int endIndex = 5;
		
		String hash = URLHelper.shorteningURL(url, startIndex, endIndex);
		
		assertEquals(endIndex - startIndex + 1, hash.length());
	}

	
}
