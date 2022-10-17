package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.hibernate.ForumHib;
import com.trucker_system.truckersystem.model.Comment;
import com.trucker_system.truckersystem.model.Forum;
import com.trucker_system.truckersystem.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CreateForumModal {
    @FXML
    public TextArea forumMessageText;
    @FXML
    public TextField forumTopicText;
    @FXML
    public Button forumPostBtn;
    @FXML
    public Label labelValidate;

    private Consumer<Forum> forumConsumer;
    private User user;
    private ForumHib forumHib = null;

    public void onCreatePost(ActionEvent actionEvent) {
        if (!forumTopicText.getText().isEmpty() && !forumMessageText.getText().isEmpty()) {
            Forum forum = new Forum(forumTopicText.getText(), user, null, forumMessageText.getText());
            List<Comment> comments = new ArrayList<>();
            comments.add(new Comment(forumMessageText.getText(), forum, comments, null, user));
            forum.setComments(comments);
            this.forumHib.createForum(forum);

            forumConsumer.accept(forum);

            Stage stage = (Stage) forumPostBtn.getScene().getWindow();
            stage.close();
        } else labelValidate.setText("Missing fields");
    }

    public void initData(ForumHib forumHib, User user) {
        this.forumHib = forumHib;
        this.user = user;
    }

    public void setForumConsumerCallback(Consumer<Forum> forumConsumer) { this.forumConsumer = forumConsumer; }
}
