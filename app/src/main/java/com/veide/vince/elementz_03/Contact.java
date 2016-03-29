package com.veide.vince.elementz_03;

import java.util.Date;

/**
 * Created by vince on 3/24/16.
 */
public class Contact {

    private String name, email, phone;
    private String picture;
    private int birthDay, birthMonth, birthYear;

    public Contact(){
        name = email = phone = "";
        birthDay = birthMonth = birthYear = 0;
    }

    public Contact(String name, String email, String phone,
                   int birthDay, int birthMonth, int birthYear){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
    }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setBirthDay(int birthDay) { this.birthDay = birthDay; }
    public void setBirthMonth(int birthMonth) { this.birthMonth = birthMonth; }
    public void setBirthYear(int birthYear) { this.birthYear = birthYear; }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public int getBirthDay() { return birthDay; }
    public int getBirthMonth() { return birthMonth; }
    public int getBirthYear() { return birthYear; }
}
