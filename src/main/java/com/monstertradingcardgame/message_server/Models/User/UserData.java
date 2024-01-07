package com.monstertradingcardgame.message_server.Models.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserData {
    public String username;
    public String bio;
    public String image;
    public UserData(@JsonProperty("Name") String name, @JsonProperty("Bio") String bio, @JsonProperty("Image") String image) {
        this.username = name;
        this.bio = bio;
        this.image = image;
    }

    public boolean isEmpty() {
        return username.isEmpty() || bio.isEmpty() || image.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("Name: %s Bio: %s Image: %s", username, bio, image);
    }
}
