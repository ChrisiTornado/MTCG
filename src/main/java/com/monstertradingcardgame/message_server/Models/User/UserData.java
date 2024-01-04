package com.monstertradingcardgame.message_server.Models.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserData {
    public String name;
    public String bio;
    public String image;
    public UserData(@JsonProperty("Username") String name, @JsonProperty("Bio") String bio, @JsonProperty("Image") String image) {
        name = this.name;
        bio = this.bio;
        image = this.image;
    }

    public boolean isEmpty() {
        return name.isEmpty() || bio.isEmpty() || image.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("Name: %s Bio: %s Image: %s", name, bio, image);
    }
}
