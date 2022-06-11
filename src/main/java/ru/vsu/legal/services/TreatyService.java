package ru.vsu.legal.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.legal.models.dto.requests.TreatyRequest;
import ru.vsu.legal.models.entities.Insurance;
import ru.vsu.legal.models.entities.Treaty;
import ru.vsu.legal.models.entities.TypeOfInsurance;
import ru.vsu.legal.repositories.TreatyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TreatyService {

    private final TreatyRepository treatyRepository;

    private final InsuranceService insuranceService;

    private final Logger log = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    public TreatyService(TreatyRepository treatyRepository, InsuranceService insuranceService) {
        this.treatyRepository = treatyRepository;
        this.insuranceService = insuranceService;
    }

    public void deleteAll(List<Treaty> treaties){
        log.info("Deleting treaties: {}", treaties);
        treatyRepository.deleteAll(treaties);
    }

    public List<Treaty> createAll(List<TreatyRequest> treaties,  Long gameId, Long companyId){
        log.info("Creating treaties by gameId: {} and companyId: {}", gameId, companyId);
        List<Treaty> savedTreaties = new ArrayList<>();
        for (TreatyRequest request : treaties){
            Treaty treaty = treatyRepository.save(request.toTreaty(companyId));
            List<Insurance> insurances = insuranceService.createInsurance(request.getTypeOfInsuranceToBaseRate(), treaty.getId(), gameId);
            treaty.setInsuranceList(insurances);
            savedTreaties.add(treaty);
        }
        return savedTreaties;
    }
}
