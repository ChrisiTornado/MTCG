package com.monstertradingcardgame.message_server.API;

import com.monstertradingcardgame.message_server.BLL.user.IUserManager;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.server_core.http.HttpHeader;
import com.monstertradingcardgame.server_core.http.HttpRequest;

public class IdentityProvider {
    private final IUserManager _userManager;

    public IdentityProvider(IUserManager userManager) {
        _userManager = userManager;
    }

    public User getIdentityForRequest(HttpRequest request) {
        User currentUser = null;

        HttpHeader headers = request.getHeader();
        String authToken = headers.getHeader("Authorization");
        if (authToken != null && authToken.startsWith("Bearer ")) {
            String token = authToken.substring("Bearer ".length());
            try {
                currentUser = _userManager.getUserByAuthToken(token);
            } catch (Exception ignored) {
                // TODO: implement exception
            }
        }

       return currentUser;
    }
}
