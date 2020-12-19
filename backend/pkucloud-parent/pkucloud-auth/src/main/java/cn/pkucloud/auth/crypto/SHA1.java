package cn.pkucloud.auth.crypto;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

public class SHA1 {
	public static String calcSHA1(String token, int timestamp, String nonce, String encrypt) {
		String[] array = new String[] { token, String.valueOf(timestamp), nonce, encrypt };
		StringBuilder sb = new StringBuilder();
		Arrays.sort(array);
		for (int i = 0; i < 4; ++i) {
			sb.append(array[i]);
		}
		String str = sb.toString();
		return DigestUtils.sha1Hex(str);
	}
}
