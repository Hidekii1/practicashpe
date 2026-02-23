package com.ismail.issuetracking.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class LoginDTO {

    private String userName;
    private String password;
}
