/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.restapi.Serialization;

import GTD.DL.DLEntity.Note;
import GTD.restapi.ApiConstants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 *
 * @author slama
 */
public class NoteDeserializer extends JsonDeserializer<Note> {

    @Override
    public Note deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        Note note = new Note();
        JsonNode node = jp.getCodec().readTree(jp);
        if (node.has(ApiConstants.NOTE_ID)) {
            int id = node.get(ApiConstants.NOTE_ID).asInt();
            note.setId(id);
        }

        return note;
    }

}
