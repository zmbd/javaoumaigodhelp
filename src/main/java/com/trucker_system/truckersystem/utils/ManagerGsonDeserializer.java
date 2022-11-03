package com.trucker_system.truckersystem.utils;

import com.google.gson.*;
import com.trucker_system.truckersystem.model.Manager;

import java.lang.reflect.Type;

public class ManagerGsonDeserializer implements JsonDeserializer<Manager> {
    @Override
    public Manager deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return new Manager(
                jsonObject.get("login").getAsString(),
                jsonObject.get("password").getAsString(),
                jsonObject.get("email").getAsString(),
                jsonObject.get("name").getAsString(),
                jsonObject.get("surname").getAsString(),
                jsonObject.get("phoneNumber").getAsString(),
                jsonObject.get("isAdmin").getAsBoolean()
        );
    }
}
