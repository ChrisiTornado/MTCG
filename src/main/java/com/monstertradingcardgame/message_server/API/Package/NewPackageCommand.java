package com.monstertradingcardgame.message_server.API.Package;

import com.monstertradingcardgame.message_server.API.AuthenticatedRouteCommand;
import com.monstertradingcardgame.message_server.BLL.Package.IPackageManager;
import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.server_core.http.HttpResponse;
import com.monstertradingcardgame.server_core.http.HttpStatusCode;

public class NewPackageCommand extends AuthenticatedRouteCommand {

    private final IPackageManager _packageManager;
    private final Card[] cards;
    private final User identity;

    public NewPackageCommand(User identity, IPackageManager packageManager, Card[] cards) {
        super(identity);
        _packageManager = packageManager;
        this.cards = cards;
        this.identity = identity;
    }

    @Override
    public HttpResponse execute() {
        HttpResponse response;
        if (!identity.credentials.username.equals("admin")) {
            response = new HttpResponse(HttpStatusCode.CLIENT_ERROR_403_FORBIDDEN);
            return response;
        }

        try {
            _packageManager.newPackage(identity, cards);
            response = new HttpResponse(HttpStatusCode.SUCCESS_201_CREATED);
        } catch (RuntimeException e) {
            response = new HttpResponse(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
        return response;
    }
}
