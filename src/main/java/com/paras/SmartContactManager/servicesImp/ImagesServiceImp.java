package com.paras.SmartContactManager.servicesImp;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.paras.SmartContactManager.helper.AppConstants;
import com.paras.SmartContactManager.services.ImageService;
@Service
public class ImagesServiceImp implements ImageService {

    private Cloudinary cloudinary;

    public ImagesServiceImp(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    @Override
    public String UploadImage(MultipartFile profilepic) {
        String filename=UUID.randomUUID().toString();

        try {
            byte[] data=new byte[profilepic.getInputStream().available()];
            profilepic.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                "public_id",filename
               
            ));
            return this.getUrlFromPublicId(filename);
        } catch (IOException e) {
           
            e.printStackTrace();
            return null;
        }
        }
    @Override
    public String uploadImage(MultipartFile contactImage, String filename) {

        // code likhnaa hia jo image ko upload kar rha ho

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id", filename));

            return this.getUrlFromPublicId(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // and return raha hoga : url

    }


    @Override
    public String getUrlFromPublicId(String publicId) {

        return cloudinary
        .url()
        .transformation(
            new Transformation<>()
            .width(AppConstants.CONTACT_IMAGE_WIDTH)
            .height(AppConstants.CONATCT_IMAGE_HEIGHT)
            .crop(AppConstants.CONATCT_IMAGE_CROP))
        .generate(publicId);
         }


    


   
        

}
