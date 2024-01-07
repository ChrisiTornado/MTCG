package com.monstertradingcardgame.message_server.Models.Card;

import java.util.UUID;

public class MonsterCard extends Card {
    public MonsterType monsterType;
    public MonsterCard(Card_Type name, double damage, UUID id, MonsterType monsterType) {
        super(id, name, damage);
        this.monsterType = monsterType;
    }
}
