package ru.vsu.legal.models.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("game_action")
public class GameActions {

    @Id
    private Long id;
    private Integer month;
    private String log;

    private Long gameKey;

    public GameActions(Integer month) {
        this.month = month;
    }
}
