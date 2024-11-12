package com.paras.SmartContactManager.Validators;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private static final long MAX_FILE_SIZE = 1024 * 1024*100*5; // 100 KB

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            // context.disableDefaultConstraintViolation();
            // context.buildConstraintViolationWithTemplate("File cannot be empty").addConstraintViolation();
             return true;
        }
        
        if (file.getSize() > MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size should be less than 5MB").addConstraintViolation();
            return false;
        }

        // Uncomment and implement image validation if needed
        /*
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            // Additional checks on the image can be added here
        } catch (IOException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid file format").addConstraintViolation();
            return false;
        }
        */

        return true;
    }
}