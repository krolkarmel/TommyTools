package com.tommytools.checkout;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CheckoutForm {

    @NotBlank(message = "Podaj imię i nazwisko")
    @Size(max = 120)
    private String fullName;

    @NotBlank(message = "Podaj email")
    @Email(message = "Nieprawidłowy email")
    @Size(max = 160)
    private String email;

    @NotBlank(message = "Podaj adres")
    @Size(max = 200)
    private String address;

    @NotBlank(message = "Podaj miasto")
    @Size(max = 120)
    private String city;

    @NotBlank(message = "Podaj kod pocztowy")
    @Size(max = 20)
    private String postalCode;

    @Size(max = 500)
    private String notes;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
