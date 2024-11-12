package com.paras.SmartContactManager.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "user")
public class Contact 
{
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    private String description;
    private boolean favorite=false;
    private String websiteLink;
    private String linkedInLink;
    private String cloudinaryImagePublicId;
    @ManyToOne
    @JsonIgnore
    private user user;
    @OneToMany(mappedBy = "contact",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<SocialMedia> sm = new ArrayList<>(); 



}
