package com.liuye.common.util.crypto.rsa;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.liuye.common.util.codec.Encodes;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RsaUtil {

    /**
     * 生成公钥和私钥 
     * @throws NoSuchAlgorithmException
     *
     */
    public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException{
        HashMap<String, Object> map = new HashMap<String, Object>();
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(512);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        map.put("public", publicKey);
        map.put("private", privateKey);
        return map;
    }
    /**
     * 使用模和指数生成RSA公钥 
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA 
     * /None/NoPadding】 
     *
     * @param modulus
     *            模 
     * @param exponent
     *            指数 
     * @return
     */
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用模和指数生成RSA私钥 
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA 
     * /None/NoPadding】 
     *
     * @param modulus
     *            模 
     * @param exponent
     *            指数 
     * @return
     */
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 公钥加密 
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey)
            throws Exception {
        byte[] bytes = data.getBytes();
        data = new String(bytes,"ISO8859-1"); //转成iso8859-1，使String中每个字符占一个字节，以便对中文字符正确分组加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 模长  
        int key_len = publicKey.getModulus().bitLength() / 8;
        // 加密数据长度 <= 模长-11  
        String[] datas = splitString(data, key_len - 11);
        String mi = "";
        //如果明文长度大于模长-11则要分组加密  
        for (String s : datas) {
            mi += bcd2Str(cipher.doFinal(s.getBytes("ISO8859-1")));
        }
        return mi;
    }

    /**
     * 私钥解密 
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        //模长  
        int key_len = privateKey.getModulus().bitLength() / 8;
        byte[] bytes = data.getBytes();
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
        //System.out.println(bcd.length);
        //如果密文长度大于模长则要分组解密  
        String ming = "";
        byte[][] arrays = splitArray(bcd, key_len);
        for(byte[] arr : arrays){
            ming += new String(cipher.doFinal(arr),"ISO8859-1");  //因为加密时String是按照ISO8859-1进行编码，解密时需要进行还原
        }
        ming = new String(ming.getBytes("ISO8859-1"));
        return ming;
    }

    /**
     * 私钥解密 (BASE64)
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey_base64(String data, RSAPrivateKey privateKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        //模长
        int key_len = privateKey.getModulus().bitLength() / 8;
        byte[] bytes = Encodes.decodeBase64(data);
        //byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
        //System.out.println(bcd.length);
        //如果密文长度大于模长则要分组解密
        String ming = "";
        //byte[][] arrays = splitArray(bcd, key_len);
        byte[][] arrays = splitArray(bytes, key_len);
        for(byte[] arr : arrays){
            ming += new String(cipher.doFinal(arr));  //因为加密时String是按照ISO8859-1进行编码，解密时需要进行还原
        }
        ming = new String(ming.getBytes());
        return ming;
    }

    /**
     * 公钥加密 
     */
    public static String encryptByPublicKey(String data, String publicKeyHexStr)
            throws Exception {
        byte[] keyBytes = GenerateKeyPair.hexStrToBytes(publicKeyHexStr);
        //取得公钥  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey key = (RSAPublicKey)keyFactory.generatePublic(x509KeySpec);
        return encryptByPublicKey(data, key);
    }

    /**
     * 私钥解密 
     *
     */
    public static String decryptByPrivateKey(String data, String privateKeyHexStr)
            throws Exception {
        byte[] keyBytes = GenerateKeyPair.hexStrToBytes(privateKeyHexStr);
        // 取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey key = (RSAPrivateKey)keyFactory.generatePrivate(pkcs8KeySpec);
        return decryptByPrivateKey(data, key);
    }

    /**
     * 私钥解密 (BASE64)
     *
     */
    public static String decryptByPrivateKey_base64(String data, String privateKeyStr)
            throws Exception {
        byte[] keyBytes = Encodes.decodeBase64(privateKeyStr);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey key = (RSAPrivateKey)keyFactory.generatePrivate(pkcs8KeySpec);
        return decryptByPrivateKey_base64(data, key);
    }


    /**
     * ASCII码转BCD码 
     *
     */
    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }
    public static byte asc_to_bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9')){
            bcd = (byte) (asc - '0');}
        else if ((asc >= 'A') && (asc <= 'F')){
            bcd = (byte) (asc - 'A' + 10);}
        else if ((asc >= 'a') && (asc <= 'f')){
            bcd = (byte) (asc - 'a' + 10);}
        else{
            bcd = (byte) (asc - 48);}
        return bcd;
    }
    /**
     * BCD转字符串 
     */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * 拆分字符串 
     */
    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i=0; i<x+z; i++) {
            if (i==x+z-1 && y!=0) {
                str = string.substring(i*len, i*len+y);
            }else{
                str = string.substring(i*len, i*len+len);
            }
            strings[i] = str;
        }
        return strings;
    }
    /**
     *拆分数组  
     */
    public static byte[][] splitArray(byte[] data,int len){
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if(y!=0){
            z = 1;
        }
        byte[][] arrays = new byte[x+z][];
        byte[] arr;
        for(int i=0; i<x+z; i++){
            arr = new byte[len];
            if(i==x+z-1 && y!=0){
                System.arraycopy(data, i*len, arr, 0, y);
            }else{
                System.arraycopy(data, i*len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }

    public static void main(String[] args) throws Exception {

        /*
        HashMap<String, Object> map = RsaUtil.getKeys();
        //生成公钥和私钥  
        RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
        RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");

        //模  
        String modulus = publicKey.getModulus().toString();
        //公钥指数  
        String public_exponent = publicKey.getPublicExponent().toString();
        //私钥指数  
        String private_exponent = privateKey.getPrivateExponent().toString();
        //明文  
        String ming = "123456789";
        //使用模和指数生成公钥和私钥  
        RSAPublicKey pubKey = RsaUtil.getPublicKey(modulus, public_exponent);
        RSAPrivateKey priKey = RsaUtil.getPrivateKey(modulus, private_exponent);

        //加密后的密文  
        String mi = RsaUtil.encryptByPublicKey(ming, pubKey);
        System.out.println(mi);
        //解密后的明文  
        ming = RsaUtil.decryptByPrivateKey(mi, priKey);
        System.out.println(ming);
        */


        String pubKeyStr="305c300d06092a864886f70d0101010500034b003048024100bd8dc264088ba1f44f8184dcd3be6707fae616fd8d5d86477bcb237da49219d295a08559cc06194f377de554ce0c536fa2a9346ec2b15bb6adea3d304fd44cb50203010001";
        String priKeyStr="30820154020100300d06092a864886f70d01010105000482013e3082013a020100024100bd8dc264088ba1f44f8184dcd3be6707fae616fd8d5d86477bcb237da49219d295a08559cc06194f377de554ce0c536fa2a9346ec2b15bb6adea3d304fd44cb5020301000102410098bb4e7457c65aba860d7046967e867a1199afd0a145485af4ce5b4c737c1d3d019de714ea21224cb6be31df53066660de8440ae0a3de4c429e06ef2838a5489022100f5d15f096704f96ace2772c88044be64db66deaa5073281dc0d1de51d62ce6eb022100c567c4d8e31952c95d59645b0b6236fc6c93f2f49273f5d32098c4daf407f2df0220487a29f640bcb3b96cfd2e97b5520f7cf73f96694afd1917e4b13c468eca806f02202f96a77ebda8a22b870480cbedff9c4fd80106a5ed146563a34d10cfd3da82b30220254bb50824efac210bcda52d7354afa74489ea399c74350caf29434b0ebddcc6";
        String test = "dfafa是旦发生大幅金卡是的附件阿斯兰附件卡送大礼分开了；是否健康了；阿斯顿发卡拉萨的计费阿斯顿发打开；是否阿斯顿就啊速度快发";
        String mi = RsaUtil.encryptByPublicKey(test, pubKeyStr);
        System.out.println(mi);
        //解密后的明文  
        String ming = RsaUtil.decryptByPrivateKey(mi, priKeyStr);
        System.out.println(ming);

        Map msg = new HashMap();
        msg.put("userCode", "admin");
        msg.put("passWord", "123456");
        //msg.put("timestamp", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        msg.put("timestamp", System.currentTimeMillis());
        String json = JSON.toJSONString(msg, SerializerFeature.SortField, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullListAsEmpty);
        System.out.println(json);
        String token = RsaUtil.encryptByPublicKey(json, pubKeyStr);
        System.out.println("_sso_token="+ token);

        System.out.println(RsaUtil.decryptByPrivateKey(token, priKeyStr));

        //System.out.println(RsaUtil.decryptByPrivateKey_base64("hL3RMkTagEhYsoXjP1C82ViU6FaJWPdb5gy9tIs/gFf8SOPyXpSvZYkRdTwpGOZKPKMwb8Uci/dbGawjrtLWpw==", priKeyStr));

        String CLIENT_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAO/8ucCgOTJ7DCPC" +
                "rCCL1VKDnUX61QnxwbAvpGp1/lletEIcjUouM7F0VvMHzViNLvpw7N7NBHPa+5gO" +
                "js68t9hKMUh+a6RTE34SWIqSDRPCzDKVWugsFb04o3vRl3rZ1z6B+QDdW7xwOhEr" +
                "PPoEqmjjIOjQPcU6xs0SPzSimOa1AgMBAAECgYAO5m0OBaSnerZNPhf7yVLMVbmd" +
                "D67MeEMjUkHuDjdlixi8BhPLqESzXtrLKg/Y0KM7D2nVh3sgSldWoIjDUzpCx8Z2" +
                "yHLU1K2wakMdBgEF3xeJPxxZRpP+earl0SyLTA4hMxl48uAjn/mkPgzoMgQkqyQz" +
                "5HOWjjsCLJFyEvqmoQJBAP5cBk0KXpHnCMgOupbi/pXDyaF1o+dCE97GaEdrV/0P" +
                "uwDfYDYfY3wzd1QM7C4b4MmE+SNVpC0W9PyaMONJlN0CQQDxiPiGdwX9actMNJea" +
                "JZ+k3BjCN+mM6Px7j/mtYcXWNZkyCXSXUBI62drZ0htenrh2qwichMlMgNJClvG6" +
                "Gu+5AkEA30R7q2gstrkrNh/nnMZHXcJr3DPc2QNhWayin/4TT+hc51krpJZMxxqN" +
                "5dMqBRcnavwzi9aCs6lxBcF6pCdUaQJANhd7uPls4PzRZ6abkQz9/LjB3rUQ29rN" +
                "uIpc2yR7XuawAVG2x7BJ9N4XMhLoyD75hrH1AsCGKFjtPbZ6OjiQGQJAF2DbIodC" +
                "uYb6eMZ8ux1Ab0wBEWWc5+iGgEVBNh22uZ/klE1/C0+KKzZhqgzaA/vPapq6dhuJ" +
                "sNXlJia10PwYrQ==";
        System.out.println(RsaUtil.decryptByPrivateKey_base64("6GuLkQxZ5VEVDGMzzVEOmVhgNjVSuV+aZiWJ0dfCuE4YCxwGejhpXEYyw6j6kDW0iHgzFM22jLF7czF3UbOmuPqaQ8Vjq4NyhsRgNPZ0lTOvTqHsD30fOcnn7FuXEBzfeiQJi6ADv/il7EAPURRgc9m5dXJ/+rvgs+pqb/2n6A4=", CLIENT_PRIVATE_KEY));

    }

}
