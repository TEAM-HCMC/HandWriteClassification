package ac.kr.inu.security.ajax.provider;

import ac.kr.inu.exception.BadPasswordException;
import ac.kr.inu.security.context.AccountContext;
import ac.kr.inu.security.context.AccountContextService;
import ac.kr.inu.security.token.PostAuthorizationToken;
import ac.kr.inu.security.token.PreAuthorizationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AjaxLoginAuthenticationProvider implements AuthenticationProvider {

    private final AccountContextService accountContextService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        PreAuthorizationToken token = (PreAuthorizationToken) authentication;
        String email = token.getEmail();
        String password = token.getPassword();

        UserDetails userDetails = accountContextService.loadUserByUsername(email);

        if (isCorrectPassword(password, userDetails)) {
            return PostAuthorizationToken.getTokenFromAccountContext((AccountContext) userDetails);
        }

        throw new BadPasswordException();
    }

    /**
     * 이 Provider가 어떠한 인증객체를 서포트할지 명시적으로 밝혀준다.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private boolean isCorrectPassword(String password, UserDetails userDetails) {
        return passwordEncoder.matches(password, userDetails.getPassword());
    }
}