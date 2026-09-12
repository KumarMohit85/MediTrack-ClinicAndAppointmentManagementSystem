package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;

import com.airtribe.meditrack.util.Validator;

public abstract class Person extends MedicalEntity {
    private String name;
    private int age;
    private String phone;
    private String email;

    public Person(String id, LocalDateTime createdAt, String name, int age, String phone, String email) {
        super(id, createdAt);
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.email = email;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean validate() {
        return Validator.validateName(name)
                && Validator.validateAge(age)
                && Validator.validatePhone(phone)
                && Validator.validateEmail(email);
    }

    public String getDetails() {
        return "Name: " + name + ", Age: " + age + ", Phone: " + phone + ", Email: " + email;
    }

}
