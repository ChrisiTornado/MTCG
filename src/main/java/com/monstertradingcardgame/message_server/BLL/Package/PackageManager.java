package com.monstertradingcardgame.message_server.BLL.Package;

import com.monstertradingcardgame.message_server.DAL.DatabasePackageDao;
import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.User;

import java.util.List;

public class PackageManager implements IPackageManager {
    private DatabasePackageDao packageDao = new DatabasePackageDao();

    @Override
    public void newPackage(User identity, Card[] cardIds) {
        packageDao.createPackage(cardIds);
    }

    @Override
    public List<Card> acquireNewPackage(User identity) {
        return packageDao.addPackageToPlayer(identity);
    }
}
