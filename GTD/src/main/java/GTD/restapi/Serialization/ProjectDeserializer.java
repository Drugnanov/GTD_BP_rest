/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.restapi.Serialization;

import GTD.DL.DLEntity.Project;
import GTD.restapi.ApiConstants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

/**
 *
 * @author slama
 */
public class ProjectDeserializer extends JsonDeserializer<Project> {

    @Override
    public Project deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        Project project = new Project();
        JsonNode node = jp.getCodec().readTree(jp);
        if (node.has(ApiConstants.PROJECT_ID)) {
            int id = node.get(ApiConstants.PROJECT_ID).asInt();
            project.setId(id);
        }
        if (node.has(ApiConstants.PROJECT_TITLE)) {
            String title = node.get(ApiConstants.PROJECT_TITLE).asText();
            project.setTitle(title);
        }

        return project;
    }

}
