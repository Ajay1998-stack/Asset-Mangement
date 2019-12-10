package com.bool.AssetManagement.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


import com.bool.AssetManagement.configuration.GoogleDriveUtils;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

public class
CreateGoogleFile {

    private static File _createGoogleFile(String googleFolderIdParent, String contentType,
                                          String customFileName, AbstractInputStreamContent uploadStreamContent) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(customFileName);
        List<String> parents = Arrays.asList(googleFolderIdParent);
        fileMetadata.setParents(parents);
        Drive driveService = GoogleDriveUtils.getDriveService();
        File file = driveService.files().create(fileMetadata, uploadStreamContent).setFields("id, webContentLink, webViewLink, parents").execute();
        return file;
    }

    // Create Google File from byte[]
    public static File createGoogleFile(String googleFolderIdParent, String contentType, //
                                        String customFileName, byte[] uploadData) throws IOException {
        //
        AbstractInputStreamContent uploadStreamContent = new ByteArrayContent(contentType, uploadData);
        //
        return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
    }

    // Create Google File from java.io.File
    public static File createGoogleFile(String googleFolderIdParent, String contentType, //
                                        String customFileName, java.io.File uploadFile) throws IOException {
        AbstractInputStreamContent uploadStreamContent = new FileContent(contentType, uploadFile);
        return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
    }

//    public static void main(String[] args) throws IOException {
//        java.io.File uploadFile = new java.io.File("/home/cgi/index.jpeg");
//        // Create Google File:
//        File googleFile = createGoogleFile(null, "*/*", "test.jpeg", uploadFile);
//        System.out.println("Created Google file!");
//        System.out.println("WebContentLink: " + googleFile.getWebContentLink());
//        System.out.println("WebViewLink: " + googleFile.getWebViewLink());
//        System.out.println("Done!");
//    }
}
