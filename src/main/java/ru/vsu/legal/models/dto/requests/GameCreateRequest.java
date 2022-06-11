package ru.vsu.legal.models.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.vsu.legal.models.entities.Game;
import ru.vsu.legal.models.enums.TypeOfInsuranceEnum;

import java.util.Map;

@AllArgsConstructor
@Getter
public class GameCreateRequest {
    private Integer taxPercent;
    private String gameName;
    private CompanyCreateRequest companyCreateRequest;
    private Map<TypeOfInsuranceEnum, Float> typeOfInsurancesCreateRequest;

    public Game toGame() {
        return new Game(taxPercent, gameName);
    }
}
