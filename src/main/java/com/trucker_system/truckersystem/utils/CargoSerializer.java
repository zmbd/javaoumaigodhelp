package com.trucker_system.truckersystem.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.trucker_system.truckersystem.model.Cargo;

import java.lang.reflect.Type;

public class CargoSerializer implements JsonSerializer<Cargo> {
    @Override
    public JsonElement serialize(Cargo cargo, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", cargo.getId());
        jsonObject.addProperty("startDest", cargo.getStartDestination());
        jsonObject.addProperty("finalDest", cargo.getFinalDestination());
        jsonObject.addProperty("assignedAt", String.valueOf(cargo.getAssignedAt()));
        jsonObject.addProperty("deliverUntil", String.valueOf(cargo.getDeliverUntil()));
        jsonObject.addProperty("cargo", cargo.getCargo());
        jsonObject.addProperty("finished", cargo.isFinished());
        jsonObject.addProperty("truckerId", cargo.getTrucker() == null ? null : cargo.getTrucker().getId());

        return jsonObject;
    }
}
