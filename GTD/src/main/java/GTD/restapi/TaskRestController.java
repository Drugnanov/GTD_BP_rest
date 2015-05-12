/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.restapi;

import GTD.BL.BLAktivity.TaskAdmin;
import GTD.BL.BLOsoby.PersonAdmin;
import GTD.DL.DLEntity.Interval;
import GTD.DL.DLEntity.Person;
import GTD.DL.DLEntity.Task;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

import GTD.restapi.facebook.FacebookInput;
import GTD.restapi.facebook.FacebookPublisher;
import GTD.restapi.google.GoogleInput;
import GTD.restapi.google.GooglePublisher;
import GTD.restapi.util.exceptions.GtdApiException;
import GTD.restapi.util.exceptions.GtdFacebookTaskAlreadyReportedException;
import GTD.restapi.util.exceptions.GtdGoogleTaskAlreadyReportedException;
import GTD.restapi.util.exceptions.GtdPublishInvalidTokenException;
import com.google.api.client.util.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author slama
 */
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskRestController extends RestControllerAbs {

  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  private TaskAdmin taskAdmin;
  private PersonAdmin personAdmin;

  @Autowired
  public void setTaskAdmin(TaskAdmin taskAdmin) {
    this.taskAdmin = taskAdmin;
  }

  @Autowired
  public void setPersonAdmin(PersonAdmin personAdmin) {
    this.personAdmin = personAdmin;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public Task get(@PathVariable int id, ServletWebRequest wr, Principal auth) {
    logRequest(wr);
    String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
    Person user = personAdmin.getOsoba(userLogin);
    return taskAdmin.getUkol(id, user);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable int id, ServletWebRequest wr, Principal auth) {
    logRequest(wr);
    String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
    Person user = personAdmin.getOsoba(userLogin);
    Task t = taskAdmin.getUkol(id, user);
    taskAdmin.deleteUkol(t, user);
  }

  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  public List<Task> getAll(Principal auth, ServletWebRequest wr) {
    logger.info("getAll " + auth + ", " + wr);
    logRequest(wr);
    String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
    Person user = personAdmin.getOsoba(userLogin);
    logger.info("allTasks: " + taskAdmin.getUkolyOsoby(user));
    return taskAdmin.getUkolyOsoby(user);
  }

  @RequestMapping(method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public Task create(@RequestBody Task task, Principal auth, ServletWebRequest wr) {
    logRequest(wr);
    String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
    Person user = personAdmin.getOsoba(userLogin);
    taskAdmin.addUkol(task, user, null);
    return task;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  @ResponseBody
  public Task update(@PathVariable int id, @RequestBody Task task, ServletWebRequest wr, Principal auth) {
    logRequest(wr);
    task.setId(id);
    String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
    Person testUser = personAdmin.getOsoba(userLogin);
    taskAdmin.updateUkol(task, testUser);
    return taskAdmin.getUkol(id, testUser);
  }

  /**
   * z tasku vytvorit text message
   */
  @RequestMapping(value = "/{id}/facebookPublish", method = RequestMethod.POST)
  public void publishTaskToFacebook(@PathVariable int id, @RequestBody FacebookInput input, ServletWebRequest wr,
                                    Principal auth)
      throws GtdFacebookTaskAlreadyReportedException, GtdApiException {
    logger.info("publishTaskToFacebook " + id + ", " + wr + ", " + auth);

    String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
    Person user = personAdmin.getOsoba(userLogin);
    Task taskToPublish = taskAdmin.getUkol(id, user);
    //kontrola zda byl dnes uz task publikovan na facebook
    if (FacebookPublisher.taskAlreadyPublished(taskToPublish)) {
      throw new GtdFacebookTaskAlreadyReportedException("Time from last post of the same task is too short.");
    }
    FacebookPublisher fbPublisher = new FacebookPublisher(input.getAccessToken());
    String facebookId = fbPublisher.publishTask(taskToPublish, input.getUserMessage());
    taskToPublish.setFacebookPublishDate(new Date());
    taskToPublish.setFacebookStringID(facebookId);
    taskAdmin.updateUkol(taskToPublish, user);
  }

  /**
   * z tasku vytvorit text message
   */
  @RequestMapping(value = "/{id}/googlePublish", method = RequestMethod.POST)
  public void publishTaskToGoogle(@PathVariable int id, @RequestBody GoogleInput input, ServletWebRequest wr,
                                  Principal auth)
      throws GtdApiException, IOException, GtdGoogleTaskAlreadyReportedException, GtdPublishInvalidTokenException {
    logger.info("publishTaskToGoogle " + id + ", " + wr + ", " + auth);

    String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
    Person user = personAdmin.getOsoba(userLogin);
    Task taskToPublish = taskAdmin.getUkol(id, user);
    if (!taskAdmin.checkInterval(taskToPublish)) {
      throw new IOException("Task with id " + taskToPublish.getId() + " is not in state calendar!");
    }
    GooglePublisher googlePublisher = new GooglePublisher(input.getAccessToken());
    String googleId = googlePublisher.publishTask(taskToPublish, input.getUserMessage());
    taskToPublish.setGooglePublishDate(new Date());
    taskToPublish.setGoogleStringID(googleId);
    taskAdmin.updateUkol(taskToPublish, user);
  }
}
