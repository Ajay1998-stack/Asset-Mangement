package com.bool.AssetManagement.controller;

import com.bool.AssetManagement.domain.Asset;
import com.bool.AssetManagement.exceptions.VehicleAlreadyExistsException;
import com.bool.AssetManagement.service.AssetCRUDService;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.bool.AssetManagement.service.CreateGoogleFile.createGoogleFile;

@RestController
@RequestMapping(value = "api/v1")
public class AssetController {
    private AssetCRUDService assetCRUDService;
    @Autowired
    public  AssetController(AssetCRUDService assetCRUDService){
        this.assetCRUDService = assetCRUDService;
    }

    @PostMapping("asset")
    public ResponseEntity<?> SaveAsset(@RequestBody Asset asset, @RequestParam String path ){
        ResponseEntity responseEntity;
        try {

            java.io.File uploadFile = new java.io.File(path);
            // Create Google File:
            File googleFile = createGoogleFile(null, "*/*", "test.jpeg", uploadFile);
            System.out.println("Created Google file!");
            System.out.println("WebContentLink: " + googleFile.getWebContentLink());
            System.out.println("WebViewLink: " + googleFile.getWebViewLink());
            System.out.println("Done!");
            asset.setPhotoUrl(googleFile.getWebContentLink());
            assetCRUDService.saveAsset(asset);
            responseEntity = new ResponseEntity<Asset>(asset, HttpStatus.CREATED);
        }catch (VehicleAlreadyExistsException | IOException ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("asset")
    public ResponseEntity<?> getAllVehicles() {
        ResponseEntity responseEntity;
        try{
        return new ResponseEntity<List<Asset>>(assetCRUDService.getAllAssets(),HttpStatus.OK);
        }catch (IOException ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @PutMapping("assetUpdate")
    public  ResponseEntity<?> updateVehicle(@RequestBody Asset asset) {
        ResponseEntity responseEntity;
        try {
            assetCRUDService.updateAsset(asset);
            return new ResponseEntity<String>("successfully updated", HttpStatus.OK);
        }catch (Exception ex){
            responseEntity= new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @DeleteMapping("assetUpdate/{id}")
    public ResponseEntity<?> deleteAsset(@PathVariable("id") int id){
        ResponseEntity responseEntity;
        try {
            assetCRUDService.deleteAsset(id);
            return new ResponseEntity<>(assetCRUDService.getAllAssets(), HttpStatus.OK);
        }catch (Exception ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("image")
    public ResponseEntity<?> imageUrl() throws IOException {
        ResponseEntity responseEntity;

        java.io.File uploadFile = new java.io.File("/home/cgi/index.jpeg");
        // Create Google File:
        File googleFile = createGoogleFile(null, "*/*", "test.jpeg", uploadFile);
        System.out.println("Created Google file!");
        System.out.println("WebContentLink: " + googleFile.getWebContentLink());
        System.out.println("WebViewLink: " + googleFile.getWebViewLink());
        System.out.println("Done!");
        responseEntity = new ResponseEntity<String>(googleFile.getWebContentLink(),HttpStatus.CONFLICT);
        return responseEntity;
    }


}
