package ru.vsu.legal.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.legal.models.entities.Game;

import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, Long> {
    Optional<Game> findGameByName(String name);
}
