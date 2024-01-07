package com.monstertradingcardgame.message_server.Models.Card;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Card {
    public Card_Type name;
    public double damage;
    public UUID id;
    public ElementType elementType;

    public Card(@JsonProperty("Id") UUID id, @JsonProperty("Name") Card_Type name, @JsonProperty("Damage") double damage) {
        this.name = name;
        this.damage = damage;
        this.id = id;
        setElementType();
    }

    public UUID getId() {
        return id;
    }

    private void setElementType() {
        if (name.toString().startsWith("Fire")) {
            elementType = ElementType.Fire;
        } else if (name.toString().startsWith("Water")) {
            elementType = ElementType.Water;
        } else if (name.toString().startsWith("Regular")) {
            elementType = ElementType.Normal;
        } else if (name.toString().equals("Knight")) {
            elementType = ElementType.Water;
        } else if (name.toString().equals("Dragon")) {
            elementType = ElementType.Fire;
        } else if (name.toString().equals("Ork")) {
            elementType = ElementType.Normal;
        } else if (name.toString().equals("Kraken")) {
            elementType = ElementType.Water;
        } else if (name.toString().equals("Wizard")) {
            elementType = ElementType.Normal;
        }
    }

    @Override
    public String toString() {
        String elementTypeName = elementType.toString();
        return String.format("%s %f %s", name, damage, elementTypeName);
    }
}
