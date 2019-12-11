package com.bool.AssetManagement.controller;

import com.bool.AssetManagement.domain.*;
import com.bool.AssetManagement.exceptions.BookingAlreadyExistsException;
import com.bool.AssetManagement.repository.AuthDetailsRepository;
import com.bool.AssetManagement.service.*;
import com.bool.AssetManagement.exceptions.VehicleAlreadyExistsException;
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
@CrossOrigin("*")
@RequestMapping(value = "/api/v1")
public class AssetController {

    private SimpMessagingTemplate template;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService userDetailsService;
    private AssetCRUDService assetCRUDService;
    private AuthenticationManager authenticationManager;
    private CredentialStoringService credentialStoringService;
    private AssetManagementService assetManagementService;


    @Autowired
    public  AssetController(AssetCRUDService assetCRUDService,AuthenticationManager authenticationManager,SimpMessagingTemplate template,JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService,CredentialStoringService credentialStoringService,AssetManagementService assetManagementService){

        this.assetCRUDService = assetCRUDService;
        this.authenticationManager=  authenticationManager;
        this.template= template;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.credentialStoringService = credentialStoringService;
        this.assetManagementService = assetManagementService;

    }

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
            DataAccessObject dataAccessObject = new DataAccessObject();
            dataAccessObject.setRegNo(jsonAsset.getRegNo());
            dataAccessObject.setPassword(jsonAsset.getRegNo());
            credentialStoringService.saveCredentials(dataAccessObject);
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

    @DeleteMapping("/assetUpdate/{regNo}")
    public ResponseEntity<?> deleteAsset(@PathVariable("regNo") String regNo){
        System.out.println("IS this getting executed !!" + " " + regNo);
        ResponseEntity responseEntity;
        try {
//            assetCRUDService.deleteAsset(regNo);
            return new ResponseEntity<>(assetCRUDService.deleteAsset(regNo), HttpStatus.OK);
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
        authenticate(authenticationRequest.getRegNo(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getRegNo());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
        private void authenticate(String regNo, String password) throws Exception {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(regNo, password));
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


    @KafkaListener(topics = "KafkaEndRide", groupId = "group_json2",containerFactory = "userKafkaListenerFactory2")
    public void consumeJson(RideEnd rideEnd) throws VehicleAlreadyExistsException {
        System.out.println("Consumed JSON Message: " + rideEnd);
        System.out.println("6");

        AdminObject adminObject  = new AdminObject();
        adminObject.setId(parseInt(rideEnd.getUser_id()));
        adminObject.setStation(rideEnd.getEnd_station());
        adminObject.setStatus(rideEnd.getVehicle_status());
        adminObject.setFeedbackOrComments(rideEnd.getComments());
        template.convertAndSend("/topic/adminUI",adminObject);


        AssetHistory assetHistory = new AssetHistory();
        assetHistory.setUsername(rideEnd.getUser_id());
        assetHistory.setInitTime(rideEnd.getStarttime());
        assetHistory.setDropTime(rideEnd.getEndtime());
        assetHistory.setFeedbackOrComments(rideEnd.getComments());
        assetHistory.setInitMeterReading(rideEnd.getInitial_meter_reading());
        assetHistory.setFinalMeterReading(rideEnd.getFinal_meter_reading());
        assetHistory.setStation(rideEnd.getEnd_station());
        assetHistory.setTotalDistance(rideEnd.getFinal_meter_reading()-rideEnd.getInitial_meter_reading());
        assetHistory.setBookingID(rideEnd.getBooking_id());

        try {
            assetManagementService.saveVehicle(assetHistory);
            System.out.println("assetHistory stored");
        }catch (BookingAlreadyExistsException ex){
            System.out.println("booking already exists");
        }

    }
}
