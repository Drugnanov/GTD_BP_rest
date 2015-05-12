package GTD.restapi;

import GTD.BL.BLOsoby.PersonAdmin;
import GTD.DL.DLDAO.DAOState;
import GTD.DL.DLEntity.*;
import GTD.restapi.util.exceptions.ResourceExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.List;

/**
 * Created by Drugnanov on 18.1.2015.
 */
@RestController
@RequestMapping("/api/v1/persons")
public class PersonRestController extends RestControllerAbs {

    @Autowired
    private PersonAdmin personAdmin;
    @Autowired
    private DAOState daoState;

    @RequestMapping(value = "/{personId}", method = RequestMethod.GET)
         @ResponseBody
         public Person get(@PathVariable int personId, ServletWebRequest wr, Principal auth) {
        logRequest(wr);
        String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
        Person user = personAdmin.getOsoba(userLogin);
        return personAdmin.getOsoba(personId, user);
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    @ResponseBody
    public Person get(ServletWebRequest wr, Principal auth) {
        logRequest(wr);
        String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
        Person user = personAdmin.getOsoba(userLogin);
        return user;
    }

    @RequestMapping(value = "/{personId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int personId, ServletWebRequest wr, Principal auth) {
        logRequest(wr);
        String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
        Person user = personAdmin.getOsoba(userLogin);
        Person userToDelete = personAdmin.getOsoba(personId, user);
        personAdmin.deletePerson(userToDelete, user);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Person> getAll(Principal auth, ServletWebRequest wr) {
        logRequest(wr);
        String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
        Person user = personAdmin.getOsoba(userLogin);
        return personAdmin.getAllUsers(user);

    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody Person person, ServletWebRequest wr)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, ResourceExistsException {
        logRequest(wr);
        populatePerson(person);
        Person personCreated = personAdmin.addOsoba(person);
        return personCreated;
    }

    @RequestMapping(value = "/{personId}", method = RequestMethod.PUT)
    @ResponseBody
    public Person update(@PathVariable int personId, @RequestBody Person person, ServletWebRequest wr, Principal auth)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        logRequest(wr);
        person.setId(personId);
        String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
        Person userPerson = personAdmin.getOsoba(userLogin);
        personAdmin.updateOsoba(person, userPerson);
        return personAdmin.getOsoba(personId);
    }

    /**
     * Replaces dummy properties from API with their real counterparts from database (where it's needed)
     *
     */
    private void populatePerson(Person person) {
        PersonState state = daoState.getOsobaAktivni();
        person.setState(state);
        person.setRightGenerateToken(true);
    }

    public PersonAdmin getPersonAdmin() {
        return personAdmin;
    }

    public void setPersonAdmin(PersonAdmin personAdmin) {
        this.personAdmin = personAdmin;
    }

    public void setDaoState(DAOState daoState) {
        this.daoState = daoState;
    }

}
