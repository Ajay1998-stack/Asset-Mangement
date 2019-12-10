package com.bool.AssetManagement.controller;

import com.bool.AssetManagement.domain.*;
import com.bool.AssetManagement.service.JwtTokenUtil;
import com.bool.AssetManagement.exceptions.VehicleAlreadyExistsException;
import com.bool.AssetManagement.service.AssetCRUDService;
import com.bool.AssetManagement.service.JwtUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.bool.AssetManagement.service.CreateGoogleFile.createGoogleFile;
import static java.lang.Integer.parseInt;

@RestController
@RequestMapping(value = "/api/v1")
public class AssetController {

    @Autowired
    private SimpMessagingTemplate template;

    private AssetCRUDService assetCRUDService;
    @Autowired
    public  AssetController(AssetCRUDService assetCRUDService){
        this.assetCRUDService = assetCRUDService;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/assetentry" )
    public ResponseEntity<?> SaveAsset(@RequestParam String asset,@RequestParam MultipartFile image){
        ResponseEntity responseEntity;
        try {
            Asset jsonAsset = new ObjectMapper().readValue(asset, Asset.class);
            System.out.println(jsonAsset);
            System.out.println(image);
            String path = assetCRUDService.uploadFile(image);
            System.out.println(path);
            java.io.File uploadFile = new java.io.File(path);
            // Create Google File:
            File googleFile = createGoogleFile(null, "*/*", "test.jpeg", uploadFile);
            System.out.println("Created Google file!");
            System.out.println("WebContentLink: " + googleFile.getWebContentLink());
            System.out.println("WebViewLink: " + googleFile.getWebViewLink());
            System.out.println("Done!");
            jsonAsset.setPhotoUrl(googleFile.getWebContentLink());
            assetCRUDService.saveAsset(jsonAsset);
            responseEntity = new ResponseEntity<Asset>(jsonAsset, HttpStatus.CREATED);
        }catch (IOException | VehicleAlreadyExistsException ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("/assetentry")
    public ResponseEntity<?> getAllVehicles() {
        ResponseEntity responseEntity;
        try{
        return new ResponseEntity<List<Asset>>(assetCRUDService.getAllAssets(),HttpStatus.OK);
        }catch (IOException ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @PutMapping("/assetUpdate")
    public  ResponseEntity<?> updateVehicle(@RequestParam String asset,@RequestParam MultipartFile image) {
        ResponseEntity responseEntity;
        try {
            Asset jsonAsset = new ObjectMapper().readValue(asset, Asset.class);
            System.out.println(jsonAsset);
            System.out.println(image);
            String path = assetCRUDService.uploadFile(image);
            System.out.println(path);
            java.io.File uploadFile = new java.io.File(path);
            // Create Google File:
            File googleFile = createGoogleFile(null, "*/*", "test.jpeg", uploadFile);
            System.out.println("Created Google file!");
            System.out.println("WebContentLink: " + googleFile.getWebContentLink());
            System.out.println("WebViewLink: " + googleFile.getWebViewLink());
            System.out.println("Done!");
            jsonAsset.setPhotoUrl(googleFile.getWebContentLink());
            assetCRUDService.updateAsset(jsonAsset);
            return new ResponseEntity<String>("successfully updated", HttpStatus.OK);
        }catch (Exception ex){
            responseEntity= new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @DeleteMapping("/assetUpdate/{id}")
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

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
        private void authenticate(String username, String password) throws Exception {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                } catch (DisabledException e) {
                    throw new Exception("USER_DISABLED", e);
                } catch (BadCredentialsException e) {
                    throw new Exception("INVALID_CREDENTIALS", e);
                }
            }

    @GetMapping("/hello")
    public String firstPage() {
        return "Hello World";
    }

    @PostMapping("/myroute")
    public ResponseEntity<?> mymethod(@RequestParam MultipartFile file) {
        System.out.println(file);
        return  new ResponseEntity<String>("asdasd", HttpStatus.OK);
    }

    @KafkaListener(topics = "KafkaStartRide", groupId = "group_json",containerFactory = "userKafkaListenerFactory")
    public void consumeJson(RideStart rideStart) {
        System.out.println("Consumed JSON Message: " + rideStart);
        System.out.println("6");
        AdminObject adminObject  = new AdminObject();
        adminObject.setId(parseInt(rideStart.getUser_id()));
        adminObject.setStation(rideStart.getStart_station());
        adminObject.setStatus(rideStart.getVehicle_status());
        adminObject.setFeedbackOrComments(rideStart.getComments());
        template.convertAndSend("/topic/adminUI",adminObject);
    }


    @KafkaListener(topics = "KafkaEndRide", groupId = "group_json",containerFactory = "userKafkaListenerFactory")
    public void consumeJson(RideEnd rideEnd) {
        System.out.println("Consumed JSON Message: " + rideEnd);
        System.out.println("6");
        AdminObject adminObject  = new AdminObject();
        adminObject.setId(parseInt(rideEnd.getUser_id()));
        adminObject.setStation(rideEnd.getStart_station());
        adminObject.setStatus(rideEnd.getVehicle_status());
        adminObject.setFeedbackOrComments(rideEnd.getComments());
        template.convertAndSend("/topic/adminUI",adminObject);
    }
}
