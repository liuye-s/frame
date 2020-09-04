package com.liuye.common.util.crypto.rsa;

import com.liuye.common.util.codec.Encodes;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/**
 * 生成密钥对
 */
public class GenerateKeyPair {
    private static final Log logger = LogFactory.getLog(GenerateKeyPair.class);
    private String priKey;
    private String pubKey;
    private String priKey_base64;
    private String pubKey_base64;
    private byte[] pubkeys;
    private byte[] prikeys;
    private static final int KEYSIZE=1024;
    public String getPriKey() {
        return priKey;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void generateKeyPair(String init) {
        try {
            java.security.KeyPairGenerator keygen = java.security.KeyPairGenerator.getInstance("RSA");
            SecureRandom secrand = new SecureRandom();
            secrand.setSeed(init.getBytes()); // 初始化随机产生器
            keygen.initialize(KEYSIZE, secrand);
            KeyPair keys = keygen.genKeyPair();

            PublicKey pubkey = keys.getPublic();
            PrivateKey prikey = keys.getPrivate();

            pubKey = bytesToHexStr(pubkey.getEncoded());
            priKey = bytesToHexStr(prikey.getEncoded());
//                        pubkeys=Base64.encode(pubkey.getEncoded());
//                        prikeys=Base64.encode(prikey.getEncoded());
            pubKey_base64 = Encodes.encodeBase64(pubkey.getEncoded());
            priKey_base64 = Encodes.encodeBase64(prikey.getEncoded());

            System.out.println("pubKey=" + pubKey);
            System.out.println("priKey=" + priKey);
//            System.out.println("pubkey=" + new String(pubkeys));
//            System.out.println("prikey=" + new String(prikeys));
            System.out.println("pubKey(BASE64)=" + pubKey_base64);
            System.out.println("priKey(BASE64)=" + priKey_base64);
            logger.info("写入对象 pubkeys 成功");
            logger.info("生成密钥对成功");
        } catch (Exception e) {
            logger.info("生成密钥对失败"+e.getMessage());
        }

    }

    /**
     * Transform the specified byte into a Hex String form.
     */
    public static final String bytesToHexStr(byte[] bcd) {
        StringBuffer s = new StringBuffer(bcd.length * 2);

        for (int i = 0; i < bcd.length; i++) {
            s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
            s.append(bcdLookup[bcd[i] & 0x0f]);
        }

        return s.toString();
    }

    /**
     * Transform the specified Hex String into a byte array.
     */
    public static final byte[] hexStrToBytes(String s) {
        byte[] bytes;

        bytes = new byte[s.length() / 2];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
                    16);
        }

        return bytes;
    }

    private static final char[] bcdLookup = { '0', '1','2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * @param args
     */
    public static void main(String[] args) {
        GenerateKeyPair n = new GenerateKeyPair();
        //n.generateKeyPair("sdferewrop[;/.fakdopfi932jmjbia90df909239jmmvvu8721937556vbnm-");
        //n.generateKeyPair("2:$j%e'.i$?.2\"i'q,p(n{5#z@q!([b&*e%:;y.^4fte:nz>x/{\"nmz[?%zcoq.l");
        n.generateKeyPair(">'[ea7c)b;?12&..%{8p*9t%{<j/h)?n;kbw0qfi<{^#b/a))x4e}[o'axqs%8diy2jo(*8o)f91:bo{&k3m!?o:x7@j'd6ssfz9d6g1^>ev3x0uf;@@\"qpi.::q67ow");
    }
}
