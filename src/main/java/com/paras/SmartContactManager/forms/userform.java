package com.paras.SmartContactManager.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class userform {
    @NotBlank(message="Username is required")
    @Size(min=3,message="Min 3 Character is required")
    private String name;
    @Email(message="Invalid Email Address")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "password is Required")
    @Size(min=6,message = "Min 6 Character is required")
    private String password;
    @NotBlank(message="About is required")
    private String about;
    @NotBlank(message = "Phone Number is required")
    @Size(min=10,max=12,message = "min 10 character required")
    private String phoneNumber;


}
