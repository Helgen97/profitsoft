package dev.profitsoft.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final long EXPIRATION_TIME = 86400000;
    private static final String PREFIX = "Bearer";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public String getToken(String login) {

        return Jwts.builder()
                .setSubject(login)
                .setExpiration(new Date(System.currentTimeMillis()
                        + EXPIRATION_TIME))
                .signWith(generateKeyForSigning())
                .compact();
    }

    private Key generateKeyForSigning() {
        byte[] apiKeySecretBytes = JWT_SECRET.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(apiKeySecretBytes, SIGNATURE_ALGORITHM.getJcaName());
    }

    public String getAuthUser(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(generateKeyForSigning())
                .build()
                .parseClaimsJws(token.replace(PREFIX, ""))
                .getBody()
                .getSubject();
    }
}