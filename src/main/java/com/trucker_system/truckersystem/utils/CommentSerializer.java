package com.trucker_system.truckersystem.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.trucker_system.truckersystem.model.Comment;

import java.lang.reflect.Type;

public class CommentSerializer implements JsonSerializer<Comment> {
    @Override
    public JsonElement serialize(Comment comment, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", comment.getId());
        jsonObject.addProperty("comment", comment.getCommentText());
        jsonObject.addProperty("forumId", comment.getForum().getId());
        jsonObject.addProperty("parentComment", comment.getParentComment() == null ? null : comment.getParentComment().getId());
        jsonObject.addProperty("userId", comment.getUser().getId());

        return jsonObject;
    }
}
