package com.sad.web.utils;

import com.sad.web.utils.uuid.UUIDUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;



public class JwtUtil {

   //设置秘钥明文
   public static final String JWT_KEY = "qhy";
   public static SecretKey generalKey() {
      byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
      SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
      return key;
   }


   public static String createJWT(String subject) {
      JwtBuilder builder = getJwtBuilder(subject, null, UUIDUtil.generateUUID());// 设置过期时间
      return builder.compact();
   }

   private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
      SecretKey secretKey = generalKey();
      return Jwts.builder()
              .setId(uuid)              //唯一的ID
              .setSubject(subject)   // 主题  可以是JSON数据
              .setIssuer("qhy")     // 签发者
              .signWith(SignatureAlgorithm.HS256, secretKey); //使用HS256对称加密算法签名, 第二个参数为秘钥

   }


   public static Claims parseJWT(String jwt) throws Exception {
      SecretKey secretKey = generalKey();
      return Jwts.parser()
              .setSigningKey(secretKey)
              .parseClaimsJws(jwt)
              .getBody();
   }



}
