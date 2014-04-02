package com.ardoq;

import com.google.gson.*;

import javax.xml.bind.DatatypeConverter;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Iso8601Adapter implements JsonDeserializer<Date>, JsonSerializer<Date> {
    @Override
    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Calendar calendar = DatatypeConverter.parseDateTime(jsonElement.getAsString());
        return calendar.getTime();
    }

    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        String s = DatatypeConverter.printDateTime(gregorianCalendar);
        return new JsonPrimitive(s);
    }
}
