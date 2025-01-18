package login.com.demo.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDTO {
    String message;
    boolean result;
    String status;
    public MessageDTO(String message, String status) {
        this.message = message;
        this.status = status;
    }
}
