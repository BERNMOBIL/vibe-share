package ch.bernmobil.vibe.shared.converter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.SQLException;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.postgresql.util.PGobject;

public class JsonObjectConverter implements AttributeConverter<Object, JsonObject> {

    @Override
    public JsonObject convertToDatabaseColumn(Object attribute) {
        if(attribute instanceof PGobject && ((PGobject)attribute).getType().equals("json")) {
            JsonParser parser = new JsonParser();
            return parser.parse(((PGobject) attribute).getValue()).getAsJsonObject();
        }
        return null;
    }

    @Override
    public Object convertToEntityAttribute(JsonObject dbData) {
        try {
            PGobject out = new PGobject();
            out.setType("json");
            out.setValue(dbData.toString());
            return out;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Unable to serialize JsonObject", e);
        }
    }
}
