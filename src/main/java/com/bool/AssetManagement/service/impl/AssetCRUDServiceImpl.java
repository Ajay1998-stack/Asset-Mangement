package com.bool.AssetManagement.service.impl;

import com.bool.AssetManagement.domain.Asset;
import com.bool.AssetManagement.exceptions.StorageException;
import com.bool.AssetManagement.exceptions.VehicleAlreadyExistsException;
import com.bool.AssetManagement.exceptions.VehicleNotFoundException;
import com.bool.AssetManagement.repository.AssetDetailsRepository;
import com.bool.AssetManagement.service.AssetCRUDService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInput;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssetCRUDServiceImpl implements AssetCRUDService {

    private AssetDetailsRepository assetDetailsRepository;

    @Autowired
    public AssetCRUDServiceImpl(AssetDetailsRepository assetDetailsRepository) {
        this.assetDetailsRepository = assetDetailsRepository;
    }

    List list = new ArrayList();

    @Override
    public Asset saveAsset(Asset asset) throws VehicleAlreadyExistsException {
        if (assetDetailsRepository.existsByRegNo(asset.getRegNo())) {
            throw new VehicleAlreadyExistsException("Asset Already Exists");
        }
        Asset savedAsset = assetDetailsRepository.save(asset);
        if (savedAsset == null) {
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
    public Asset updateAsset(Asset asset) throws VehicleNotFoundException, IOException {
        Asset jsonAsset = new ObjectMapper().readValue((DataInput) asset, Asset.class);
        System.out.println(jsonAsset);
        if (!(assetDetailsRepository.existsByRegNo(asset.getRegNo()))) {
            throw new VehicleNotFoundException("Cant Update. Asset Not Found");
        }
        return assetDetailsRepository.save(jsonAsset);
    }

    @Override
    public long deleteAsset(String regNo) throws VehicleNotFoundException {
        if (!(assetDetailsRepository.existsByRegNo(regNo))) {
            throw new VehicleNotFoundException("Cant Delete. Asset with that RegNo Not Found");
        }

        return assetDetailsRepository.deleteByRegNo(regNo);
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String path = "/home/cgi/Downloads";
        System.out.println(path);
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }
        try {
            var fileName = file.getOriginalFilename();
            System.out.println(fileName);
            var is = file.getInputStream();
            System.out.println(is);
            String localPath = Paths.get(path + "/" + file.getOriginalFilename()).toString();
            System.out.println(localPath);
            Files.copy(file.getInputStream(), Path.of(path, fileName), StandardCopyOption.REPLACE_EXISTING);
            return localPath;
        } catch (IOException e) {
            var msg = String.format("Failed to store file", file.getName());
            throw new StorageException(msg, e);
        }
    }
}
