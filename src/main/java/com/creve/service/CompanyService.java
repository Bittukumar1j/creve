package com.creve.service;

import com.creve.model.Company;
import com.creve.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CompanyService {
    @Autowired private CompanyRepository companyRepository;

    public List<Company> searchCompanies(String name) {
        return companyRepository.findByNameContainingIgnoreCase(name);
    }
}