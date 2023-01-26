package donghyun.basicboard.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberLoginRequestDto {
    private String memberId;
    private String memberPassword;
}
