package com.liuye.common.util.crypto.des;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

//import javax.crypto.spec.DESKeySpec;

public class DesUtil {

    private static boolean debug = false;
    private static String Algorithm = "DES";
    private static String AlgorithmWithIV = "DES/CBC/PKCS5Padding";
    private static byte[] key_default = new byte[] { (byte) 0x20, (byte) 0xE5,
            (byte) 0x9E, (byte) 0x73, (byte) 0x26, (byte) 0x61, (byte) 0x4C,
            (byte) 0x68 };

    private String key;

    private String iv = null;

    public DesUtil() {
        this.key = byte2hex(key_default);
    }

    public DesUtil(String key) {
        this.key = key;
    }

    public DesUtil(String key, String iv) {
        this.key = key;
        this.iv = iv;
    }

    public String encrypt(String data) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        if (data==null || data.equals(""))
            return data;
        return byte2hex(this.encrypt(data.getBytes()));
    }

    public String encrypt(String data, String charsetName) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
        if (data==null || data.equals(""))
            return data;
        return byte2hex(this.encrypt(data.getBytes(charsetName)));
    }

    public String decrypt(String data) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        if (data==null || data.equals(""))
            return data;
        return new String(this.decrypt(hex2byte(data)));
    }

    public String decrypt(String data, String charsetName) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        if (data==null || data.equals(""))
            return data;
        return new String(this.decrypt(hex2byte(data)), Charset.forName(charsetName));
    }

    /**
     * 将指定的数据根据提供的密钥进行加密
     * @param data
     *            需要加密的数据
     * @return byte[] 加密后的数据
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     */
    public byte[] encrypt(byte[] data) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        byte[] b_key = hex2byte(key);
        if (debug) {
            System.out.println("加密前的二进串:" + byte2hex(data));
            System.out.println("加密前的字符串:" + new String(data));
        }
        Cipher c1 = null;

        SecretKey deskey = new javax.crypto.spec.SecretKeySpec(b_key, Algorithm);
        if (iv==null || iv.trim().equals("")) {
            // ECB
            c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
        } else {
            // CBC
            c1 = Cipher.getInstance(AlgorithmWithIV);
            byte[] b_iv = hex2byte(iv);
            IvParameterSpec spec = new IvParameterSpec(b_iv);
            c1.init(Cipher.ENCRYPT_MODE, deskey, spec);
        }

        byte[] cipherByte = c1.doFinal(data);
        if (debug) {
            System.out.println("加密后的二进串:" + byte2hex(cipherByte));
        }

        return cipherByte;
    }

    /**
     * 将给定的已加密的数据通过指定的密钥进行解密
     * @param data
     *            待解密的数据
     * @return byte[] 解密后的数据
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeySpecException
     * @throws InvalidAlgorithmParameterException
     */
    public byte[] decrypt(byte[] data) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        byte[] b_key = hex2byte(key);
        if (debug) {
            System.out.println("解密前的信息:" + byte2hex(data));
        }
        Cipher c1 = null;

        SecretKey deskey = new javax.crypto.spec.SecretKeySpec(b_key, Algorithm);
        if (iv==null || iv.trim().equals("")) {
            // ECB
            c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
        } else {
            // CBC
            c1 = Cipher.getInstance(AlgorithmWithIV);
            byte[] b_iv = hex2byte(iv);
            IvParameterSpec spec = new IvParameterSpec(b_iv);
            c1.init(Cipher.DECRYPT_MODE, deskey, spec);
        }
        byte[] clearByte = c1.doFinal(data);
        if (debug) {
            System.out.println("解密后的二进串:" + byte2hex(clearByte));
            System.out.println("解密后的字符串:" + (new String(clearByte)));
        }

        return clearByte;
    }

    /**
     * 字节码转换成16进制字符串
     * @param b 输入要转换的字节码
     * @return String 返回转换后的16进制字符串
     */
    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    /**
     * @see 16进制字符串转换成字节码
     * @param src
     *            输入要转换的16进制字符串
     * @return byte[] 返回转换后的字节码
     */
    private static byte[] hex2byte(String src) {
        int m = 0, n = 0;
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
        }
        return ret;
    }

    private static byte uniteBytes(String src0, String src1) {
        byte b0 = Byte.decode("0x" + src0).byteValue();
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + src1).byteValue();
        byte ret = (byte) (b0 | b1);
        return ret;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public static void main(String[] args) throws Exception {
        DesUtil desUtil = new DesUtil("3132333438373635", "1234567890ABCDEF");
        System.out.println(desUtil.encrypt("123456"));
        System.out.println(desUtil.decrypt("E43517DFC9B52EBD"));
    }
}
