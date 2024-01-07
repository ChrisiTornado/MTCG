package com.monstertradingcardgame.http;

import com.monstertradingcardgame.message_server.API.IRouteCommand;
import com.monstertradingcardgame.message_server.API.User.GetUserCommand;
import com.monstertradingcardgame.message_server.API.User.LoginCommand;
import com.monstertradingcardgame.message_server.API.User.RegisterCommand;
import com.monstertradingcardgame.message_server.API.User.UpdateUserCommand;
import com.monstertradingcardgame.message_server.BLL.user.DuplicateUserException;
import com.monstertradingcardgame.message_server.BLL.user.UserManager;
import com.monstertradingcardgame.message_server.DAL.DatabaseManager;
import com.monstertradingcardgame.message_server.DAL.DatabaseUserDao;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserData;
import com.monstertradingcardgame.server_core.http.HttpParser;
import com.monstertradingcardgame.server_core.http.HttpResponse;
import com.monstertradingcardgame.server_core.http.HttpStatusCode;
import com.monstertradingcardgame.server_core.httpserver.config.ConfigurationManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTest {
    private HttpParser httpParser;
    private DatabaseManager databaseManager = new DatabaseManager();

    @BeforeAll
    public void beforeClass() {
        httpParser = new HttpParser();
    }

    @Test
    public void registerUser() {
        System.out.println("Register User:");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        databaseManager.initializeDatabase();
        User user = new User("test", "test", 20);
        DatabaseUserDao userDao = new DatabaseUserDao();
        UserManager _userManager = new UserManager(userDao);

        IRouteCommand command = new RegisterCommand(_userManager, user.credentials);
        HttpResponse response = command.execute();
        System.out.println(response.buildResponse());
        assertEquals(response.getStatusCode(), HttpStatusCode.SUCCESS_201_CREATED);
    }

    @Test
    public void login() {
        System.out.println("login user:");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        databaseManager.initializeDatabase();
        User user = new User("test", "test", 20);
        DatabaseUserDao userDao = new DatabaseUserDao();
        UserManager _userManager = new UserManager(userDao);

        IRouteCommand command = new RegisterCommand(_userManager, user.credentials);
        HttpResponse response = command.execute();

        command = new LoginCommand(_userManager, user.credentials);
        response = command.execute();
        System.out.println(response.buildResponse());
        assertEquals(response.getStatusCode(), HttpStatusCode.SUCCESS_200_OK);
    }

    @Test
    public void loginFail() {
        System.out.println("login user failed:");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        databaseManager.initializeDatabase();
        User user = new User("test", "test", 20);
        User user2 = new User("test", "test2", 20);
        DatabaseUserDao userDao = new DatabaseUserDao();
        UserManager _userManager = new UserManager(userDao);

        IRouteCommand command = new RegisterCommand(_userManager, user.credentials);
        HttpResponse response = command.execute();

        command = new LoginCommand(_userManager, user2.credentials);
        response = command.execute();
        System.out.println(response.buildResponse());
        assertEquals(response.getStatusCode(), HttpStatusCode.CLIENT_ERROR_401_UNAUTHORIZED);
    }

    @Test
    public void updateUser() {
        System.out.println("update User:");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        databaseManager.initializeDatabase();
        User user = new User("test", "test", 20);
        DatabaseUserDao userDao = new DatabaseUserDao();
        UserManager _userManager = new UserManager(userDao);

        IRouteCommand command = new RegisterCommand(_userManager, user.credentials);
        HttpResponse response = command.execute();

        UserData ud = new UserData("tesst", "Im 21 years old", ":D");

        command = new UpdateUserCommand(_userManager, user.credentials.username, user, ud);
        response = command.execute();
        System.out.println(response.buildResponse());
        assertEquals(response.getStatusCode(), HttpStatusCode.SUCCESS_200_OK);
    }

    @Test
    public void updateUserNotExists() {
        System.out.println("update User fail:");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        databaseManager.initializeDatabase();
        User user = new User("test", "test", 20);
        User user2 = new User("asdasd", "asdasd", 20);
        DatabaseUserDao userDao = new DatabaseUserDao();
        UserManager _userManager = new UserManager(userDao);

        IRouteCommand command = new RegisterCommand(_userManager, user.credentials);
        HttpResponse response = command.execute();

        UserData ud = new UserData("tesst", "Im 21 years old", ":D");

        command = new UpdateUserCommand(_userManager, user.credentials.username, user2, ud);
        response = command.execute();
        System.out.println(response.buildResponse());
        assertEquals(response.getStatusCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
    }

    @Test
    public void getUser() {
        System.out.println("get user:");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        databaseManager.initializeDatabase();
        User user = new User("test", "test", 20);
        DatabaseUserDao userDao = new DatabaseUserDao();
        UserManager _userManager = new UserManager(userDao);

        IRouteCommand command = new RegisterCommand(_userManager, user.credentials);
        HttpResponse response = command.execute();

        command = new GetUserCommand(_userManager, user.credentials.username, user);
        response = command.execute();
        System.out.println(response.buildResponse());
        assertEquals(response.getStatusCode(), HttpStatusCode.SUCCESS_200_OK);
    }

    @Test
    public void getUserNotExisting() {
        System.out.println("get user not existing:");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        databaseManager.initializeDatabase();
        User user = new User("test", "test", 20);
        DatabaseUserDao userDao = new DatabaseUserDao();
        UserManager _userManager = new UserManager(userDao);

        IRouteCommand command = new GetUserCommand(_userManager, user.credentials.username, user);
        HttpResponse response = command.execute();
        System.out.println(response.buildResponse());
        assertEquals(response.getStatusCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
    }
}
