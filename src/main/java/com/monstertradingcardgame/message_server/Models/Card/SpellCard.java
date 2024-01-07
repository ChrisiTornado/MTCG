package com.monstertradingcardgame.message_server.Models.Card;

import java.util.UUID;

public class SpellCard extends Card {
    public ElementType elementType;
    public SpellCard(Card_Type name, double damage, UUID id, ElementType elementType) {
        super(id, name, damage);
        this.elementType = elementType;
    }
}
