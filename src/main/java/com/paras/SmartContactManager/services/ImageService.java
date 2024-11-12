package com.paras.SmartContactManager.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    //public String UploadImage(MultipartFile profilepic); 

    String getUrlFromPublicId(String publicId);

    public String uploadImage(MultipartFile profilepic, String fileName);

    String UploadImage(MultipartFile profilePic);
   
       
        
    

}
