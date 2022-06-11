package ru.vsu.legal.repositories;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.vsu.legal.models.entities.Company;

public interface CompanyRepository extends CrudRepository<Company, Long> {
    @Modifying
    @Query("""
            UPDATE legal_game.company SET capital = :capital WHERE id = :id_company;
            """)
    void updateCapital(@Param("capital") Float capital, @Param("id_company")Long id_company);
}
