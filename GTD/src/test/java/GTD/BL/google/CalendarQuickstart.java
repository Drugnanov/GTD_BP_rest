package GTD.BL.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.*;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by Drugnanov
 */
public class CalendarQuickstart {

  /** Application name. */
  private static final String APPLICATION_NAME =
      "Calendar API Java Quickstart";

  /** Directory to store user credentials. */
  private static final java.io.File DATA_STORE_DIR = new java.io.File(
      System.getProperty("user.home"), ".credentials/calendar-api-quickstart");

  /** Global instance of the {@link FileDataStoreFactory}. */
  private static FileDataStoreFactory DATA_STORE_FACTORY;

  /** Global instance of the JSON factory. */
  private static final JsonFactory JSON_FACTORY =
      JacksonFactory.getDefaultInstance();

  /** Global instance of the HTTP transport. */
  private static HttpTransport HTTP_TRANSPORT;

  /** Global instance of the scopes required by this quickstart. */
  private static final List<String> SCOPES =
      Arrays.asList(CalendarScopes.CALENDAR);

  static {
    try {
      HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
      DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
    }
    catch (Throwable t) {
      t.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Creates an authorized Credential object.
   *
   * @return an authorized Credential object.
   * @throws IOException
   */
  public static Credential authorize() throws IOException {
    // Load client secrets.
    //File file = new File("client_secret.json");
    //FileInputStream fis = new FileInputStream(file);

    //Path path = Path.

    InputStream in =
        CalendarQuickstart.class.getResourceAsStream("client_secret.json");
    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow =
        new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(DATA_STORE_FACTORY)
            .setAccessType("offline")
            .build();
    Credential credential = new AuthorizationCodeInstalledApp(
        flow, new LocalServerReceiver()).authorize("user");
    System.out.println(
        "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
    return credential;
  }

  /**
   * Build and return an authorized Calendar client service.
   *
   * @return an authorized Calendar client service
   * @throws IOException
   * @param credential
   */
  public static com.google.api.services.calendar.Calendar
  getCalendarService(GoogleCredential credential) throws IOException {
    //Credential credential = authorize();
    return new com.google.api.services.calendar.Calendar.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, credential)
        .setApplicationName(APPLICATION_NAME)
        .build();
  }

  private static void addCalendarEvent(com.google.api.services.calendar.Calendar service) throws IOException {
// Create and initialize a new event
    Event event = new Event();
    event.setSummary("Appointment");
    event.setLocation("Somewhere");

    ArrayList<EventAttendee> attendees = new ArrayList<EventAttendee>();
    attendees.add(new EventAttendee().setEmail("asdfEmail@gmail.com"));
// ...
    event.setAttendees(attendees);

    Date startDate = new Date();
    Date endDate = new Date(startDate.getTime() + 3600000);
    DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
    event.setStart(new EventDateTime().setDateTime(start));
    DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
    event.setEnd(new EventDateTime().setDateTime(end));

// Insert the new event
    Event createdEvent = service.events().insert("primary", event).execute();

    System.out.println(createdEvent.getId());
  }

  /**
   * authentication:
   * https://developers.google.com/identity/protocols/OAuth2WebServer
   * https://developers.google.com/identity/protocols/OAuth2
   * https://developers.google.com/api-client-library/python/auth/installed-app
   * <p/>
   * nastaveni uctu
   * https://myaccount.google.com/
   * <p/>
   * opravneni uctu
   * https://security.google.com/settings/security/permissions?pli=1
   * <p/>
   * Calendar:
   * *********************************************************************
   * https://developers.google.com/google-apps/calendar/auth
   * java spi client:
   * https://developers.google.com/api-client-library/java/apis/calendar/v3
   *
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    // Build a new authorized API client service.
    // Note: Do not confuse this class with the
    //   com.google.api.services.calendar.model.Calendar class.
    String accessToken = "ya29.YQHKo4JWONSPL-bW5OtrPZyXsGGYYzlMuHHrMSASZhNBW2b5eNf8u54WXz2fVVuJoobD02zrdmzT1g";
    GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
    com.google.api.services.calendar.Calendar service =
        getCalendarService(credential);

    addCalendarEvent(service);

    // List the next 10 events from the primary calendar.
    DateTime now = new DateTime(System.currentTimeMillis() - 365 * 24 * 60 * 60 * 1000);
    Events events = service.events().list("primary")
        .setMaxResults(10)
        .setTimeMin(now)
        .setOrderBy("startTime")
        .setSingleEvents(true)
        .execute();
    List<Event> items = events.getItems();
    if (items.size() == 0) {
      System.out.println("No upcoming events found.");
    }
    else {
      System.out.println("Upcoming events");
      for (Event event : items) {
        DateTime start = event.getStart().getDateTime();
        if (start == null) {
          start = event.getStart().getDate();
        }
        System.out.printf("%s (%s)\n", event.getSummary(), start);
      }
    }
    System.out.println("aaaaaaa");
  }
}
