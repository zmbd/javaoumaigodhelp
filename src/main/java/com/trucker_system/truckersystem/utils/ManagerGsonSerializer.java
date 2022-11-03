package com.trucker_system.truckersystem.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.trucker_system.truckersystem.model.Manager;

import java.lang.reflect.Type;

public class ManagerGsonSerializer implements JsonSerializer<Manager> {

    @Override
    public JsonElement serialize(Manager manager, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("login", manager.getLogin());
        jsonObject.addProperty("password", manager.getPassword());
        jsonObject.addProperty("email", manager.getEmail());
        jsonObject.addProperty("name", manager.getName());
        jsonObject.addProperty("surname", manager.getSurname());
        jsonObject.addProperty("phoneNumber", manager.getPhoneNumber());
        jsonObject.addProperty("admin", manager.isAdmin());

        return jsonObject;
    }
}
