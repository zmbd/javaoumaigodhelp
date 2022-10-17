package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.hibernate.CommentHib;
import com.trucker_system.truckersystem.hibernate.ForumHib;
import com.trucker_system.truckersystem.model.Comment;
import com.trucker_system.truckersystem.model.Forum;
import com.trucker_system.truckersystem.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ForumthreadReplyModal {
    @FXML
    public TextArea replyMessageText;
    @FXML
    public Button replyBtn;
    @FXML
    public Label labelValidate;

    private Consumer<Comment> commentConsumer;
    private Forum forum;
    private User user;
    private Comment parentComment;
    private CommentHib commentHib;

    public void setCommentConsumerCallback(Consumer<Comment> commentConsumer) { this.commentConsumer = commentConsumer; }

    public void initData(Forum forum, CommentHib commentHib, User user, Comment parentComment) {
        this.forum = forum;
        this.commentHib = commentHib;
        this.user = user;
        this.parentComment = parentComment;
    }

    public void onReply(ActionEvent actionEvent) {
        if (!replyMessageText.getText().isEmpty()) {
            Comment comment = new Comment(replyMessageText.getText(), this.forum, null, parentComment, user);
            commentHib.createComment(comment);
            commentConsumer.accept(comment);

            Stage stage = (Stage) replyBtn.getScene().getWindow();
            stage.close();
        }
    }
}
