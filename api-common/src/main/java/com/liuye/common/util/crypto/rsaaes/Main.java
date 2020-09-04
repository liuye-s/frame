package com.liuye.common.util.crypto.rsaaes;

/**
 * @author Taoyimin
 * @create 2019 05 06 17:31
 */
public class Main {
    public static void main1(String[] args) throws Exception {
        //客户端代码
        String text = "hello server!";

        //随机生成16位aes密钥
        byte[] aesKey = SecureRandomUtil.getRandom(16).getBytes();
        System.out.println("生成的aes密钥:\n" + new String(aesKey));

        //使用aes密钥对数据进行加密
        String encryptText = AESCipher.encrypt(aesKey, text);
        System.out.println("经过aes加密后的数据:\n" + encryptText);

        //使用客户端私钥对aes密钥签名
        String signature = RSACipher.sign(Config.CLIENT_PRIVATE_KEY, aesKey);
        System.out.println("签名:\n" + signature);

        //使用服务端公钥加密aes密钥
        byte[] encryptKey = RSACipher.encrypt(Config.SERVER_PUBLIC_KEY, aesKey);
        System.out.println("加密后的aes密钥:\n" + new String(encryptKey));

        //客户端发送密文、签名和加密后的aes密钥
        System.out.println("\n************************分割线************************\n");
        //接收到客户端发送过来的signature encrypt_key encrypt_text

        //服务端代码
        //使用服务端私钥对加密后的aes密钥解密
        byte[] aesKey1 = RSACipher.decrypt(Config.SERVER_PRIVATE_KEY, encryptKey);
        System.out.println("解密后的aes密钥:\n" + new String(aesKey1));

        //使用客户端公钥验签
        Boolean result = RSACipher.checkSign(Config.CLIENT_PUBLIC_KEY, aesKey1, signature);
        System.out.println("验签结果:\n" + result);

        //使用aes私钥解密密文
        String decryptText = AESCipher.decrypt(aesKey1, encryptText);
        System.out.println("经过aes解密后的数据:\n" + decryptText);
    }

    public static void main(String[] args) throws Exception {

        String text = "色哦阿拉丁法拉利发大水啊";
        String decryptText = new String(Base64.encodeBase64(RSACipher.encrypt(Config.CLIENT_PUBLIC_KEY, text.getBytes())));
        System.out.println("加密后的数据:\n" + decryptText);

        //String privKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAvY3CZAiLofRPgYTc075nB/rmFv2NXYZHe8sjfaSSGdKVoIVZzAYZTzd95VTODFNvoqk0bsKxW7at6j0wT9RMtQIDAQABAkEAmLtOdFfGWrqGDXBGln6GehGZr9ChRUha9M5bTHN8HT0BnecU6iEiTLa+Md9TBmZg3oRArgo95MQp4G7yg4pUiQIhAPXRXwlnBPlqzidyyIBEvmTbZt6qUHMoHcDR3lHWLObrAiEAxWfE2OMZUsldWWRbC2I2/GyT8vSSc/XTIJjE2vQH8t8CIEh6KfZAvLO5bP0ul7VSD3z3P5ZpSv0ZF+SxPEaOyoBvAiAvlqd+vaiiK4cEgMvt/5xP2AEGpe0UZWOjTRDP09qCswIgJUu1CCTvrCELzaUtc1Svp0SJ6jmcdDUMrylDSw693MY=";
        //String decryptText = "hL3RMkTagEhYsoXjP1C82ViU6FaJWPdb5gy9tIs/gFf8SOPyXpSvZYkRdTwpGOZKPKMwb8Uci/dbGawjrtLWpw==";
        //decryptText = "6GuLkQxZ5VEVDGMzzVEOmVhgNjVSuV+aZiWJ0dfCuE4YCxwGejhpXEYyw6j6kDW0iHgzFM22jLF7czF3UbOmuPqaQ8Vjq4NyhsRgNPZ0lTOvTqHsD30fOcnn7FuXEBzfeiQJi6ADv/il7EAPURRgc9m5dXJ/+rvgs+pqb/2n6A4=";
        decryptText = "gM1tj8wGRItISds9U1LzZK/hC7+lTNAdcwX67Daeop41fKfgfVBtM3btK8NMnEkJqP31Lh2g+AB/qWim+1WWFSc8sstPLYY7DOkvFOpZxJl484GDvZUgzHDLhuiD61BL+LaX0rJg1Tv4E0I6vqCvswwqCJ/+PdTK5cpK2TMwK3Q=";
        byte[] bytes = RSACipher.decrypt(Config.CLIENT_PRIVATE_KEY, Base64.decodeBase64(decryptText.getBytes()));
        System.out.println("解密后的数据:\n" + new String(bytes));
    }

}
