package com.panchenko.LogisticsApp.service;

import com.panchenko.LogisticsApp.dto.ContractorDTO;
import com.panchenko.LogisticsApp.model.Contractor;

import java.util.List;

public interface ContractorService {
    Contractor create(Contractor contractor);

    Contractor readById(long id);

    Contractor update(Contractor contractor);

    void delete(long id);

    List<Contractor> getAll();

    Contractor convertToContractor(ContractorDTO contractorDTO);

    ContractorDTO convertToContractorDTO(Contractor contractor);
}
