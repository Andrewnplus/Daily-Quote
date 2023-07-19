package com.example.security.controller.dto;

import com.sun.istack.NotNull;
import lombok.Getter;


@Getter
public class LoginDto {

   @NotNull
   private String username;
   @NotNull
   private String password;

   private boolean rememberMe;

   @Override
   public String toString() {
      return "LoginVM{" +
         "username='" + username + '\'' +
         ", rememberMe=" + rememberMe +
         '}';
   }
}
