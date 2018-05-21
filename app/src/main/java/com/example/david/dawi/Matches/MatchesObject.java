package com.example.david.dawi.Matches;

public class MatchesObject {
    private String userId;
    private String name;
    private String age;
    private String phone;
    private String gender;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public MatchesObject(String userId, String name, String age, String phone, String gender, String image){
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.gender = gender;
        this.image = image;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
