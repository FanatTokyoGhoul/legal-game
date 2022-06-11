package ru.vsu.legal.models.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table("treaty")
public class Treaty {

    @Id
    private Long id;
    private Integer size;
    private Float installmentFrequency;
    private Integer validity;
    private Float maxPayment;
    private  Float sizeHitForMinPayment;

    private Long companyKey;

    @MappedCollection(idColumn = "treaty_key")
    private List<Insurance> insuranceList;

    public Treaty(Integer size, Float installmentFrequency, Integer validity, Float maxPayment, Float sizeHitForMinPayment, Long companyKey) {
        this.size = size;
        this.installmentFrequency = installmentFrequency;
        this.validity = validity;
        this.maxPayment = maxPayment;
        this.sizeHitForMinPayment = sizeHitForMinPayment;
        this.companyKey = companyKey;
    }

    public void update(Integer size, Float installmentFrequency, Integer validity, Float maxPayment, Float sizeHitForMinPayment){
        this.size = size;
        this.installmentFrequency = installmentFrequency;
        this.validity = validity;
        this.maxPayment = maxPayment;
        this.sizeHitForMinPayment = sizeHitForMinPayment;
    }
}
