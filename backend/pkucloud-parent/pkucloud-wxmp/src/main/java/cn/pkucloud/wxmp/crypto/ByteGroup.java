package cn.pkucloud.wxmp.crypto;

import java.util.ArrayList;

class ByteGroup {
	ArrayList<Byte> byteContainer = new ArrayList<>();

	public byte[] toBytes() {
		int size = byteContainer.size();
		byte[] bytes = new byte[size];
		for (int i = 0; i < size; ++i) {
			bytes[i] = byteContainer.get(i);
		}
		return bytes;
	}

	public ByteGroup addBytes(byte[] bytes) {
		for (byte b : bytes) {
			byteContainer.add(b);
		}
		return this;
	}

	public int size() {
		return byteContainer.size();
	}
}
