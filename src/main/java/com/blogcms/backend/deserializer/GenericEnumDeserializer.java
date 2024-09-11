package com.blogcms.backend.deserializer;

import com.blogcms.backend.exception.InvalidEnumValueException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class GenericEnumDeserializer<T extends Enum<T> & EnumValue> extends JsonDeserializer<T> {

    private final Class<T> enumType;

    public GenericEnumDeserializer(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText();

        for (T constant : enumType.getEnumConstants()) {
            if (constant.getValue().equalsIgnoreCase(value)) {
                return constant;
            }
        }

        throw new InvalidEnumValueException("Invalid value: " + value + ". Please ensure that you use valid values for " + enumType.getSimpleName());

    }
}
