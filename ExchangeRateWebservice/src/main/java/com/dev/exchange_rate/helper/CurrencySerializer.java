package com.dev.exchange_rate.helper;

import com.dev.exchange_rate.domain.Currency;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Locale;

public class CurrencySerializer implements JsonSerializer<Currency>, JsonDeserializer<Currency> {

    @Override
    public JsonElement serialize(Currency currency, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(currency.name());
    }

    @Override
    public Currency deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String enumValue = jsonElement.getAsString().toUpperCase(Locale.ROOT);
        try {
            return Currency.valueOf(enumValue);
        } catch (IllegalArgumentException e) {
            throw new JsonParseException("Invalid enum value: " + enumValue);
        }
    }
}