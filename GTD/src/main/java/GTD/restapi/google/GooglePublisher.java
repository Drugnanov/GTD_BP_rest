package GTD.restapi.google;

import GTD.DL.DLEntity.Task;
import GTD.restapi.util.exceptions.GtdApiException;
import GTD.restapi.util.exceptions.GtdGoogleTaskAlreadyReportedException;
import GTD.restapi.util.exceptions.GtdPublishInvalidTokenException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.TimeZone;

/**
 * Created by Drugnanov
 */
public class GooglePublisher {

  private final GoogleCredential credential;
  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  private  final String UNAUTHORIZED = "401 Unauthorized";
  private  final String FORBIDDEN = "403 Forbidden";

  /** Global instance of the JSON factory. */
  private static final JsonFactory JSON_FACTORY =
      JacksonFactory.getDefaultInstance();

  private static final String APPLICATION_NAME =
      "GTD-BP-Android";

  /** Global instance of the HTTP transport. */
  private static HttpTransport HTTP_TRANSPORT;

  public GooglePublisher(GoogleCredential credential) {
    this.credential = credential;
  }

  public GooglePublisher(String accessToken) {
    credential = new GoogleCredential().setAccessToken(accessToken);
  }

  public Event publishEvent(Event event) throws IOException, GeneralSecurityException {
    com.google.api.services.calendar.Calendar service = getCalendarService(credential);
    return service.events().insert("primary", event).execute();
  }

  public String publishTask(Task task, String userMessage) throws GtdApiException,
      GtdGoogleTaskAlreadyReportedException, GtdPublishInvalidTokenException {
    if (task == null)
      throw new IllegalArgumentException("No task to publish");

    //check if task was already published on google.
    if (GooglePublisher.taskAlreadyPublished(task)) {
      throw new GtdGoogleTaskAlreadyReportedException("Time from last post of the same task is too short.");
    }

    Event event = new Event();
    event.setSummary(task.getTitle());
    String description = "";
    description += (userMessage != null) ? userMessage +"\n" : "";
    description += (task.getDescription() != null) ? task.getTitle() +"\n": "";
    if (task.getNotes().size() > 0) {
      description += (task.getNotes().get(0).getText() != null) ? task.getNotes().get(0).getText() : "";
    }
    event.setDescription(description);

    ArrayList<EventAttendee> attendees = new ArrayList<EventAttendee>();
//    attendees.add(new EventAttendee().setEmail("asdfEmail@gmail.com"));
// ...
    event.setAttendees(attendees);

    DateTime start = new DateTime(task.getCalendar().getFrom(), TimeZone.getTimeZone("UTC"));
    DateTime end = new DateTime(task.getCalendar().getTo(), TimeZone.getTimeZone("UTC"));
    event.setStart(new EventDateTime().setDateTime(start));
    event.setEnd(new EventDateTime().setDateTime(end));

    logger.info("publishing task with id:" + task.getId() + " to google");

    String id = null;
    try {
      // Insert the new event
      Event createdEvent = publishEvent(event);
      id = createdEvent.getId();
    }
    catch (Exception e) {
      if (UNAUTHORIZED.equals(e.getMessage()) || e.getMessage().startsWith(FORBIDDEN)){
        throw new GtdPublishInvalidTokenException("Calling google api failed due to invalid token.");
      }
      throw new GtdApiException(
          "Something bad with publish with id:" + task.getId() + " to google.");
    }
    //maybe? || id.isEmpty()
    if (id == null) {
      throw new GtdApiException(
          "Something bad with publish with id:" + task.getId() + " to google.");
    }
    return id;
  }

  private static boolean taskAlreadyPublished(Task task) {
    String id = task.getGoogleStringID();
    if (id == null || id.isEmpty()) {
      return false;
    }
    else {
      return true;
    }
  }

  /**
   * Build and return an authorized Calendar client service.
   *
   * @param credential
   * @return an authorized Calendar client service
   * @throws java.io.IOException
   */
  public static com.google.api.services.calendar.Calendar
  getCalendarService(GoogleCredential credential) throws IOException, GeneralSecurityException {
    //Credential credential = authorize();
    HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    return new com.google.api.services.calendar.Calendar.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, credential)
        .setApplicationName(APPLICATION_NAME)
        .build();
  }
}
