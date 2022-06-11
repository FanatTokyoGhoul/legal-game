package ru.vsu.legal.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.legal.models.dto.requests.CompanyCreateRequest;
import ru.vsu.legal.models.entities.Company;
import ru.vsu.legal.repositories.CompanyRepository;

import java.util.List;

@Service
public class CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyService.class);
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void createCompany(CompanyCreateRequest createRequest, Long gameId) {
        log.info("Converting createRequest and gameId: {} to company", gameId);
        Company companyToSave = createRequest.toCompany(gameId);
        log.info("Saving company: {}", companyToSave.toString());
        companyRepository.save(companyToSave);
    }

    public Company saveCompany(Company company) {
        log.info("Saving company: {}", company.toString());
        return companyRepository.save(company);
    }

    public void updateCapital(Float capital, Long id) {
        companyRepository.updateCapital(capital, id);
    }

    public Company getCompany(Long id) {
        return companyRepository.findById(id).get();
    }
}
