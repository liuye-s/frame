package com.liuye.common.util.crypto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class SignatureData {
	private static final Log logger = LogFactory.getLog(SignatureData .class);
	
	public static String signature(String info,String privkey) {
		String sign="";
		try {
			String prikeyvalue =privkey;
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(hexStrToBytes(prikeyvalue));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey myprikey = keyf.generatePrivate(priPKCS8);

			String myinfo = info;// 要签名的信息
			// 用私钥对信息生成数字签名
			java.security.Signature signet = java.security.Signature.getInstance("SHA1withRSA");
			signet.initSign(myprikey);
			signet.update(myinfo.getBytes("UTF-8"));//ISO-8859-1
			byte[] signed = signet.sign(); // 对信息的数字签名
                        sign=bytesToHexStr(signed);
			//System.out.println(new String(signed));
			//System.out.println("signed(签名内容)原值=" + sign);
			//System.out.println("info（原值）=" + myinfo);

			logger.info("签名并生成文件成功");
		} catch (Exception e) {
			logger.info("签名并生成文件失败"+e.getMessage());
		}
		return sign;
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

	private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//        String pubkey="30819f300d06092a864886f70d010101050003818d0030818902818100d56f568b67a44ad7af55014657646e704c78ff097d047e706c04309ac25b015db30e5b1b0233f28d98539399a633b450691916d68552785a3990686a96fad237439ab7283eb8f8906b38775c09d41f98ab82ba3caaa5137fa4544d5ec747837937c6b7a6f121369c864536d10ff96c36aad64bff98795649fe447aa48f7ef2170203010001";
//		String prikeyvalue = "30820277020100300d06092a864886f70d0101010500048202613082025d02010002818100d56f568b67a44ad7af55014657646e704c78ff097d047e706c04309ac25b015db30e5b1b0233f28d98539399a633b450691916d68552785a3990686a96fad237439ab7283eb8f8906b38775c09d41f98ab82ba3caaa5137fa4544d5ec747837937c6b7a6f121369c864536d10ff96c36aad64bff98795649fe447aa48f7ef21702030100010281806819c6ef759c97787da70b5920b4f67cc57fda7ce94230dd6a81dca70f16ce22c27bc509f65819fce0081b9cbc88c970d2e20ed690ee798f20368407411e933ac660b0bd961ff83b033cd66a839b53059767210ee15b4e74b7fe342fa82217beead34a8df8230a8aa8435e867327b6182e660f46097732db2dc04ef5416a2b29024100fe7e59120de2b84399e4add55e52f58f4decb8f5048296cdd777d5ba3ad47379dee59bdb9ac088ab5a82bf6b1521c4505281d6a05d30589810f329b9d0ed8d9b024100d6b2c565c038a6e682920291d3720dfd96dbdc9984e9b2cf3ca1cf4cbcefbe6b8e959b61373f5d97b4e6c387f4a0565d0c033448b689bd3eda30569d5c5c7335024100f067c0925a85a295518a01607ef3153f23588cbc005fb03f90dc860c9f4854317b0b21b948adba2d4c9a1862fa9a644d903350e13a46d25a0c20f878acd1cb23024100897fec9401cb4678925ed86e7430b36d1954adaf5444275439cc976ada05e0ba19930ef3efe2111abbac9ec54f91f03d9f17de4043476b7ff954c3e3fbe6e99102404ee3c40c67c723330839cd13c0ac15a1f09f375192ef1fea3e479d5c0ef0cda8599724e22d9493c5876c2ad73933c519986eec8568513eb2f88c7468f8195724";// 这是GenerateKeyPair输出的私钥编码
//		Map<String,String> param=new HashMap<String,String>();
//    	param.put("filename", "\\0\\doc\\dd962c35-710f-4c79-979e-fb0f042313a7.docx");
//    	param.put("timestamp", "20190212153530");
//    	String json=JSON.toJSONString(param, SerializerFeature.SortField);
//		SignatureData s = new SignatureData();
//		String sig=s.signature(json,prikeyvalue);
//		System.out.println("---"+sig);
//	}
}
