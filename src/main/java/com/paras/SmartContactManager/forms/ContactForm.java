package com.paras.SmartContactManager.forms;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.paras.SmartContactManager.Validators.ValidFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Getter
@Setter
@Builder
@ToString
public class ContactForm {

    @NotBlank(message = "Name is required")
    @Size(min=3,message="min 3 character is required")
    private String name;
    
    @Email(message = "Invalid email address")
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "phone no is required")
    private String phoneNumber;
    @NotBlank(message = "address is required")
    private String address;
    private String description;
    private boolean favorite;
    private String picture;

    @ValidFile(message = "invalid file")
    private MultipartFile profilepic; 
    private String websiteLink;
    private String Linkedlin;
    
    

}
