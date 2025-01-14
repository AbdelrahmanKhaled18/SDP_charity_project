package model.DesignPatterns;

public class UserLoginContext {
    private ILogin loginStrategyRef;

    public UserLoginContext(ILogin loginStrategyRef) {
        this.loginStrategyRef = loginStrategyRef;
    }


    public void setLoginStrategyRef(ILogin loginStrategyRef) {
        this.loginStrategyRef = loginStrategyRef;
    }


    public String authenticate() {
        return loginStrategyRef.login();
    }

}
