package ru.vsu.legal.models.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.vsu.legal.models.entities.Company;

@AllArgsConstructor
@Getter
public class CompanyCreateRequest {
    private String name;
    private Float capital;

    public Company toCompany(Long gameId) {
        return new Company(name, capital, gameId);
    }
}
