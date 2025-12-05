package hotel;

public class UserSession {
    private static UserSession instance;
    private String username;
    private String password;
    private UserType userType;

    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void login(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public void logout() {
        this.username = null;
        this.password = null;
        this.userType = null;
    }

    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    public boolean isUserType(UserType type) {
        return userType == type;
    }
}
