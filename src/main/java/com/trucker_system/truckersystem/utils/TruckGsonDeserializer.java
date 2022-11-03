package com.trucker_system.truckersystem.utils;

import com.google.gson.*;
import com.trucker_system.truckersystem.model.Truck;
import com.trucker_system.truckersystem.model.Trucker;

import java.lang.reflect.Type;

public class TruckGsonDeserializer implements JsonDeserializer<Trucker> {

    @Override
    public Trucker deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        JsonObject truckObj = jsonObject.getAsJsonObject("truck");
        Truck truck = new Truck(
                truckObj.get("id").getAsInt(),
                truckObj.get("brand").getAsString(),
                truckObj.get("model").getAsString(),
                truckObj.get("hp").getAsInt(),
                truckObj.get("engine").getAsDouble(),
                truckObj.get("releaseYear").getAsInt()
        );

        return new Trucker(
            jsonObject.get("login").getAsString(),
            jsonObject.get("password").getAsString(),
            jsonObject.get("email").getAsString(),
            jsonObject.get("name").getAsString(),
            jsonObject.get("surname").getAsString(),
            jsonObject.get("phoneNumber").getAsString(),
            jsonObject.get("finishedTrips").getAsInt(),
            null,
                truck
        );
    }
}
