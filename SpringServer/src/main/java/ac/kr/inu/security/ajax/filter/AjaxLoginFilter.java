package ac.kr.inu.security.ajax.filter;

import ac.kr.inu.dto.account.AccountLoginReqDto;
import ac.kr.inu.security.token.PreAuthorizationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AjaxLoginFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;
    private ObjectMapper objectMapper;

    public AjaxLoginFilter(String defaultFilterProcessesUrl, AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler, ObjectMapper objectMapper) {
        super(defaultFilterProcessesUrl);
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
        if (isJson(request)) {
            AccountLoginReqDto loginReqDto = objectMapper.readValue(request.getReader(), AccountLoginReqDto.class);
            PreAuthorizationToken preAuthorizationToken = loginReqDto.toPreAuthorizationToken();

            log.info("[request start] -> {}", request.getRequestURI());

            return super.getAuthenticationManager().authenticate(preAuthorizationToken);
        }
        throw new IllegalArgumentException("Wrong Content Type.");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        this.authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
    }

    private boolean isJson(HttpServletRequest request) {
        return MediaType.APPLICATION_JSON.toString().equals(request.getContentType());
    }
}
