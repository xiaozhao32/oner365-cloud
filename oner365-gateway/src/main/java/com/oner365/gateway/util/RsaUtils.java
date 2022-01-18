package com.oner365.gateway.util;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RSA工具类
 *
 * @author zhaoyong
 */
public class RsaUtils {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(RsaUtils.class);

  public static final String ALGORITHM_RSA = "RSA";

  public static final String ALGORITHM_RSA_SIGN = "SHA256WithRSA";

  public static final int ALGORITHM_RSA_PRIVATE_KEY_LENGTH = 2048;

  private static final int MODE = 2;

  public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAosqXvSzav40EiOMCDOKzmViq6tk45axzW8WF4dvN4wdpSxbP+Ka4tvfPXXfLI6GdaX39fgm75vqkuq9jW1e+Upa4Q+ZcvwBd+DnuP9gvcKgy39mMU524EkdWX0iqXJnozOMjY7fZd4uIkBMTgzL53ZQ4QkuuciJDcqSvxrgbx+GfAh/4Ed8X+ujbdxzCZUXcyMHwtJY27kDA0gx7jYhDWc28G7+tzDWs9VdKO9IepFRFTQmfWSDyEOoW2QnPfPU73Py+0uJ2Z4ZZQ314o8KP6HsU8oRPpQ7ArRFQ1ZeD53B37Ry4U2tfOiXTaM8tdD/q9CUdzXGbjVrR0te4tTrXRQIDAQAB";

  public static final String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCiype9LNq/jQSI4wIM4rOZWKrq2TjlrHNbxYXh283jB2lLFs/4pri2989dd8sjoZ1pff1+Cbvm+qS6r2NbV75SlrhD5ly/AF34Oe4/2C9wqDLf2YxTnbgSR1ZfSKpcmejM4yNjt9l3i4iQExODMvndlDhCS65yIkNypK/GuBvH4Z8CH/gR3xf66Nt3HMJlRdzIwfC0ljbuQMDSDHuNiENZzbwbv63MNaz1V0o70h6kVEVNCZ9ZIPIQ6hbZCc989Tvc/L7S4nZnhllDfXijwo/oexTyhE+lDsCtEVDVl4PncHftHLhTa186JdNozy10P+r0JR3NcZuNWtHS17i1OtdFAgMBAAECggEBAIPmnQ31wwRDkXIMZUPlh9nvIwWInfZSRWINaE45l9ek0Ka7xCTXiYEOxyEXiCvFrlKkd/lgj7Q2T7THmTyQmrA2WPUDORC1jm4rGiT22HFyh04i6fVyLIv66zvnF8pSFnO0cp9Aft7C2xLJshyy+AuWJgNpaN4nbvj8AUSFNlU76HJmpx3tkV9hC80t1yAdKJETvrtYg1k+az3Ynj9n5rsOrXHuaRv16eAbqQOTyAVR3g4EeEfn3jU+WGqusFjg7xTR0q3ZItQYuTZXF1xS/gtmnf9os9rDIBXZT3mUvb5sbJixILCqNsHjMnvSfg2KB+RH11sdCSBNUmjSw8ewOcECgYEA5Li+AGzMNAZkkEg4pZ7KtfDhNXeYqEp6jSjTMWOTpId7HF4sKs7WNlxRGeCsx8uzVzZGYQez/0LRSpLpg8Zt2HaGPEVud92Rn6aWXPWLLt1mIo22rcvUN4RVLsbt3U1aoQBK6MRyhX6oWUJKj5YbAle+CbdI0lSCA7sGSK9XhhUCgYEAtjTjXEy5w12MxxxZ6X/WajJD62s8tX5NPFel9toftbi2LNzeuo9EFK2kGacdvkfCEVrnrdrd5i7irJhRo3jGeeG7SLKCGiNUIZUSblvUGm2ddhMQiqhEQ+pKl5HYjry1fKa5OgWLiX73rtRW4CgHyuNSJIHR7gEyJ3ChOEfICHECgYEAw/fmE8ckT48KqvS2VHFxjeINlBocmlT2G+xWx4JiBh5uxSeLAEKoF3uRoXJ3l9hYmOjP9GL9PkCpwhOOMHHbvBQRf6Kdu+YD156MRqm7zzOzRvfVBaQW+FpSAfo/49VbSYdOp5Fp7idPZur9p3kdOyyijwCTTGNL4pZkE/LoMuECgYBRf8KazG7i5ftMLHBDMz0y7xzWNndHLyFY9lNz4QkB/Ybu9jRh+icXoLugBdIWBH7XyHOMnjNW/G3DibYqiqEsbz2YPNBY5KiDBOzjlAuBbrpZ0Bk8DBvMzQVkWoYlAl+jS+fZ/hT2AWbEOpJJXV5u79MUJUVDUiGe5pAWXnUo4QKBgESqPj7O478xIskESnrzHQFt+9rkVxAeM4NH+PviqD8TdJ/bMN7EXlpiJibR5lJdr5OWJKWlwb2JujsCaPtwHstUKio1dZT8xb6nJbONwrSYgCkYEiHHyqTPfTPT+8w5h8iTQ93kwUBGOhBOuEtThVpH/gMrSBB92wXsCWiUlrv9";
  
  private RsaUtils() {
    
  }

  /**
   * 生成密钥
   *
   * @param keySize 密钥长度
   * @return Map<String, String>
   */
  public static Map<String, String> initRsaKey(int keySize) {
    KeyPairGenerator kpg;
    if (keySize != ALGORITHM_RSA_PRIVATE_KEY_LENGTH) {
      throw new IllegalArgumentException("RSA1024已经不安全，请使用2048初始化RSA密钥对");
    }
    try {
      kpg = KeyPairGenerator.getInstance(ALGORITHM_RSA);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalArgumentException("No such algorithm-->[RSA]");
    }
    kpg.initialize(ALGORITHM_RSA_PRIVATE_KEY_LENGTH);
    KeyPair keyPair = kpg.generateKeyPair();
    Key publicKey = keyPair.getPublic();
    String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
    Key privateKey = keyPair.getPrivate();
    String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
    Map<String, String> keyPairMap = new HashMap<>(8);
    keyPairMap.put("publicKey", publicKeyStr);
    keyPairMap.put("privateKey", privateKeyStr);
    return keyPairMap;
  }

  /**
   * 公钥加密
   *
   * @param data 数据
   * @param key  公钥
   * @return String
   */
  public static String buildRsaEncryptByPublicKey(String data, String key) {
    try {
      X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
      KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
      Key publicKey = keyFactory.generatePublic(x509KeySpec);
      Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
      cipher.init(1, publicKey);
      return Base64.getEncoder().encodeToString(rsaSplitCodec(cipher, 1, data.getBytes(Charset.defaultCharset())));
    } catch (Exception e) {
      LOGGER.error("公钥加密: 字符串 {} 异常", data, e);
    }
    return null;
  }

  /**
   * 公钥解密
   *
   * @param data 数据
   * @param key  公钥
   * @return String
   */
  public static String buildRsaDecryptByPublicKey(String data, String key) {
    try {
      X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
      KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
      Key publicKey = keyFactory.generatePublic(x509KeySpec);
      Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
      cipher.init(2, publicKey);
      return new String(rsaSplitCodec(cipher, 2, Base64.getDecoder().decode(data)), Charset.defaultCharset());
    } catch (Exception e) {
      LOGGER.error("公钥解密: 字符串 {} 异常", data, e);
    }
    return null;
  }

  /**
   * 私钥加密
   *
   * @param data 数据
   * @param key  私钥
   * @return String
   */
  public static String buildRsaEncryptByPrivateKey(String data, String key) {
    try {
      PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key));
      KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
      Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
      Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
      cipher.init(1, privateKey);
      return Base64.getEncoder().encodeToString(rsaSplitCodec(cipher, 1, data.getBytes(Charset.defaultCharset())));
    } catch (Exception e) {
      LOGGER.error("私钥解密: 字符串 {} 异常", data, e);
    }
    return null;
  }

  /**
   * 私钥解密
   *
   * @param data 数据
   * @param key  私钥
   * @return String
   */
  public static String buildRsaDecryptByPrivateKey(String data, String key) {
    try {
      PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key));
      KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
      Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
      Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
      cipher.init(2, privateKey);
      return new String(rsaSplitCodec(cipher, 2, Base64.getDecoder().decode(data)), Charset.defaultCharset());
    } catch (Exception e) {
      LOGGER.error("私钥解密: 字符串 {} 异常", data, e);
    }
    return null;
  }

  /**
   * 签名
   *
   * @param data 数据
   * @param key  私钥
   * @return String
   */
  public static String buildRsaSignByPrivateKey(String data, String key) {
    try {
      PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key));
      KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
      PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
      Signature signature = Signature.getInstance(ALGORITHM_RSA_SIGN);
      signature.initSign(privateKey);
      signature.update(data.getBytes(Charset.defaultCharset()));
      return Base64.getEncoder().encodeToString(signature.sign());
    } catch (Exception e) {
      LOGGER.error("签名: 字符串 {} 异常", data, e);
    }
    return null;
  }

  /**
   * 验证签名
   *
   * @param data 数据
   * @param key  私钥
   * @return String
   */
  public static boolean buildRsaVerifyByPublicKey(String data, String key, String sign) {
    try {
      X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
      KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
      PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
      Signature signature = Signature.getInstance(ALGORITHM_RSA_SIGN);
      signature.initVerify(publicKey);
      signature.update(data.getBytes(Charset.defaultCharset()));
      return signature.verify(Base64.getDecoder().decode(sign));
    } catch (Exception e) {
      LOGGER.error("验证签名: 字符串 {} 异常", data, e);
    }
    return false;
  }

  private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] data) {
    int maxBlock;
    if (opmode == MODE) {
      maxBlock = 256;
    } else {
      maxBlock = 245;
    }
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      int offSet = 0;
      int i = 0;
      while (data.length > offSet) {
        byte[] buff;
        if (data.length - offSet > maxBlock) {
          buff = cipher.doFinal(data, offSet, maxBlock);
        } else {
          buff = cipher.doFinal(data, offSet, data.length - offSet);
        }
        out.write(buff, 0, buff.length);
        i++;
        offSet = i * maxBlock;
      }
      return out.toByteArray();
    } catch (Exception e) {
      LOGGER.error("加解密阈值为 {} 异常", maxBlock, e);
    }
    return new byte[0];
  }

  /**
   * 公钥加密
   *
   * @param data 字符串
   * @return String
   */
  public static String encrypt(String data) {
    return buildRsaEncryptByPublicKey(data, PUBLIC_KEY);
  }

  /**
   * 私钥解密
   *
   * @param encodedData 字符串
   * @return String
   */
  public static String decrypt(String encodedData) {
    return buildRsaDecryptByPrivateKey(encodedData, PRIVATE_KEY);
  }

}
