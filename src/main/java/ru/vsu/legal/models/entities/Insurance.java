package ru.vsu.legal.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table("insurance")
public class Insurance {

    @Id
    private Long id;
    private Long typeOfInsuranceId;
    private Float baseRate;

    private Long treatyKey;

    public Insurance(Long typeOfInsurance, Float baseRate, Long treatyKey) {
        this.typeOfInsuranceId = typeOfInsurance;
        this.baseRate = baseRate;
        this.treatyKey = treatyKey;
    }
}
