package com.bool.AssetManagement.service;

import com.bool.AssetManagement.domain.Asset;
import com.bool.AssetManagement.exceptions.VehicleAlreadyExistsException;
import com.bool.AssetManagement.exceptions.VehicleNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface AssetCRUDService {
    Asset saveAsset(Asset asset) throws VehicleAlreadyExistsException;
    List getAllAssets() throws IOException;
    Asset updateAsset(Asset asset) throws VehicleNotFoundException;
    boolean deleteAsset(int no) throws VehicleNotFoundException;
}
