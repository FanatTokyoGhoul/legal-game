package ru.vsu.legal.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.legal.exceptions.NotFoundException;
import ru.vsu.legal.models.entities.Insurance;
import ru.vsu.legal.models.entities.TypeOfInsurance;
import ru.vsu.legal.models.enums.TypeOfInsuranceEnum;
import ru.vsu.legal.repositories.InsuranceRepository;
import ru.vsu.legal.repositories.TypeOfInsuranceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class InsuranceService {

    private static final Float BASE_DEMAND = 1f;

    private final Logger log = LoggerFactory.getLogger(CompanyService.class);

    private final TypeOfInsuranceRepository typeOfInsuranceRepository;

    private final InsuranceRepository insuranceRepository;

    @Autowired
    public InsuranceService(TypeOfInsuranceRepository typeOfInsuranceRepository, InsuranceRepository insuranceRepository) {
        this.typeOfInsuranceRepository = typeOfInsuranceRepository;
        this.insuranceRepository = insuranceRepository;
    }

    public void createTypes(Map<TypeOfInsuranceEnum, Float> typeOfInsurancesCreateRequest, Long gameId) {
        log.info("Starting create types for game with id: {}", gameId);
        TypeOfInsurance createType;
        List<TypeOfInsurance> listTypesToCreate = new ArrayList<>();
        for(TypeOfInsuranceEnum typeEnum: TypeOfInsuranceEnum.values()) {
            if(typeOfInsurancesCreateRequest.containsKey(typeEnum)){
                log.info("Creating type: {} with custom setting", typeEnum.name());
                createType = new TypeOfInsurance(typeEnum, typeOfInsurancesCreateRequest.get(typeEnum), gameId);
                listTypesToCreate.add(createType);
            }else {
                log.info("Creating type: {} with base setting", typeEnum.name());
                createType = new TypeOfInsurance(typeEnum, BASE_DEMAND, gameId);
                listTypesToCreate.add(createType);
            }
        }

        typeOfInsuranceRepository.saveAll(listTypesToCreate);
    }

    public TypeOfInsurance getTypeOfInsurance(Long id){
        log.info("Getting type of insurance by id: {}", id);
        return typeOfInsuranceRepository.findById(id).orElseThrow(() -> new NotFoundException("TypeOfInsurance with id: " + id + "not found"));
    }

    public List<Insurance> createInsurance(Map<TypeOfInsuranceEnum, Float> map, Long treatyId, Long gameId){
        log.info("Getting typeOfInsurances by gameId: {}", gameId);
        List<TypeOfInsurance> typeOfInsurances = typeOfInsuranceRepository.findAllByGameKey(gameId);
        log.info("Crating insurances");
        List<Insurance> insurances = typeOfInsurances
                .stream().map(typeOfInsurance -> new Insurance(typeOfInsurance.getId(), map.get(typeOfInsurance.getType()), treatyId)).toList();
        List<Insurance> savedList = new ArrayList<>();
        for(Insurance insurance: insuranceRepository.saveAll(insurances)){
            savedList.add(insurance);
        }
        return savedList;
    }
}
