package com.ssafy.ws.member.util;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafy.ws.member.model.dto.MemberDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	private final String secretKey; // 시크릿 키, 토큰 생성과 검증 시 필요 (환경변수 JWT_SECRET)

	public JWTUtil(@Value("${jwt.secret}") String secretKey) {
		this.secretKey = secretKey;
	}

	// JWT 토큰 생성 메소드
	public String generateToken(MemberDto member) {
		long now = System.currentTimeMillis();
		long expirationSecond = 60*60*2;	//2시간

		Claims claims = Jwts.claims();
		claims.put("userId", member.getUserId());
		claims.put("name", member.getName());

		return Jwts.builder()
				.setClaims(claims)	//정보 담기
				.setExpiration(new Date(now + 1000*expirationSecond)) // 토큰 만료 시간
				.signWith(SignatureAlgorithm.HS256, secretKey)		  // encoding 방식
				.compact();	//토큰 생성
	}

	// JWT 토큰 유효성 검증 메소드(SecretKey 필요)
	public Jws<Claims> validateToken(String token) {
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token);
	}

	// JWT 토큰 decode(SecretKey 필요X)
	public static String decodeToken(String token) {
		String[] splitToken = token.split("\\.");
		String body = splitToken[1];
		
		//디코딩
		Base64.Decoder decoder = Base64.getDecoder();
		String decodedBody = new String(decoder.decode(body));
		
		return decodedBody;
	}
}
