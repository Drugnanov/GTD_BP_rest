package GTD.restapi.facebook;

import GTD.DL.DLEntity.Task;
import GTD.restapi.util.exceptions.GtdApiException;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Drugnanov
 */
public class FacebookPublisher {

  private final FacebookClient facebookClient;
  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  public FacebookPublisher(FacebookClient facebookClient) {
    this.facebookClient = facebookClient;
  }

  public FacebookPublisher(String accessToken) {
    this.facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_2);
  }

  public String publishSimpleMessage(String msgBody) {
    FacebookType publishMessageResponse = facebookClient
        .publish("me/feed", FacebookType.class, Parameter.with("message", msgBody));
    return publishMessageResponse.getId();
  }

  public String publishTask(Task task, String userMessage) throws GtdApiException {
    if (task == null)
      throw new IllegalArgumentException("No task to publish");

    String msgText = "GTD task report:";
    String description = (task.getDescription() == null) ? "" : "\n Description:" + task.getDescription();
    msgText += "\n Title: "+ task.getTitle() + description;
    msgText += "\n State: " + task.getState().getDescription();
    if (task.getNotes().size() >0){
      String note = task.getNotes().get(0).getText();
      String noteText = (note == null || note.isEmpty()) ? "" : "\n Note:" + note;
      msgText += noteText;
    }
    if (userMessage != null && userMessage.trim().length() > 0)
      msgText += "\n" + userMessage;

    logger.info("publishing task with id:" + task.getId() + " to facebook with message " + msgText + ".");

    String id = null;
    try {
      id = publishSimpleMessage(msgText);
    }
    catch (Exception e) {
      throw new GtdApiException(
          "Something bad with publish with id:" + task.getId() + " to facebook with message " + msgText + ".");
    }
    //maybe? || id.isEmpty()
    if (id == null) {
      throw new GtdApiException(
          "Something bad with publish with id:" + task.getId() + " to facebook with message " + msgText + ".");
    }
    return id;
  }

  public static boolean taskAlreadyPublished(Task task) {
    if(task == null)
      throw new IllegalArgumentException("task is null!");

    if(task.getFacebookPublishDate() == null)
      return false;

    Calendar now = Calendar.getInstance();

    Calendar published = Calendar.getInstance();
    published.setTime(task.getFacebookPublishDate());
    //pokud uplynulo alespon 24 hodin
    return (now.getTimeInMillis() - published.getTimeInMillis()) < (24*60*60*1000);
  }
}
