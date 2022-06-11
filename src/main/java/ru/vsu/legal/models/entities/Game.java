package ru.vsu.legal.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table("game")
public class Game {

    @Id
    private Long id;
    private Integer taxPercent;
    private String name;

    @MappedCollection(idColumn = "game_key")
    private Company company;

    @MappedCollection(idColumn = "game_key")
    private List<GameActions> gameHistory;

    @MappedCollection(idColumn = "game_key")
    private List<TypeOfInsurance> typeOfInsurances;

    public Game(Integer taxPercent, String name) {
        this.taxPercent = taxPercent;
        this.name = name;
    }
}
