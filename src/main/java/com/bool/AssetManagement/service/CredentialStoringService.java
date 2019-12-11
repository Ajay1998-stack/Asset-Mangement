package com.bool.AssetManagement.service;

import com.bool.AssetManagement.domain.DataAccessObject;
import com.bool.AssetManagement.exceptions.VehicleAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public interface CredentialStoringService {
    DataAccessObject saveCredentials(DataAccessObject dataAccessObject) throws VehicleAlreadyExistsException;

}
