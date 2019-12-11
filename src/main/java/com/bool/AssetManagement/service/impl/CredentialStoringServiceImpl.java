package com.bool.AssetManagement.service.impl;

import com.bool.AssetManagement.domain.AssetHistory;
import com.bool.AssetManagement.domain.DataAccessObject;
import com.bool.AssetManagement.exceptions.VehicleAlreadyExistsException;
import com.bool.AssetManagement.repository.AuthDetailsRepository;
import com.bool.AssetManagement.service.CredentialStoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialStoringServiceImpl implements CredentialStoringService {

    private AuthDetailsRepository authDetailsRepository;

    @Autowired
    public CredentialStoringServiceImpl(AuthDetailsRepository authDetailsRepository) {
        this.authDetailsRepository = authDetailsRepository;
    }

    @Override
    public DataAccessObject saveCredentials(DataAccessObject dataAccessObject) throws VehicleAlreadyExistsException {
        if(authDetailsRepository.existsByRegNo(dataAccessObject.getRegNo())){
            throw new VehicleAlreadyExistsException("Vehicle Already Exists");
        }
        DataAccessObject savedCredentials = authDetailsRepository.save(dataAccessObject);
        if(savedCredentials == null){
            throw new VehicleAlreadyExistsException("Null credentials");
        }
        return savedCredentials;
    }
}
