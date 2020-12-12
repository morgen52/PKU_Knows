package cn.pkucloud.wxmp.crypto;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class PKCS7Encoder {
	static Charset CHARSET = StandardCharsets.UTF_8;
	static int BLOCK_SIZE = 32;

	static byte[] encode(int count) {
		int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
		if (amountToPad == 0) {
			amountToPad = BLOCK_SIZE;
		}
		char padChr = chr(amountToPad);
		StringBuilder tmp = new StringBuilder();
		for (int i = 0; i < amountToPad; ++i) {
			tmp.append(padChr);
		}
		return tmp.toString().getBytes(CHARSET);
	}

	static byte[] decode(byte[] decrypted) {
		int length = decrypted.length;
		int pad = decrypted[length - 1];
		if (pad < 1 || pad > 32) {
			pad = 0;
		}
		return Arrays.copyOfRange(decrypted, 0, length - pad);
	}

	static char chr(int a) {
		byte target = (byte) (a & 0xFF);
		return (char) target;
	}
}
