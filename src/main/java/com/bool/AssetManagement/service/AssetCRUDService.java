package com.bool.AssetManagement.service;

import com.bool.AssetManagement.domain.Asset;
import com.bool.AssetManagement.exceptions.VehicleAlreadyExistsException;
import com.bool.AssetManagement.exceptions.VehicleNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface AssetCRUDService {
    Asset saveAsset(Asset asset) throws VehicleAlreadyExistsException;
    List getAllAssets() throws IOException;
    Asset updateAsset(Asset asset) throws VehicleNotFoundException, IOException;
    long deleteAsset(String regNo) throws VehicleNotFoundException;
    String uploadFile(MultipartFile multipartFile) throws IOException;
    int meterReading(String regNo) throws VehicleNotFoundException;
    
}
