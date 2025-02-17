package Functions;

public class UserStartSession {
    private static UserStartSession instance;
    private String loggedInUser;

    private UserStartSession() {}

    public static UserStartSession getInstance() {
        if (instance == null) {
            instance = new UserStartSession();
        }
        return instance;
    }

    public void setLoggedInUser(String username) {
        this.loggedInUser = username;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void logout() {
        this.loggedInUser = null;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }
}
