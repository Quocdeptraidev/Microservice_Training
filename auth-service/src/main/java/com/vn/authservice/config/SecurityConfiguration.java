package com.vn.authservice.config;

import com.vn.authservice.service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity // Kích hoạt bảo mật Web
@EnableMethodSecurity // Kích hoạt bảo mật theo phương thức
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;
    private final AccountService accountService;


    public SecurityConfiguration(@Lazy JwtFilter jwtFilter,
                                 @Lazy AccountService accountService
    ) {
        this.jwtFilter = jwtFilter;
        this.accountService = accountService;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // Tắt CSRF để đơn giản hóa API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(APIURL.PUBLIC_URLS).permitAll() // Cho phép truy cập không cần xác thực
                        .requestMatchers(APIURL.ADMIN_URLS).hasRole("ADMIN") // Chỉ ADMIN mới được truy cập
                        .requestMatchers(APIURL.USER_URLS).hasRole("USER") // Chỉ USER mới được truy cập
                        .anyRequest().authenticated() // Các request còn lại yêu cầu xác thực
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // Cấu hình session
                .authenticationProvider(authenticationProvider()) // Đăng ký provider xác thực
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Thêm JWT filter trước authentication filter
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(accountService); // Sử dụng service quản lý user
        authenticationProvider.setPasswordEncoder(passwordEncoder()); // Sử dụng mã hóa mật khẩu
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Mã hóa mật khẩu bằng BCrypt
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); // Quản lý xác thực
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000"); // Chỉ cho phép frontend truy cập
        corsConfiguration.addAllowedHeader("*"); // Cho phép tất cả headers
        corsConfiguration.addAllowedMethod("*"); // Cho phép tất cả phương thức HTTP
        corsConfiguration.setAllowCredentials(true); // Hỗ trợ gửi credentials

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }

    @Bean
    public OidcUserService oidcUserService1() {
        return new OidcUserService(); // Xử lý thông tin người dùng OIDC
    }

    @Bean
    public DefaultOAuth2UserService oAuth2UserService1() {
        return new DefaultOAuth2UserService(); // Xử lý thông tin người dùng OAuth2
    }
}
