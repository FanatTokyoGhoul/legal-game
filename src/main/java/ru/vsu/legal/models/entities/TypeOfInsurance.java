package ru.vsu.legal.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import ru.vsu.legal.models.enums.TypeOfInsuranceEnum;

@Getter
@Setter
@NoArgsConstructor
@Table("type_of_insurance")
public class TypeOfInsurance {

    @Id
    private Long id;
    private TypeOfInsuranceEnum type;
    private Float baseDemand;
    private Long gameKey;

    public TypeOfInsurance(TypeOfInsuranceEnum type, Float baseDemand, Long gameKey) {
        this.type = type;
        this.baseDemand = baseDemand;
        this.gameKey = gameKey;
    }
}
