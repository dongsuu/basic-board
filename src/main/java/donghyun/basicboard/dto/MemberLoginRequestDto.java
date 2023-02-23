package donghyun.basicboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberLoginRequestDto {
    private String memberId;
    private String memberPassword;
}
