package ac.kr.inu.controller;


import ac.kr.inu.dto.account.AccountInfoResDto;
import ac.kr.inu.dto.account.AccountLoginReqDto;
import ac.kr.inu.dto.account.AccountSaveReqDto;
import ac.kr.inu.service.AccountService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signUp(@RequestBody AccountSaveReqDto accountSaveReq) {
        return ResponseEntity.ok(accountService.saveAccount(accountSaveReq));
    }

    @PostMapping("/login")
    public void login(@RequestBody AccountLoginReqDto loginReqDto) {
    }

    @GetMapping("")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<AccountInfoResDto> getAccountInfo(@ApiIgnore Authentication authentication) {
        final Long id = Long.parseLong(authentication.getPrincipal().toString());
        return ResponseEntity.ok(accountService.getAccountInfo(id));
    }

}