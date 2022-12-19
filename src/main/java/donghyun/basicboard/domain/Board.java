package donghyun.basicboard.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class Board {
    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private BoardName name;


    public Board() {
    }

    public Board(BoardName name) {
        this.name = name;
    }
}
