package login.com.demo.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDTO {
    Long id;
    String username;
    String password;


    public AccountDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
