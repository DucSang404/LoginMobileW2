package login.com.demo.utils;

import org.springframework.stereotype.Component;

import java.util.Random;
public class OTP {

    private String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    private int otpLength;

    public OTP() {
        generateOTP();
    }

    private void generateOTP() {
        StringBuilder otp = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            otp.append(random.nextInt(10));
        }

        this.otp = otp.toString();
    }
}
