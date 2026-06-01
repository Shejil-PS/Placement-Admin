package com.placements.service;

import com.placements.dto.request.CreateCompanyRequest;
import com.placements.dto.request.UpdateCompanyRequest;
import com.placements.dto.response.CompanyResponse;
import com.placements.model.Company;
import com.placements.repository.CompanyRepository;
import io.vertx.core.Future;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Future<List<CompanyResponse>> getAllCompanies() {
        return companyRepository.findAll()
                .map(companies -> companies.stream()
                        .map(CompanyResponse::fromCompany)
                        .collect(Collectors.toList()));
    }

    public Future<CompanyResponse> getCompanyById(String id) {
        return companyRepository.findById(id)
                .map(company -> {
                    if (company == null) {
                        throw new RuntimeException("Company not found with id: " + id);
                    }
                    return CompanyResponse.fromCompany(company);
                });
    }

    public Future<CompanyResponse> createCompany(CreateCompanyRequest request) {
        Company company = new Company();
        company.setId(request.getId());
        company.setCompanyCode(request.getCompanyCode());
        company.setCompanyName(request.getCompanyName());
        company.setIndustry(request.getIndustry());
        company.setContactPerson(request.getContactPerson());
        company.setEmail(request.getEmail());
        company.setPhone(request.getPhone());
        company.setAddress(request.getAddress());

        return companyRepository.create(company)
                .map(CompanyResponse::fromCompany);
    }

    public Future<CompanyResponse> updateCompany(String id, UpdateCompanyRequest request) {
        if (request.isEmpty()) {
            return Future.failedFuture("No fields provided for update");
        }
        return companyRepository.update(id, request.toUpdateDocument())
                .map(updated -> {
                    if (updated == null) {
                        throw new RuntimeException("Company not found with id: " + id);
                    }
                    return CompanyResponse.fromCompany(updated);
                });
    }

    public Future<Void> deleteCompany(String id) {
        return companyRepository.delete(id)
                .compose(deleted -> {
                    if (!deleted) {
                        return Future.failedFuture("Company not found with id: " + id);
                    }
                    return Future.succeededFuture();
                });
    }
}

