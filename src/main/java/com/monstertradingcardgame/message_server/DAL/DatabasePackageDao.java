package com.monstertradingcardgame.message_server.DAL;

import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.User;

import java.util.List;
import java.util.UUID;

public class DatabasePackageDao implements IPackageDao {
    @Override
    public void createPackage(Card[] cards) {

    }

    @Override
    public List<Card> addPackageToPlayer(User user) {
        return null;
    }

    @Override
    public void deletePackageId(int package_id) {

    }

    @Override
    public void insertCardToUserStack(String username, UUID[] cardIds) {

    }

    @Override
    public void insertIntoTableCard(Card card) {

    }

    @Override
    public void boughtPackage(User user) {

    }
}
