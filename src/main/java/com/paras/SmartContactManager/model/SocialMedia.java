package com.paras.SmartContactManager.model;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class SocialMedia {
    @Id
    private long id;
    private String link;
    private String title;
    @ManyToOne
    private Contact contact;


}
