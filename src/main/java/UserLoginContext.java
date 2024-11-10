public class UserLoginContext {
    private String email;
    private String password;
    private ILogin loginStrategyRef;

    public UserLoginContext(String email, String password) {
        this.email = email;
        this.password = password;
        loginStrategyRef = new EmailLogin();
    }

    public boolean authenticate(){
        return loginStrategyRef.login();
    }

}
