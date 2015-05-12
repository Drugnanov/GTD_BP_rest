package GTD.restapi.google;

/**
 * Created by Drugnanov
 */
public class GoogleInput {

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
    return "GoogleInput{" +
        "accessToken='" + accessToken + '\'' +
        ", userMessage='" + userMessage + '\'' +
        '}';
  }
}
