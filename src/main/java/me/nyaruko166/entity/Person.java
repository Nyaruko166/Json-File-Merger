package me.nyaruko166.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String ipAddress;
    private String avatar;
    private String country;
    private String job;
    private String company;
    private String salary;
    private String username;
    private String password;
    private String slogan;

}
