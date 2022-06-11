package ru.vsu.legal.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.legal.models.entities.GameActions;

public interface GameActionsRepository extends CrudRepository<GameActions, Long> {
}
