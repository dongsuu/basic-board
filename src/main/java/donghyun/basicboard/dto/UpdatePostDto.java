package donghyun.basicboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostDto {
    private String title;
    private String content;
    private String boardName;
    private List<String> uploadFilePaths;
}
