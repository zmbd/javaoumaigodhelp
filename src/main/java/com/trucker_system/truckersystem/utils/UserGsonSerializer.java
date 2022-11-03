package com.trucker_system.truckersystem.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.trucker_system.truckersystem.model.User;

import java.lang.reflect.Type;

public class UserGsonSerializer implements JsonSerializer<User> {

    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", user.getId());
        jsonObject.addProperty("dtype", user.getDtype());
        jsonObject.addProperty("login", user.getLogin());
        jsonObject.addProperty("password", user.getPassword());
        jsonObject.addProperty("email", user.getEmail());
        jsonObject.addProperty("name", user.getName());
        jsonObject.addProperty("surname", user.getSurname());
        jsonObject.addProperty("phoneNumber", user.getPhoneNumber());

        return jsonObject;
    }
}
