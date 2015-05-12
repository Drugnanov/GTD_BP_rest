/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.restapi.Serialization;

import GTD.DL.DLEntity.Task;
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
public class TaskDeserializer extends JsonDeserializer<Task> {

    @Override
    public Task deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        Task task = new Task();
        JsonNode node = jp.getCodec().readTree(jp);
        if (node.has(ApiConstants.TASK_ID)) {
            int id = node.get(ApiConstants.TASK_ID).asInt();
            task.setId(id);
        }
        if (node.has(ApiConstants.TASK_TITLE)) {
            String title = node.get(ApiConstants.TASK_TITLE).asText();
            task.setTitle(title);
        }
        return task;
    }

}
