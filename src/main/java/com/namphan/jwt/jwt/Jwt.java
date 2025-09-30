package com.namphan.jwt.jwt;

import com.namphan.jwt.exception.AppException;
import com.namphan.jwt.exception.ErrorCode;
import com.namphan.jwt.payload.entity.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@Component
@Getter // Dùng @Getter để tạo ra các getter
public class Jwt {

    // Spring sẽ inject giá trị từ application.properties
    @Value("${app.jwt-secret}")
    private String secretKey;

    @Value("${app.jwt-access-expiration-milliseconds}")
    private long accessExpiration; // Bạn nên tạo thêm các trường cho các thuộc tính khác

    @Value("${app.jwt-refresh-expiration-milliseconds}")
    private long refreshExpiration;


    public TokenPair generateTokens(User user) {
        // Generate Access Token
        TokenInfo accessToken = generateToken(user, accessExpiration);

        // Generate Refresh Token
        TokenInfo refreshToken = generateRefreshToken(user);

        return new TokenPair(accessToken, refreshToken);
    }




    public  TokenInfo generateAccessToken(User user) {

        return generateToken(user, accessExpiration);
    }

    // Function to generate both Access and Refresh tokens
    private TokenInfo generateToken(User user, long expirationMillis) {
        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

            Date issueTime = new Date();
            Date expiryTime = new Date(issueTime.getTime() + expirationMillis);

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer("namphan.com")
                    .issueTime(issueTime)
                    .expirationTime(expiryTime)
                    .jwtID(UUID.randomUUID().toString())
                    .claim("roleName", user.getRole().getRoleName())  // Store only essential information
                    .build();

            SignedJWT signedJWT = new SignedJWT(header, claimsSet);
            signedJWT.sign(new MACSigner(secretKey.getBytes()));

            return new TokenInfo(signedJWT.serialize(), expiryTime);
        } catch (JOSEException e) {
            throw new RuntimeException("Error creating JWT token", e);
        }
    }

    // New function to generate Refresh Token
    private TokenInfo generateRefreshToken(User user) {
        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

            Date issueTime = new Date();
            Date expiryTime = new Date(issueTime.getTime() + refreshExpiration);

            // Only store userId and jwtID in the refresh token
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .issuer("tixclick.com")
                    .issueTime(issueTime)
                    .expirationTime(expiryTime)
                    .jwtID(UUID.randomUUID().toString())  // Use a shorter, unique identifier for refresh tokens
                    .claim("userId", user.getId())  // Store only the userId or reference
                    .build();

            SignedJWT signedJWT = new SignedJWT(header, claimsSet);
            signedJWT.sign(new MACSigner(secretKey.getBytes()));

            return new TokenInfo(signedJWT.serialize(), expiryTime);
        } catch (JOSEException e) {
            throw new RuntimeException("Error creating refresh JWT token", e);
        }
    }

    // Function to verify the token
    public SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(secretKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        return signedJWT;
    }

    public record TokenInfo(String token, Date expiryDate) {}

    public record TokenPair(TokenInfo accessToken, TokenInfo refreshToken) {}

    public Integer extractUserId(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token); // Parse token
            return signedJWT.getJWTClaimsSet().getIntegerClaim("userId"); // Lấy userId
        } catch (ParseException e) {
            throw new RuntimeException("Invalid token format", e);
        }
    }

}