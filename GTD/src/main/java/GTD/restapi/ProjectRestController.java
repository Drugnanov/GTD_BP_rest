/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.restapi;

/**
 *
 * @author skvarla
 */
import GTD.BL.BLAktivity.ProjectAdmin;
import GTD.BL.BLOsoby.PersonAdmin;
import GTD.DL.DLEntity.Person;
import GTD.DL.DLEntity.Project;
import java.security.Principal;

import java.util.List;
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

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectRestController extends RestControllerAbs {

    private PersonAdmin personAdmin;
    private ProjectAdmin projectAdmin;

    @Autowired
    public void setPersonAdmin(PersonAdmin personAdmin) {
        this.personAdmin = personAdmin;
    }

    @Autowired
    public void setProjectAdmin(ProjectAdmin projectAdmin) {
        this.projectAdmin = projectAdmin;
    }

    public ProjectRestController() {
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Project get(@PathVariable int id, ServletWebRequest wr, Principal auth) {
        logRequest(wr);
        String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
        Person user = personAdmin.getOsoba(userLogin);
        return projectAdmin.getProjekt(id, user);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Project> getAll(ServletWebRequest wr, Principal auth) {
        logRequest(wr);
        String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
        Person user = personAdmin.getOsoba(userLogin);
        return projectAdmin.getProjektyOsoby(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id, ServletWebRequest wr, Principal auth) {
        logRequest(wr);
        String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
        Person user = personAdmin.getOsoba(userLogin);
        Project p = projectAdmin.getProjekt(id, user);
        projectAdmin.deleteProjekt(p, user);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Project create(@RequestBody Project prj, ServletWebRequest wr, Principal auth) {
        logRequest(wr);
        String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
        Person user = personAdmin.getOsoba(userLogin);
        projectAdmin.addProjekt(prj, null, user); // TODO steklsim what about projects's activity?

        return prj;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Project update(@PathVariable int id, @RequestBody Project prj, ServletWebRequest wr, Principal auth) {
        logRequest(wr);
        prj.setId(id);
        String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
        Person user = personAdmin.getOsoba(userLogin);
        projectAdmin.updateProjekt(prj, user);
        return projectAdmin.getProjekt(id, user);
    }
}
