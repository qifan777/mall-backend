package io.qifan.mall.server.auth.model;

import lombok.Data;

@Data
public class PhonePasswordAuth implements AuthModel{
    private String phoneNumber;
    private String password;
}
