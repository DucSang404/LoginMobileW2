package login.com.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import login.com.demo.constant.MailConfirmOTPConstant;
import login.com.demo.constant.OTPConstant;
import login.com.demo.dto.AccountDTO;
import login.com.demo.dto.MessageDTO;
import login.com.demo.service.AccountService;
import login.com.demo.utils.MailUtils;
import login.com.demo.utils.OTP;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/acount")
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

    @PostMapping(("/login"))
    public ResponseEntity<AccountDTO> Login(@RequestBody AccountDTO accountDTO) throws AccountNotFoundException {
        if (accountDTO == null || accountDTO.getUsername() == null || accountDTO.getPassword() == null)
            return ResponseEntity.badRequest().body(null); // Trả về mã 400 nếu dữ liệu không hợp lệ

        accountDTO = accountService.findByUsernameAndPassword(accountDTO.getUsername(), accountDTO.getPassword());

        if (accountDTO == null) throw new AccountNotFoundException("Account not found for username: " + accountDTO.getUsername());

        return ResponseEntity.ok(accountDTO);
    }


    @PostMapping(("/register"))
    public ResponseEntity<MessageDTO> register (@RequestBody String email, HttpServletRequest request) throws Exception {
        if (email.isEmpty() || email == null)
            return ResponseEntity.badRequest().body(null); // Trả về mã 400 nếu dữ liệu không hợp lệ

        // đưa otp vào session
        OTP codeOTP = new OTP(6);
        request.getSession().setAttribute(OTPConstant.OTP, codeOTP);

        var check = request.getSession().getAttribute(OTPConstant.OTP);

        MailUtils mailUtils = new MailUtils();
        if (mailUtils.sendEmail("phama9162@gmail.com", email , MailConfirmOTPConstant.SUBJECT, MailConfirmOTPConstant.CONTENT)) {
            return ResponseEntity.ok(new MessageDTO("Gửi OTP thành công !","success"));
        } else  throw new Exception("Chưa thể gửi OTP");
    }


    @PostMapping(("/confirm-otp"))
    public ResponseEntity<MessageDTO> ConfirmOTP (@RequestBody String code, HttpServletRequest request) throws Exception {
        if (code.isEmpty() || code == null)
            return ResponseEntity.badRequest().body(null); // Trả về mã 400 nếu dữ liệu không hợp lệ

        // đưa otp vào session
        var codeOTP = request.getSession().getAttribute(OTPConstant.OTP);

        if (code.equals("codeOTP.getOtp()")) {
            return ResponseEntity.ok(new MessageDTO("Tạo tài khoản thành công !","success",true));
        } else
            return ResponseEntity.ok(new MessageDTO("OTP không đúng!","failure",false));
    }
}
