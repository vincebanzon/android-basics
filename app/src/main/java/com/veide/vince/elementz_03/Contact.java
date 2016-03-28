package com.veide.vince.elementz_03;

import java.util.Date;

/**
 * Created by vince on 3/24/16.
 */
public class Contact {

    private String name, email, phone;
    private Date birthday;
    private String picture;

    public Contact(){
        name = email = phone = "";
    }

    public Contact(String name, String email, String phone){
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}
