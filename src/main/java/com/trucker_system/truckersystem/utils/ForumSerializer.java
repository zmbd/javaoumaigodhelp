package com.trucker_system.truckersystem.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.trucker_system.truckersystem.model.Forum;

import java.lang.reflect.Type;

public class ForumSerializer implements JsonSerializer<Forum> {
    @Override
    public JsonElement serialize(Forum forum, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", forum.getId());
        jsonObject.addProperty("title", forum.getTitle());
        jsonObject.addProperty("userId", forum.getUser().getId());
        jsonObject.addProperty("rootComment:", forum.getFirstCommentText());

        return jsonObject;
    }
}
