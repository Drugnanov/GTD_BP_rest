/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.restapi.Serialization;

import GTD.DL.DLEntity.Task;
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
public class TaskSerializer extends JsonSerializer<Task> {

    @Override
    public void serialize(Task t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeStartObject();
        jg.writeNumberField(ApiConstants.TASK_ID, t.getId());
        jg.writeStringField(ApiConstants.TASK_TITLE, t.getTitle());
        jg.writeEndObject();
    }

}
