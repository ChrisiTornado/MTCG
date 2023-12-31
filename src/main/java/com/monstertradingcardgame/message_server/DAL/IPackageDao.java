package com.monstertradingcardgame.message_server.DAL;

import com.monstertradingcardgame.message_server.API.Package.NoPackageAvailableException;
import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.User;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface IPackageDao {
    public void createPackage(Card[] cards);
    public List<Card> addPackageToPlayer(User user) throws SQLException, NoPackageAvailableException;
    public void deletePackageId(int package_id);
    public void insertCardToUserStack(String username, UUID[] cardIds);
    public void insertIntoTableCard(Card card);
    public void boughtPackage(User user);
}
