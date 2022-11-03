//package com.trucker_system.truckersystem.utils;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonSerializationContext;
//import com.google.gson.JsonSerializer;
//import com.trucker_system.truckersystem.model.Truck;
//
//import java.lang.reflect.Type;
//
//public class TruckGsonSerializer implements JsonSerializer<Truck> {
//    @Override
//    public JsonElement serialize(Truck truck, Type type, JsonSerializationContext jsonSerializationContext) {
//        JsonObject jsonObject = new JsonObject();
//
//        jsonObject.addProperty("id", truck.getId());
//        jsonObject.addProperty("brand", truck.getBrand());
//        jsonObject.addProperty("model", truck.getModel());
//        jsonObject.addProperty("hp", truck.getHp());
//        jsonObject.addProperty("engine", truck.getEngine());
//        jsonObject.addProperty("releaseYear", truck.getReleaseYear());
//
//        return jsonObject;
//    }
//}
