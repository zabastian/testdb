package com.example.test.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.test.common.user.entity.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
public class TokenService {
    private static final String BEARER_PREFIX = "Bearer ";
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret}")
    private String secret;

    public TokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

//    @Value("${jwt.secret}")
//    private String secret;

    @Transactional
    public String createAccessToken(Long userId, UserRole role) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Date expiresAt = Date.from(Instant.now().plus(15, ChronoUnit.MINUTES));
            //
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withSubject(Long.toString(userId))
                    .withClaim("role", role.name())
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            // Invalid Signing configuration / Couldn't convert Claims.
            throw new RuntimeException("생성실패", exception);
        }
    }

    public String createRefreshToken() {
        return UUID.randomUUID().toString();
    }

    @Transactional //자동 try-catch + rollback + commit
    public TokenResponse refreshTokens(String oldRefreshToken) {
        RefreshToken tokenEntity = refreshTokenRepository.findByToken(oldRefreshToken)
                .orElseThrow(() -> new RuntimeException("LOGIN_FAILED"));
        if(tokenEntity.isExpired()) {
            refreshTokenRepository.delete(tokenEntity);
            throw new RuntimeException("TOKEN_IS_EXPIRED");
        }

        String newAccessToken = createAccessToken(tokenEntity.getUserId(), tokenEntity.getUserRole());
            String newRefreshToken = createRefreshToken();

        RefreshToken newTokenEntity = new RefreshToken(newRefreshToken, tokenEntity.getUserId(), tokenEntity.getUserRole(), Instant.now().plus(30, ChronoUnit.DAYS));

        refreshTokenRepository.delete(tokenEntity);
        refreshTokenRepository.save(newTokenEntity);


//        tokenEntity.setToken(newRefreshToken);
//        tokenEntity.setExpiryDate(Instant.now().plus(30,ChronoUnit.DAYS));
//        refreshTokenRepository.save(tokenEntity);

        return new TokenResponse(newAccessToken,newRefreshToken);
    }

    /*
        public long verifyToken(String bearerToken) {
    */
    public DecodedJWT verifyToken(String bearerToken) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = bearerToken.substring((BEARER_PREFIX.length()));
            JWTVerifier verifier = JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer("auth0")
                    // reusable verifier instance
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            /* String userId = decodedJWT.getSubject();

            return Long.parseLong((userId));*/
            return verifier.verify(token);

        } catch (JWTVerificationException exception) {
            throw new RuntimeException("검증실패", exception);
            // Invalid signature/claims
        }
    }
}

// tokenService에서 createToken을 통해 token생성 (이때, filter단에서 Baerer와 null값은 filter 통과하는 로직 칠요)
// 그 후 로그인 서비스단에서 token 및 refreshtoken 만료시간 로직 설정
// verifyToken통해 DecodedJWT 객체에 인증된 토큰값 저장 후 filter단에서 이 검증된 속성들을 가지고와 SecurityContext 로직으로 검증함
// 이때, filter 단에서 GrantedAuthority를 통해 Role_을 붙여 hasRole()에서 인식하도록 한다.( 즉, 이 JetAuthenticationFilter 자체가 Bearer토큰 검증도구인 동시에 SecurityContext에 사용될 사용자 인증 정보를 저장하는것이다.)
//  그 후 이 SecurityContext는 SecurityConfig의 사용자 인증 정보를 활용한다.



