package login.com.demo.controller;

import login.com.demo.dto.AccountDTO;
import login.com.demo.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    AccountService accountService;

    @PostMapping
    public ResponseEntity<Boolean> checkLogin(@RequestBody AccountDTO accountDTO) {
        String username = accountDTO.getUsername();
        String password = accountDTO.getPassword();

        boolean validLogin = accountService.findAccount(username, password);
        var t = ResponseEntity.ok(validLogin);
        return t;
    }
}
