package com.monstertradingcardgame.message_server.BLL.Package;

import com.monstertradingcardgame.message_server.API.Package.NoPackageAvailableException;
import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.User;

import java.sql.SQLException;
import java.util.List;

public interface IPackageManager {
    void newPackage(User identity, Card[] cardIds);
    List<Card> acquireNewPackage(User identity) throws SQLException, NoPackageAvailableException;
}
