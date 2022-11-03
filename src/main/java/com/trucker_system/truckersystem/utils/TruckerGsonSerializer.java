package com.trucker_system.truckersystem.utils;

import com.google.gson.*;
import com.trucker_system.truckersystem.model.Trucker;

import java.lang.reflect.Type;

public class TruckerGsonSerializer implements JsonSerializer<Trucker> {

    @Override
    public JsonElement serialize(Trucker trucker, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject truckerJsonObject = new JsonObject();
        truckerJsonObject.addProperty("login", trucker.getLogin());
        truckerJsonObject.addProperty("password", trucker.getPassword());
        truckerJsonObject.addProperty("email", trucker.getEmail());
        truckerJsonObject.addProperty("name", trucker.getName());
        truckerJsonObject.addProperty("surname", trucker.getSurname());
        truckerJsonObject.addProperty("finishedTrips", trucker.getFinishedTrips());
        truckerJsonObject.addProperty("truck", trucker.getTruck().toString());
        truckerJsonObject.addProperty("cargos", trucker.getCargosList().toString());
//        JsonObject truckJsonObject = truckerJsonObject.getAsJsonObject("truck");
//        truckJsonObject.addProperty("id", trucker.getTruck().getId());

        return truckerJsonObject;
    }
}