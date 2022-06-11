package ru.vsu.legal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import ru.vsu.legal.models.entities.Insurance;

@Repository
public interface InsuranceRepository extends CrudRepository<Insurance, Long> {
}
