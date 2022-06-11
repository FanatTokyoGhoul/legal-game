package ru.vsu.legal.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.legal.models.entities.Treaty;

public interface TreatyRepository extends CrudRepository<Treaty, Long> {
}
