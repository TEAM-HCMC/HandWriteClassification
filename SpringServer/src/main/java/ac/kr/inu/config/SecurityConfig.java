package ac.kr.inu.config;

import ac.kr.inu.security.ajax.filter.AjaxLoginFilter;
import ac.kr.inu.security.ajax.handler.AjaxLoginFailureHandler;
import ac.kr.inu.security.ajax.handler.AjaxLoginSuccessHandler;
import ac.kr.inu.security.ajax.provider.AjaxLoginAuthenticationProvider;
import ac.kr.inu.security.jwt.filter.JwtAuthenticationFilter;
import ac.kr.inu.security.jwt.handler.JwtFailureHandler;
import ac.kr.inu.security.jwt.provider.JwtAuthenticationProvider;
import ac.kr.inu.security.matcher.FilterSkipMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String SIGNUP_END_POINT = "/api/account/signup";
    private static final String LOGIN_END_POINT = "/api/account/login";
    private static final String NEED_JWT_POINT = "/api/**";

    private final AjaxLoginAuthenticationProvider ajaxLoginProvider;
    private final JwtFailureHandler jwtFailureHandler;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final AjaxLoginSuccessHandler ajaxLoginSuccessHandler;
    private final AjaxLoginFailureHandler ajaxLoginFailureHandler;
    private final ObjectMapper objectMapper;

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(this.ajaxLoginProvider)
                .authenticationProvider(this.jwtAuthenticationProvider);
    }

    protected AjaxLoginFilter ajaxLoginFilter() throws Exception {
        AjaxLoginFilter filter = new AjaxLoginFilter(LOGIN_END_POINT, ajaxLoginSuccessHandler, ajaxLoginFailureHandler, objectMapper);
        filter.setAuthenticationManager(getAuthenticationManager());
        filter.setAuthenticationSuccessHandler(ajaxLoginSuccessHandler);
        filter.setAuthenticationFailureHandler(ajaxLoginFailureHandler);
        return filter;
    }

    protected JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        FilterSkipMatcher skipMatcher = getSkipMatcher();
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(skipMatcher, jwtFailureHandler);
        filter.setAuthenticationManager(getAuthenticationManager());
        filter.setAuthenticationFailureHandler(jwtFailureHandler);
        return filter;
    }

    private FilterSkipMatcher getSkipMatcher() {
        return new FilterSkipMatcher(Arrays.asList(LOGIN_END_POINT, SIGNUP_END_POINT), NEED_JWT_POINT);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                cors();

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.
                authorizeRequests()
                .antMatchers("/h2/*")
                .permitAll()
                .and()
                .csrf().disable()
                .headers()
                .frameOptions()
                .disable();

        http
                .addFilterBefore(ajaxLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

}
