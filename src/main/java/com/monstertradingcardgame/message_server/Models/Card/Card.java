package com.monstertradingcardgame.message_server.Models.Card;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Card {
    public Card_Type name;
    public double damage;
    public UUID id;
    public ElementType elementType;

    public Card(@JsonProperty("Name") Card_Type name, @JsonProperty("Damage") double damage, @JsonProperty("Id") UUID id) {
        this.name = name;
        this.damage = damage;
        this.id = id;
        setElementType();
    }

    private void setElementType() {
        if (name.toString().startsWith("Fire")) {
            ElementType elementType = ElementType.Fire;
        } else if (name.toString().startsWith("Water")) {
            ElementType elementType = ElementType.Water;
        } else if (name.toString().startsWith("Regular")) {
            ElementType elementType = ElementType.Normal;
        } else if (name.toString().equals("Knight")) {
            ElementType elementType = ElementType.Water;
        } else if (name.toString().equals("Dragon")) {
            ElementType elementType = ElementType.Fire;
        } else if (name.toString().equals("Ork")) {
            ElementType elementType = ElementType.Normal;
        } else if (name.toString().equals("Kraken")) {
            ElementType elementType = ElementType.Water;
        } else if (name.toString().equals("Wizard")) {
            ElementType elementType = ElementType.Normal;
        }
    }

    @Override
    public String toString() {
        return String.format("%s %d %s", name, damage, elementType);
    }
}
