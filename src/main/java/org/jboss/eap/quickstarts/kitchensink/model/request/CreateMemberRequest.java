package org.jboss.eap.quickstarts.kitchensink.model.request;

import com.google.gson.Gson;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Builder
public class CreateMemberRequest {

    @NotNull(message = "name can not be null")
    @Size(min = 1, max = 25, message = "name length should be between 1 to 25")
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String name;

    @NotNull(message = "Email can not be null")
    @NotEmpty(message = "Email can not be empty")
    private String email;

    @NotNull(message = "PhoneNumber can not be null")
    @Size(min = 10, max = 12, message = "Phone length should be between 10 to 12 ")
    @Digits(fraction = 0, integer = 12, message = "Digit can only be between 0 to 12")
    private String phoneNumber;


    public CreateMemberRequest() {

    }

    public CreateMemberRequest(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
