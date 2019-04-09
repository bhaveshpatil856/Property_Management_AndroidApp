package com.example.bhaveshpatil.niwaraa.model;

public class PropertyListRepo {

    String id;
    String project;
    String city;
    String locality;
    String address;
    String price;
    String number;
    String image_name;
    String image_path;

    public PropertyListRepo() {

        this.id= id;
        this.project = project;
        this.city = city;
        this.locality = locality;
        this.address = address;
        this.price = price;
        this.number = number;
        this.image_name=image_name;
        this.image_path=image_path;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
