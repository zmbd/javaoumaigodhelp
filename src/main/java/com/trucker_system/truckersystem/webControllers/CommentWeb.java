package com.trucker_system.truckersystem.webControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trucker_system.truckersystem.hibernate.CommentHib;
import com.trucker_system.truckersystem.hibernate.ForumHib;
import com.trucker_system.truckersystem.hibernate.UserHib;
import com.trucker_system.truckersystem.model.Comment;
import com.trucker_system.truckersystem.model.Forum;
import com.trucker_system.truckersystem.model.User;
import com.trucker_system.truckersystem.utils.CommentSerializer;
import com.trucker_system.truckersystem.utils.ForumSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Properties;

@Controller
public class CommentWeb {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("truckerdb");
    private final ForumHib forumHib = new ForumHib(entityManagerFactory);
    private final UserHib userHib = new UserHib(entityManagerFactory);
    private final CommentHib commentHib = new CommentHib(entityManagerFactory);

    @RequestMapping(value = "comment/getAll", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllComments() {
        List<Comment> commentList = commentHib.getAllComments();

        if (commentList.size() < 1) return "No comments in forum thread";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Comment.class, new CommentSerializer());

        Gson gson = gsonBuilder.setPrettyPrinting().create();

        return gson.toJson(commentList);
    }

    @RequestMapping(value = "comment/getCommentById/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCommentById(@PathVariable(name = "id") int id) {
        Comment comment = commentHib.getCommentById(id);

        if (comment == null) return  "Comment not found";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Comment.class, new CommentSerializer());

        Gson gson = gsonBuilder.setPrettyPrinting().create();

        return gson.toJson(comment);
    }


    @RequestMapping(value = "comment/createComment", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String createForum(@RequestBody String request) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);

        Forum forum = forumHib.getForumById(Integer.parseInt(properties.getProperty("forumId")));
        User user = userHib.getUserById(Integer.parseInt(properties.getProperty("userId")));
        Comment parentComment = properties.getProperty("parentComment") == null ? null : commentHib.getCommentById(Integer.parseInt(properties.getProperty("parentComment")));

        if (forum == null) return "Forum not found, can't insert comment to non existent thread";

        if (user == null) return "No user with ID: " + Integer.parseInt(properties.getProperty("userId")) + ", can't insert comment without valid user";


        Comment comment = new Comment(
            properties.getProperty("comment"),
            forum,
            null,
            parentComment,
            user
        );

        try {
            commentHib.createComment(comment);
        } catch (Exception e) {
            return "Failed to create comment";
        }

        return "Comment created successfully";
    }

    @RequestMapping(value = "comment/updateComment/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String createForum(@RequestBody String request, @PathVariable(value = "id") int id) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);

        Comment comment = commentHib.getCommentById(id);

        if (comment == null) return "Comment does not exist";

        comment.setCommentText(properties.getProperty("comment"));

        try {
            commentHib.updateComment(comment);
        } catch (Exception e) {
            return "Failed to update comment";
        }

        return "Comment updated";
    }

    @RequestMapping(value = "comment/deleteComment/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteComment(@PathVariable(name = "id") int id) {
        Comment comment = commentHib.getCommentById(id);

        if (comment == null) return "Comment ID: " + id + " does not exist";

        try {
            if (comment.getParentComment() != null) {
                comment.setParentComment(null);
                commentHib.updateComment(comment);
            }
            commentHib.deleteComment(id);
        } catch (Exception e) {
            return "Failed to delete Comment";
        }

        return "Comment deleted";
    }
}
