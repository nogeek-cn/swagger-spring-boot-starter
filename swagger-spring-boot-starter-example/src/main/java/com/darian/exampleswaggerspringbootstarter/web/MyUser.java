package com.darian.exampleswaggerspringbootstarter.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class MyUser {
    @NotNull
    private String username;
    private String password;
}
