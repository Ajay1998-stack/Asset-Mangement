package com.bool.AssetManagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse implements Serializable {
    private  String jwttoken;

//    public JwtResponse(String jwttoken) {
//        this.jwttoken = jwttoken;
//    }
//
//    public String getToken() {
//        return this.jwttoken;
//    }

   }
