/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.restapi.Serialization;

import GTD.DL.DLEntity.Note;
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
public class NoteSerializer extends JsonSerializer<Note> {

    @Override
    public void serialize(Note n, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeStartObject();
        jg.writeNumberField(ApiConstants.NOTE_ID, n.getId());
//      jg.writeNumberField(ApiConstants.NOTE_ORDER, n.getOrder());
        jg.writeEndObject();
    }

}
