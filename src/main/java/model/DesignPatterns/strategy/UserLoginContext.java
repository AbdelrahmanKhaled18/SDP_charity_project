package model.DesignPatterns.strategy;

import model.Person;
import model.Staff;
import model.Volunteer;

public class UserLoginContext {
    private static UserLoginContext instance;
    private ILogin loginStrategyRef;
    private Person loggedInUser;

    public static UserLoginContext getInstance() {
        if (instance == null) {
            instance = new UserLoginContext();
        }
        return instance;
    }

    private UserLoginContext() {
    }

    public void setLoginStrategyRef(ILogin loginStrategyRef) {
        this.loginStrategyRef = loginStrategyRef;
    }

    public Person getLoggedInUser() {
        return loggedInUser;
    }

    public String authenticate() {
        loggedInUser = loginStrategyRef.login();
        if (loggedInUser instanceof Volunteer) {
            return "volunteer";
        }
        if (loggedInUser instanceof Staff) {
            return "staff";
        }
        return "failed";
    }
}
