package zerobase.storereservationapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import zerobase.storereservationapi.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter authenticationFilter;

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                //csrf disable
                .csrf(AbstractHttpConfigurer::disable)
                //From 로그인 방식 disable
                .formLogin(AbstractHttpConfigurer::disable)
                //http basic 인증 방식 disable
                .httpBasic(AbstractHttpConfigurer::disable)
                //경로별 인가 작업
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/signup/**", "/login").permitAll()
                        .anyRequest().authenticated())
                //JWTFilter 등록
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //세션 설정
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
