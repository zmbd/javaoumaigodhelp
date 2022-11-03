package com.trucker_system.truckersystem.utils;

import com.google.gson.*;
import com.trucker_system.truckersystem.hibernate.UserHib;
import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.model.Trucker;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CargoDeserializer implements JsonDeserializer<Cargo> {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("truckerdb");
    private final UserHib userHib = new UserHib(entityManagerFactory);
    @Override
    public Cargo deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Trucker trucker = (Trucker) userHib.getUserById(jsonObject.get("truckerId").getAsInt());

        LocalDate assignedAt = LocalDate.parse(jsonObject.get("assignedAt").getAsString());
        LocalDate deliverUntil = LocalDate.parse(jsonObject.get("deliverUntil").getAsString());

        return new Cargo(
            jsonObject.get("client").getAsString(),
            jsonObject.get("startDest").getAsString(),
            jsonObject.get("finalDest").getAsString(),
            assignedAt,
            deliverUntil,
            jsonObject.get("cargo").getAsString(),
            jsonObject.get("isFinished").getAsBoolean(),
            trucker
        );
    }
}
