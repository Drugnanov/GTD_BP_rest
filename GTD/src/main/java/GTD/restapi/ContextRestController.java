/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.restapi;

import GTD.BL.BLFiltry.ContextAdmin;
import GTD.BL.BLOsoby.PersonAdmin;
import GTD.DL.DLEntity.Context;
import GTD.DL.DLEntity.Person;
import java.security.Principal;

import java.util.List;

import GTD.restapi.util.exceptions.ObjectAlreadyExistsException;
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
@RequestMapping("/api/v1/contexts")
public class ContextRestController extends RestControllerAbs {

    private ContextAdmin contextAdmin;
    private PersonAdmin personAdmin;

    @Autowired
    public void setContextAdmin(ContextAdmin contextAdmin) {
        this.contextAdmin = contextAdmin;
    }

    @Autowired
    public void setPersonAdmin(PersonAdmin personAdmin) {
        this.personAdmin = personAdmin;
    }

    public ContextRestController() {

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Context get(@PathVariable int id, ServletWebRequest wr, Principal auth) {
        logRequest(wr);
        String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
        Person user = personAdmin.getOsoba(userLogin);
        return contextAdmin.getKontext(id, user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id, ServletWebRequest wr, Principal auth) {
        logRequest(wr);
        String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
        Person user = personAdmin.getOsoba(userLogin);
        Context c = contextAdmin.getKontext(id, user);
        contextAdmin.deleteKontext(c, user);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Context> getAll(ServletWebRequest wr, Principal auth) {
        logRequest(wr);
        String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
        Person user = personAdmin.getOsoba(userLogin);
        return contextAdmin.getKontextyOsoby(user);
    }

    // TODO steklsim getContextsByUser()
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Context create(@RequestBody Context context, ServletWebRequest wr, Principal auth)
        throws ObjectAlreadyExistsException {
        logRequest(wr);
        String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
        Person user = personAdmin.getOsoba(userLogin);
        populateContext(context);

        contextAdmin.addContext(context, user);

        return context;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Context update(@PathVariable int id, @RequestBody Context context, ServletWebRequest wr, Principal auth) {
        logRequest(wr);
        context.setId(id);
        String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
        Person user = personAdmin.getOsoba(userLogin);
        populateContext(context);
        contextAdmin.updateKontext(context, user);

        return contextAdmin.getKontext(id, user);
    }

    /**
     * Replaces dummy properties from API with their real counterparts from database (where it's needed)
     *
     * @param task
     * @param user logged-in user
     */
    private void populateContext(Context context) {
    }
}
