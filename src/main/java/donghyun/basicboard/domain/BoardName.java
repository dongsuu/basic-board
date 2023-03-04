package donghyun.basicboard.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum BoardName {
    FREE("FREE"), STUDY("STUDY"), SPORTS("SPORTS");

    @JsonValue
    private final String boardName;
}
