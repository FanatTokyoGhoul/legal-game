package ru.vsu.legal.models.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.vsu.legal.models.entities.Insurance;
import ru.vsu.legal.models.entities.Treaty;
import ru.vsu.legal.models.enums.TypeOfInsuranceEnum;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class TreatyRequest {
    private Integer size;
    private Float installmentFrequency;
    private Integer validity;
    private Float maxPayment;
    private  Float sizeHitForMinPayment;

    private Map<TypeOfInsuranceEnum, Float> typeOfInsuranceToBaseRate;

    public Treaty toTreaty(Long companyId){
        return new Treaty(size, installmentFrequency, validity, maxPayment, sizeHitForMinPayment, companyId);
    }
}
