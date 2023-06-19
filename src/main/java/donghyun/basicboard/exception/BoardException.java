package donghyun.basicboard.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class BoardException extends RuntimeException{
    private int status;
    private String message;
    private String code;

    public BoardException(BoardErrorCode boardErrorCode){
        this.status = boardErrorCode.getStatus();
        this.message = boardErrorCode.getMessage();
        this.code = boardErrorCode.getCode();
    }

    public BoardException(BoardErrorCode boardErrorCode, Throwable cause) {
        super(cause);
        this.status = boardErrorCode.getStatus();
        this.message = boardErrorCode.getMessage();
        this.code = boardErrorCode.getCode();
    }
}
