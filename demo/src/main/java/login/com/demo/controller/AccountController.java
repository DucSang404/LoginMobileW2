package login.com.demo.controller;

import login.com.demo.dto.AccountDTO;
import login.com.demo.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    AccountService accountService;

    @GetMapping
    public List<AccountDTO> getAccount() {
        for(AccountDTO dto : accountService.findAll()) {
            System.out.println(dto.getUsername());
        }
        return accountService.findAll();
    }
}
