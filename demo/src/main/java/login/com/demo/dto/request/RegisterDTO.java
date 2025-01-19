package login.com.demo.dto.request;

public class RegisterDTO {
    private String username;
    private String password;

    public RegisterDTO(String username, String password, String otpCode) {
        this.username = username;
        this.password = password;
        this.otpCode = otpCode;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String otpCode;
}
