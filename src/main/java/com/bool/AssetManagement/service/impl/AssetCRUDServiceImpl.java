package com.bool.AssetManagement.service.impl;

import com.bool.AssetManagement.domain.Asset;
import com.bool.AssetManagement.exceptions.VehicleAlreadyExistsException;
import com.bool.AssetManagement.exceptions.VehicleNotFoundException;
import com.bool.AssetManagement.repository.AssetDetailsRepository;
import com.bool.AssetManagement.service.AssetCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssetCRUDServiceImpl implements AssetCRUDService {

    private AssetDetailsRepository assetDetailsRepository;
    @Autowired
    public  AssetCRUDServiceImpl(AssetDetailsRepository assetDetailsRepository){
        this.assetDetailsRepository = assetDetailsRepository;
    }

    List list = new ArrayList();

    @Override
    public Asset saveAsset(Asset asset) throws VehicleAlreadyExistsException {
        if(assetDetailsRepository.existsById(asset.getVehicleNo())){
            throw new VehicleAlreadyExistsException("Asset Already Exists");
        }
        Asset savedAsset = assetDetailsRepository.save(asset);
        if(savedAsset == null){
            throw new VehicleAlreadyExistsException("Asset Already Exists");
        }
        return savedAsset;
    }

    @Override
    public List getAllAssets() throws IOException {
        List assetList = assetDetailsRepository.findAll();
        return assetList;
    }

    @Override
    public Asset updateAsset(Asset asset) throws VehicleNotFoundException {
        if(!(assetDetailsRepository.existsById(asset.getVehicleNo()))){
            throw new VehicleNotFoundException("Cant Update. Asset Not Found");
        }
        return assetDetailsRepository.save(asset);
    }

    @Override
    public boolean deleteAsset(int no) throws VehicleNotFoundException {
        if(!(assetDetailsRepository.existsById(no))){
            throw new VehicleNotFoundException("Cant Delete. Asset with that ID Not Found");
        }
        assetDetailsRepository.deleteById(no);
        return true;
    }
}
