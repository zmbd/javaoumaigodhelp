package com.trucker_system.truckersystem.webControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trucker_system.truckersystem.hibernate.CommentHib;
import com.trucker_system.truckersystem.hibernate.ForumHib;
import com.trucker_system.truckersystem.hibernate.UserHib;
import com.trucker_system.truckersystem.model.Comment;
import com.trucker_system.truckersystem.model.Forum;
import com.trucker_system.truckersystem.model.User;
import com.trucker_system.truckersystem.utils.ForumSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Controller
public class ForumWeb {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("truckerdb");
    private final ForumHib forumHib = new ForumHib(entityManagerFactory);
    private final UserHib userHib = new UserHib(entityManagerFactory);
    private final CommentHib commentHib = new CommentHib(entityManagerFactory);

    @RequestMapping(value = "forum/getAll", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllForums() {
        List<Forum> forumList = forumHib.getAllForumsThreads();

        if (forumList.size() < 1) return "No forum threads exist";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Forum.class, new ForumSerializer());

        Gson gson = gsonBuilder.setPrettyPrinting().create();

        return gson.toJson(forumList);
    }

    @RequestMapping(value = "forum/getForumById/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCargoById(@PathVariable(name = "id") int id) {
        Forum forum = forumHib.getForumById(id);

        if (forum == null) return "Forum ID: " + id + " does not exist";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Forum.class, new ForumSerializer());

        Gson gson = gsonBuilder.setPrettyPrinting().create();

        return gson.toJson(forum);
    }

    @RequestMapping(value = "forum/createForum", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String createForum(@RequestBody String request) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);

        User user = userHib.getUserById(Integer.parseInt(properties.getProperty("userId")));

        if (user == null) return "User id does not exist";

        Forum forum = new Forum(
                properties.getProperty("title"),
                user,
                null,
                properties.getProperty("rootComment")
        );

        try {
            forumHib.createForum(forum);
            commentHib.createComment(new Comment(forum.getFirstCommentText(), forum, null, null, user));
        } catch (Exception e) {
            return "Failed to create Forum";
        }

        return "Forum created successfully";
    }

    @RequestMapping(value = "forum/updateForum/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String createForum(@RequestBody String request, @PathVariable(value = "id") int id) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);

        Forum forum = forumHib.getForumById(id);

        if (forum == null) return "Forum with ID: " + id + " does not exist";

        User user = userHib.getUserById(Integer.parseInt(properties.getProperty("userId")));

        if (user == null) return "User with ID: " + id + " not found";

        forum.setTitle(properties.getProperty("title"));
        forum.setFirstCommentText(properties.getProperty("rootComment"));
        forum.setUser(user);

        try {
            forumHib.updateForum(forum);
            Comment parentComment = commentHib.getParentComment(forum);

            parentComment.setCommentText(forum.getFirstCommentText());
            parentComment.setUser(user);

            commentHib.updateComment(parentComment);
        } catch (Exception e) {
            return "Failed to update Forum and its entities";
        }

        return "Forum successfully updated";
    }

    @RequestMapping(value = "forum/deleteForum/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteForum(@PathVariable(name = "id") int id) {
        Forum forum = forumHib.getForumById(id);

        if (forum == null) return "Forum ID: " + id + " does not exist";

        try {
            forum.setComments(commentHib.getAllCommentsOfForum(forum));
            for (var c : forum.getComments()) {
                commentHib.deleteComment(c.getId());
            }

            forumHib.deleteForum(id);
        } catch (Exception e) {
            return "Failed to delete Forum ID: " + id;
        }

        return "Forum ID: " + id + " was deleted successfully";
    }
}
