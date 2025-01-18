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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    AccountService accountService;
    Map<String, String> otpStorage = new ConcurrentHashMap<>();
    MailUtils mailUtils;

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
        if (email.isEmpty())
            return ResponseEntity.badRequest().body(null); // Trả về mã 400 nếu dữ liệu không hợp lệ

        // đưa otp vào session
        OTP codeOTP = new OTP();
        request.getSession().setAttribute(OTPConstant.OTP, codeOTP);


        MailUtils mailUtils = new MailUtils();
        if (mailUtils.sendEmail("phama9162@gmail.com", email , MailConfirmOTPConstant.SUBJECT, MailConfirmOTPConstant.CONTENT + codeOTP.getOtp())) {
            return ResponseEntity.ok(new MessageDTO(MailConfirmOTPConstant.REPONSE,"success"));
        } else  throw new Exception("Chưa thể gửi OTP");
    }


    @PostMapping(("/confirm-otp"))
    public ResponseEntity<MessageDTO> ConfirmOTP (@RequestBody String code, HttpServletRequest request) throws Exception {
        if (code.isEmpty() || code == null)
            return ResponseEntity.badRequest().body(null); // Trả về mã 400 nếu dữ liệu không hợp lệ

        // đưa otp vào session
        OTP codeOTP = (OTP) request.getSession().getAttribute(OTPConstant.OTP);

        if (code.equals(codeOTP.getOtp())) {
            return ResponseEntity.ok(new MessageDTO("Tạo tài khoản thành công !",true, "success"));
        } else
            return ResponseEntity.ok(new MessageDTO("OTP không đúng!",false, "failure"));
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOTP(@RequestParam String email, HttpServletRequest request) {
        OTP otp = new OTP();
        otpStorage.put(email, otp.getOtp());

        String subject = "Your OTP";
        String body = "Hi,\n\nYour OTP is: " + otp.getOtp() + "\nPlease use this code to authenticate.";

        boolean isSent = mailUtils.sendEmail("phama9162@gmail.com", email, subject, body);
        if (isSent) {
            return ResponseEntity.ok("OTP has send to " + email);
        } else {
            return ResponseEntity.status(500).body("Could not send OTP. Pls try again");
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestParam String otp,
                                            @RequestParam String email) {
        if (otpStorage.containsKey(email) && otpStorage.get(email).equals(otp)) {
            otpStorage.remove(email);
            return ResponseEntity.ok("Verify success");
        }
        else {
            return ResponseEntity.status(400).body("Invalid OTP or OTP expired");
        }
    }
}
