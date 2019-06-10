package com.lccx.manager.util;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


/**
 * 编码工具类 实现aes加密、解密
 */
public class EncryptUtils {

	/**
	 * 密钥(密钥必须是16位)
	 */
	private static final String KEY = "9580d469a609f07765b76b7aefe1148c";

	/**
	 * 算法
	 */
	private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

	public static void main(String[] args) throws Exception {
		String key="31e5ad245583db1e79ca48c00dfe8543";
		System.out.println(key);

		String content = "123456";
//		System.out.println("加密前：" + content);
//
//		System.out.println("加密密钥和解密密钥：" + KEY);
//
		String encrypt = aesEncrypt(content, key);
		System.out.println("加密后：" + encrypt);
//
//		String decrypt = aesDecrypt("j7gJPhqlpvAgRl3kLcZ48g==", key);
//		System.out.println("解密后：" + decrypt);
//		System.out.println(aesEncrypt("111","9580d469a609f07765b76b7aefe1148c"));
//		getKey();
	}


	/**
	 * aes解密
	 *
	 * @param encrypt
	 *            内容
	 * @return
	 * @throws Exception
	 */
	public static String aesDecrypt(String encrypt) throws Exception {
		return aesDecrypt(encrypt, KEY);
	}

	/**
	 * aes加密
	 *
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String aesEncrypt(String content) throws Exception {
		return aesEncrypt(content, KEY);
	}

	/**
	 * 将byte[]转为各种进制的字符串
	 *
	 * @param bytes
	 *            byte[]
	 * @param radix
	 *            可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
	 * @return 转换后的字符串
	 */
	public static String binary(byte[] bytes, int radix) {
		return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
	}

	/**
	 * base 64 encode
	 *
	 * @param bytes
	 *            待编码的byte[]
	 * @return 编码后的base 64 code
	 */
	public static String base64Encode(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	/**
	 * base 64 decode
	 *
	 * @param base64Code
	 *            待解码的base 64 code
	 * @return 解码后的byte[]
	 * @throws Exception
	 */
	public static byte[] base64Decode(String base64Code) throws Exception {
		return StringUtils.isEmpty(base64Code) ? null : Base64.decodeBase64(base64Code);
	}

	/**
	 * AES加密
	 *
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的byte[]
	 * @throws Exception
	 */
	public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);
		Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

		return cipher.doFinal(content.getBytes("utf-8"));
	}

	/**
	 * AES加密为base 64 code
	 *
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的base 64 code
	 * @throws Exception
	 */
	public static String aesEncrypt(String content, String encryptKey) throws Exception {
		return base64Encode(aesEncryptToBytes(content, encryptKey));
	}

	/**
	 * AES解密
	 *
	 * @param encryptBytes
	 *            待解密的byte[]
	 * @param decryptKey
	 *            解密密钥
	 * @return 解密后的String
	 * @throws Exception
	 */
	public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG") ;
		secureRandom.setSeed(decryptKey.getBytes());
		kgen.init(128,secureRandom);

		Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
		byte[] decryptBytes = cipher.doFinal(encryptBytes);

		return new String(decryptBytes, "UTF-8");
	}

	/**
	 * 将base 64 code AES解密
	 *
	 * @param encryptStr
	 *            待解密的base 64 code
	 * @param decryptKey
	 *            解密密钥
	 * @return 解密后的string
	 * @throws Exception
	 */
	public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
		return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
	}


	/**
	 * 获得秘钥
	 */
	public static String getKey() {
		String s=null;
		try {
			KeyGenerator kg = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG") ;

			kg.init(128,secureRandom);
			//要生成多少位，只需要修改这里即可128, 192或256
			SecretKey sk = kg.generateKey();
			byte[] b = sk.getEncoded();
			s= byteToHexString(b);
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.out.println("没有此算法。");
		}
		return s;
	}

	/**
	 * byte数组转化为16进制字符串
	 * @param bytes
	 * @return
	 */
	public static String byteToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String strHex=Integer.toHexString(bytes[i]);
			if(strHex.length() > 3) {
				sb.append(strHex.substring(6));
			} else {
				if(strHex.length() < 2) {
					sb.append("0" + strHex);
				} else {
					sb.append(strHex);
				}
			}
		}
		return sb.toString();
	}

}