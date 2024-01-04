package com.monstertradingcardgame.message_server.BLL.Package;

import com.monstertradingcardgame.message_server.API.Package.NoPackageAvailableException;
import com.monstertradingcardgame.message_server.DAL.DatabasePackageDao;
import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.User;

import java.sql.SQLException;
import java.util.List;

public class PackageManager implements IPackageManager {
    private DatabasePackageDao packageDao;

    public PackageManager(DatabasePackageDao packageDao) {
        this.packageDao = packageDao;
    }

    @Override
    public void newPackage(User identity, Card[] cardIds) {
        packageDao.createPackage(cardIds);
    }

    @Override
    public List<Card> acquireNewPackage(User identity) throws SQLException, NoPackageAvailableException {
        return packageDao.addPackageToPlayer(identity);
    }
}
