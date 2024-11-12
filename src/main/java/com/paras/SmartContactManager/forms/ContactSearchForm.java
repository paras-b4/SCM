package com.paras.SmartContactManager.forms;

import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class ContactSearchForm {

    private String field;
    private String value;
}
