package login.com.demo.utils;

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

    public OTP(int otpLength) {
        this.otpLength = otpLength;
        generateOTP(otpLength);
    }

    private void generateOTP(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length should be greater than 0");
        }

        StringBuilder otp = new StringBuilder();
        Random random = new Random();


        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }

        this.otp = otp.toString();
    }
}
