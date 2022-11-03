package com.trucker_system.truckersystem.utils;

import com.google.gson.*;
import com.trucker_system.truckersystem.hibernate.TruckHib;
import com.trucker_system.truckersystem.model.Truck;
import com.trucker_system.truckersystem.model.Trucker;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Type;

public class TruckerGsonDeserializer implements JsonDeserializer<Trucker> {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("truckerdb");
    private final TruckHib truckHib = new TruckHib(entityManagerFactory);

    @Override
    public Trucker deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Truck truck = truckHib.getTruckById(jsonObject.getAsJsonObject("truck").get("id").getAsInt());

        return new Trucker(
                jsonObject.get("login").getAsString(),
                jsonObject.get("password").getAsString(),
                jsonObject.get("email").getAsString(),
                jsonObject.get("name").getAsString(),
                jsonObject.get("surname").getAsString(),
                jsonObject.get("phoneNumber").getAsString(),
                jsonObject.get("finishedTrips").getAsInt(),
                truck
        );
    }
}
