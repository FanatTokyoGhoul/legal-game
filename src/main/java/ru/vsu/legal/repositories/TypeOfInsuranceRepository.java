package ru.vsu.legal.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.legal.models.entities.TypeOfInsurance;

import java.util.List;

public interface TypeOfInsuranceRepository extends CrudRepository<TypeOfInsurance, Long> {
    List<TypeOfInsurance> findAllByGameKey(Long gameKey);
}
