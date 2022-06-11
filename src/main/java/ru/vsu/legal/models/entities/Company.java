package ru.vsu.legal.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table("company")
public class Company {

    @Id
    private Long id;
    private String name;
    private Float capital;

    private Long gameKey;

    @MappedCollection(idColumn = "company_key")
    private Set<Treaty> treaties;

    public Company(String name, Float capital, Long gameKey) {
        this.name = name;
        this.capital = capital;
        this.gameKey = gameKey;
    }
}
