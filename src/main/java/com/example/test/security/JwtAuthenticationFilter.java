package com.example.test.security;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.test.token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //OncePerRequestFilter: 요청마다 딱 한 번만 실행되는 필터를 만들기 위한 추상 클래스 상속

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            //authHeader == null은 아예 비로그인으로 접속했을 시에 "토큰이 없는 요청"으로 넘겨주는게 맞다.
            // 토큰 없으면 다음 필터로 넘김
            return;
        }

        try {
            DecodedJWT decodedJWT = tokenService.verifyToken(authHeader);
            Long userId = Long.parseLong(decodedJWT.getSubject());
            String role = decodedJWT.getClaim("role").asString();

            List<GrantedAuthority> authorities = List.of( //Arrays.asList()만큼 자주 쓰인다.
                    new SimpleGrantedAuthority("ROLE_" + role)  // ROLE_ prefix 붙여야 hasRole()에서 인식함
            ); //이게 있어야 hasRole이 역할 수행할 수 있음.

            CustomUserPrincipal principle = new CustomUserPrincipal(userId, role);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(principle, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authToken);
            //인증 완료 → SecurityContext 저장
            /* SecurityContextHolder
                └──SecurityContext
                     └── Authentication ← 반드시 이걸 통해 "인증 여부", "사용자 정보", "권한"을 확인*/

           /* 만약 CustomUserPrincipal를 사용 안한다면?

            new UsernamePasswordAuthenticationToken(userId, null, authorities)

            생성자 첫 번째 인자가 principal 임.
            즉 내부적으로는 거의 이런 느낌:

            this.principal = userId;
            가 저장되는 거야.그래서 나중에:
            auth.getPrincipal() 사용 가능 */


        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, " JWT 검증 실패 " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}