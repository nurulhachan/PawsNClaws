package com.example.pawsnclaws;

public class Animal {
    private String name;
    private String imageUrl;
    private String info;
    private String soundUrl;

    public Animal() {
        // Default constructor required for calls to DataSnapshot.getValue(Animal.class)
    }

    public Animal(String name, String imageUrl, String info, String soundUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.info = info;
        this.soundUrl = soundUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSoundUrl() {
        return soundUrl;
    }

    public void setSoundUrl(String soundUrl) {
        this.soundUrl = soundUrl;
    }
}
