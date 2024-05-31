package com.example.javafxdemo.user;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.function.Consumer;

public class UserDetailsView extends GridPane {

    private final TextField nameField;
    private final TextField roleField;
    private UserProfile userProfile;
    private final Consumer<UserProfile> onSave;

    public UserDetailsView(Consumer<UserProfile> onSave) {
        this.onSave = onSave;

        Label nameLabel = new Label("Name:");
        nameField = new TextField();
        Label roleLabel = new Label("Role:");
        roleField = new TextField();

        this.addRow(0, nameLabel, nameField);
        this.addRow(1, roleLabel, roleField);

        nameField.setOnKeyReleased(e -> saveChanges());
        roleField.setOnKeyReleased(e -> saveChanges());
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        nameField.setText(userProfile.getName());
        roleField.setText(userProfile.getRole());
    }

    private void saveChanges() {
        userProfile.setName(nameField.getText());
        userProfile.setRole(roleField.getText());
        onSave.accept(userProfile);
    }
}
