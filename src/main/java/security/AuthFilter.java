package security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import security.model.JwtAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    public AuthFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = retrieveToken(servletRequest);
        if (token != null) {
            try {
                Claims claims = jwtProvider.getAccessClaims(token);
                JwtAuthentication jwtAuth = JwtUtil.createByClaims(claims);
                jwtAuth.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(jwtAuth);
            } catch (JwtException e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
                return;
            }
        }
        chain.doFilter(servletRequest, servletResponse);
    }

    private String retrieveToken(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }
}
