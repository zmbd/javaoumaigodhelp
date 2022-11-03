package com.trucker_system.truckersystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String commentText;
    @ManyToOne
    private Forum forum;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> replies;
    @ManyToOne
    private Comment parentComment;
    @ManyToOne
    private User user;

    public Comment(String commentText, Forum forum, List<Comment> replies, Comment parentComment, User user) {
        this.commentText = commentText;
        this.forum = forum;
        this.replies = replies;
        this.parentComment = parentComment;
        this.user = user;
    }

    @Override
    public String toString() {
        return user.getName() + " replied: " + commentText;
    }

    public String displayMessage() {
        return user.getName() + " commented:\n" + commentText;
    }


}
