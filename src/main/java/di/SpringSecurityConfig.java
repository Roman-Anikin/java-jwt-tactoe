package di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import security.AuthFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private static final String[] PATTERNS = {"/auth/reg", "/auth/login", "/auth/access"};
    private final AuthFilter filter;

    public SpringSecurityConfig(AuthFilter filter) {
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(request -> request
                        .requestMatchers(PATTERNS).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(filter, AnonymousAuthenticationFilter.class)
                .exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, authException) ->
                        response.setStatus(HttpStatus.UNAUTHORIZED.value())));
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
