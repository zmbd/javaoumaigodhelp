package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.hibernate.ForumHib;
import com.trucker_system.truckersystem.model.Forum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class ForumTopicEdit {
    @FXML
    public TextField forumTitleField;
    @FXML
    public Button submitBtn;
    @FXML
    public Label labelValidate;

    private Consumer<Forum> forumConsumer;
    private Forum forum;
    private ForumHib forumHib;

    public void setForumConsumerCallback(Consumer<Forum> forumConsumer) { this.forumConsumer = forumConsumer; }

    public void initData(ForumHib forumHib, Forum forum) {
        this.forumHib = forumHib;
        this.forum = forum;

        forumTitleField.setText(forum.getTitle());
    }

    public void onEditSubmit(ActionEvent actionEvent) {
        if (!this.forum.getTitle().isEmpty() && !this.forum.getTitle().equals(forumTitleField.getText())) {
            this.forum.setTitle(forumTitleField.getText());
            this.forumHib.updateForum(this.forum);
            forumConsumer.accept(forum);

            Stage stage = (Stage) submitBtn.getScene().getWindow();
            stage.close();
        } else labelValidate.setText("Input data is invalid");
    }
}
