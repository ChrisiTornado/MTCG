package com.monstertradingcardgame.message_server.API;

import com.monstertradingcardgame.message_server.BLL.user.IUserManager;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.server_core.http.HttpRequest;

import java.util.Map;

public class IdentityProvider {
    private final IUserManager _userManager;

    public IdentityProvider(IUserManager userManager) {
        _userManager = userManager;
    }

    public User getIdentityForRequest(HttpRequest request) {
        User currentUser = null;
//
//        Map<String, String> headers = request.getHeader();
//        if (headers.containsKey("Authorization")) {
//            String authToken = headers.get("Authorization");
//            String prefix = "Bearer ";
//
//            if (authToken.startsWith(prefix)) {
//                try {
//                    currentUser = _userManager.getUserByAuthToken(authToken.substring(prefix.length()));
//                } catch (Exception ignored) {
//                    // Behandlung der Ausnahme (falls erforderlich)
//                }
//            }
//        }
//
       return currentUser;
    }
}
