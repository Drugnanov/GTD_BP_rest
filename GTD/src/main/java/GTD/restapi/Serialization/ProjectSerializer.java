/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.restapi.Serialization;

import GTD.DL.DLEntity.Project;
import GTD.restapi.ApiConstants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 *
 * @author slama
 */
public class ProjectSerializer extends JsonSerializer<Project> {

    @Override
    public void serialize(Project p, JsonGenerator jg, SerializerProvider sp) throws IOException,
            JsonProcessingException {
        jg.writeStartObject();
        jg.writeNumberField(ApiConstants.PROJECT_ID, p.getId());
        jg.writeStringField(ApiConstants.PROJECT_TITLE, p.getTitle());
        jg.writeEndObject();
    }

}
