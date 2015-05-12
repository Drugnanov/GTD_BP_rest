package GTD.restapi.facebook;

/**
 * Created by Drugnanov
 */
public class FacebookInput {
    /** token pro facebook */
    private String accessToken;
    /** zprava uzivatele */
    private String userMessage;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FacebookInput{");
        sb.append("accessToken='").append(accessToken).append('\'');
        sb.append(", userMessage='").append(userMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
