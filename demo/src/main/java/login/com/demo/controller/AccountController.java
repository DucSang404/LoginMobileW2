package login.com.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import login.com.demo.constant.MailConfirmOTPConstant;
import login.com.demo.constant.OTPConstant;
import login.com.demo.dto.AccountDTO;
import login.com.demo.dto.MessageDTO;
import login.com.demo.dto.request.RegisterDTO;
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

    @PostMapping("/check-user")
    public ResponseEntity<MessageDTO> Register (@RequestBody String email) throws Exception {

        return (accountService.existsByUsername(email)) ? ResponseEntity.ok(new MessageDTO("no user",true,"success"))
                : ResponseEntity.ok(new MessageDTO("already has users",false,"success"));

    }


    @PostMapping(("/register"))
    public ResponseEntity<MessageDTO> Register (@RequestBody RegisterDTO registerDTO, HttpServletRequest request) throws Exception {

        AccountDTO accountDTO = new AccountDTO(registerDTO.getUsername(),registerDTO.getPassword());
        String otp = registerDTO.getOtpCode();

        if (otpStorage.containsKey(accountDTO.getUsername()) && otpStorage.get(accountDTO.getUsername()).equals(otp)) {
            otpStorage.remove(accountDTO.getUsername());

            //register
            return ResponseEntity.ok(accountService.register(accountDTO));
        }
        else {
            return ResponseEntity.ok(new MessageDTO("mã OTP không hợp lệ",false,"fail"));
        }
    }

    private boolean sendOTP (OTP otp,String email) {
        String subject = "Your OTP";
        String body = "Hi,\n\nYour OTP is: " + otp.getOtp() + "\nPlease use this code to authenticate.";

        return mailUtils.sendEmail("phama9162@gmail.com", email, subject, body);
    }

    @PostMapping("/send-otp-for-register")
    public ResponseEntity<MessageDTO> sendOTPForRegister(@RequestParam String email, HttpServletRequest request) {
        if (accountService.existsByUsername(email))
            return ResponseEntity.ok(new MessageDTO("User đã tồn tại !",false,"success"));

        OTP otp = new OTP();
        otpStorage.put(email, otp.getOtp());

        boolean isSent = sendOTP(otp,email);
        if (isSent) {
            return ResponseEntity.ok(new MessageDTO("OTP has send to " + email,true,"success"));
        } else {
            return ResponseEntity.ok(new MessageDTO("Could not send OTP. Pls try again",false,"fail"));
        }
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOTP(@RequestParam String email, HttpServletRequest request) {
        OTP otp = new OTP();
        otpStorage.put(email, otp.getOtp());

        boolean isSent = sendOTP(otp,email);
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
