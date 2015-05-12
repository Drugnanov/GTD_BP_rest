package GTD.BL.restapi;

import GTD.restapi.config.ApplicationConfig;
import GTD.restapi.config.SecurityConfig;
import GTD.restapi.facebook.FacebookInput;
import GTD.restapi.util.filters.AuthenticationTokenProcessingFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

/**
 * Created by Drugnanov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ApplicationConfig.class, SecurityConfig.class})
public class TaskRestControllerTest {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private AuthenticationTokenProcessingFilter authenticationFilter;
    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(authenticationFilter, springSecurityFilterChain).build();
    }

    @Test
    public void doSomething() throws Exception {
        //RequestBuilder requestBuilder = MockMvcRequestBuilders.;
        FacebookInput input = new FacebookInput();
        input.setAccessToken("CAAWtU8h0CD8BAO1J1ZC1OxoWus8wv6xcPXZAbjYChP1Kg5un2ArsiFUUEP4xqEwYmr401wxzZCiZAlOBgwkFHwyuh3H8eoKekcLNzH2LsrwXJzZCY7jJ0ca71MRkiZAvf5p4b6TY44RpIX155CtekeZAymFwrmgS1wCLrzXJiE3Y8Hf6fTxk3TDuTYLvJwKvyFfwXaHKnaMqfn1eKDvmZAp0");
        input.setUserMessage("Nejakej user comment");

        //TODO: tady by to chtelo nejak prechroustat pres reflexi
        StringBuilder inputJson = new StringBuilder();
        inputJson.append("{\"accessToken\":\"").append(input.getAccessToken()).append("\",");
        inputJson.append("\"userMessage\":\"").append(input.getUserMessage()).append("\"}");

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tasks/{id}/facebookPublish"
                        , 1l)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token", "9bd12abbb9d3dc2bba34732d27a3965f")
                        .content(inputJson.toString())
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                //.requestAttr("token", "149679a753bf37a0b4150744b1e2a417")

        ).andReturn();
        System.out.println("response status  " + result.getResponse().getStatus());
    }


}
