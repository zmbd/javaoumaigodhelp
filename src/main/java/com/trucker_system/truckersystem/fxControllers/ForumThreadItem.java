package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.model.Comment;
import com.trucker_system.truckersystem.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ForumThreadItem {
    private int id;
    private String commentText;
    private int forumId;
    private User user;
    private List<Comment> replies;

    public ForumThreadItem(int id, String commentText, int forumId, User user, List<Comment> replies) {
        this.id = id;
        this.commentText = commentText;
        this.forumId = forumId;
        this.user = user;
        this.replies = replies;
    }


    @Override
    public String toString() {
        return user.getName() + " writes:\n" + commentText;
    }
}
