package com.creve.controller;

import com.creve.model.Company;
import com.creve.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    @Autowired private CompanyService companyService;

    @GetMapping("/search")
    public List<Company> search(@RequestParam String name) {
        return companyService.searchCompanies(name);
    }
}