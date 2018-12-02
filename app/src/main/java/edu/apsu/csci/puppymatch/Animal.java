package edu.apsu.csci.puppymatch;

enum Size {
    SMALL, MEDIUM, LARGE
}

public class Animal {

    private int id;
    private String name;
    private String type;
    private String species;
    private int age;
    private boolean children;
    private Size size;
    private int photo;
    private String totalLikes;
    private String gender;
    private String shelterName;
    private String shelterAddress;
    private String shelterPhone;

    public Animal() {

    }

    public Animal(int id, String name, String gender, String species, int age, boolean children, Size size, int photo, String shelterName, String shelterAddress, String shelterPhone) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.gender = gender;
        this.species = species;
        this.age = age;
        this.children = children;
        this.size = size;
        this.photo = photo;
        this.shelterName = shelterName;
        this.shelterAddress = shelterAddress;
        this.shelterPhone = shelterPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTotalLikes() {
        return totalLikes;
    }
    public void setTotalLikes(String totalLikes) {
        this.totalLikes = totalLikes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isChildren() {
        return children;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getShelterName() { return shelterName; }

    public String getShelterAddress() { return shelterAddress; }

    public String getShelterPhone() { return shelterPhone; }

}