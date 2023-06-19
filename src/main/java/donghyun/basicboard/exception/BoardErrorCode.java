package donghyun.basicboard.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardErrorCode {
    // Common

    // JWT
    REFRESH_TOKEN_NOT_FOUND(400, "존재하지 않는 리프레시 토큰 입니다.", "J001"),
    TOKEN_AUTHENTICATION_DENIED(401, "권한 정보가 없는 토큰 입니다.", "J002"),

    // Member
    MEMBER_NOT_FOUND(400, "존재하지 않는 회원 입니다.", "M001"),

    // Post
    POST_NOT_FOUND(400, "존재하지 않는 게시글 입니다.", "P001"),

    ;


    private final int status;
    private final String message;
    private final String code;
}
