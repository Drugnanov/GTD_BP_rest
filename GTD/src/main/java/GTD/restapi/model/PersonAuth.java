package GTD.restapi.model;

/**
 * Created by Drugnanov on 14.12.2014.
 */
public class PersonAuth {

    private String userName;
    private String token;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
