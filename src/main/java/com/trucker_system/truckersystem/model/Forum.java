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
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @org.hibernate.annotations.ForeignKey(name = "none")
    private transient List<Comment> comments;
    private String firstCommentText;

    public Forum(String title, User user) {
        this.title = title;
        this.user = user;
    }

    public Forum(String title, User user, List<Comment> comments, String firstCommentText) {
        this.title = title;
        this.user = user;
        this.comments = comments;
        this.firstCommentText = firstCommentText;
    }

    @Override
    public String toString() {
        return title;
    }

    public String displayMessage() {
        return user.getName() + " commented:\n" + firstCommentText;
    }
}
