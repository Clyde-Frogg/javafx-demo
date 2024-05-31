package com.example.javafxdemo;

import com.example.javafxdemo.user.UserDetailsView;
import com.example.javafxdemo.user.UserProfile;
import com.example.javafxdemo.user.UserProfileView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFxDemoApplication extends Application {

    private ObservableList<UserProfile> userProfiles;
    private HBox profilesContainer;
    private UserDetailsView userDetailsView;

    @Override
    public void start(Stage primaryStage) {
        userProfiles = FXCollections.observableArrayList(
                new UserProfile("User 1", "test.png", "Admin"),
                new UserProfile("User 2", "test2.jpg", "User"),
                new UserProfile("User 3", "test3.png", "Operator")
                // Add more profiles here...
        );

        profilesContainer = new HBox(10); // 10px spacing between profiles
        profilesContainer.setPrefHeight(150); // Height to accommodate profile views
        ScrollPane scrollPane = new ScrollPane(profilesContainer);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        userDetailsView = new UserDetailsView(this::saveUserProfile);

        VBox mainLayout = new VBox(10, scrollPane, userDetailsView);

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("User Profiles");
        primaryStage.show();

        refreshProfiles();
    }

    private void refreshProfiles() {
        profilesContainer.getChildren().clear();
        for (int i = 0; i < userProfiles.size(); i++) {
            UserProfile userProfile = userProfiles.get(i);
            int final1 = i;
            int final2 = i;
            UserProfileView profileView = new UserProfileView(
                    userProfile,
                    this::selectUserProfile,
                    () -> moveProfileLeft(final1),
                    () -> moveProfileRight(final2)
            );
            profilesContainer.getChildren().add(profileView);
        }
    }

    private void selectUserProfile(UserProfile userProfile) {
        userDetailsView.setUserProfile(userProfile);
        for (javafx.scene.Node node : profilesContainer.getChildren()) {
            if (node instanceof UserProfileView) {
                ((UserProfileView) node).setSelected(((UserProfileView) node).getUserProfile() == userProfile);
            }
        }
    }

    private void moveProfileLeft(int index) {
        if (index > 0) {
            UserProfile temp = userProfiles.get(index - 1);
            userProfiles.set(index - 1, userProfiles.get(index));
            userProfiles.set(index, temp);
            refreshProfiles();
            selectUserProfile(userProfiles.get(index - 1));
        }
    }

    private void moveProfileRight(int index) {
        if (index < userProfiles.size() - 1) {
            UserProfile temp = userProfiles.get(index + 1);
            userProfiles.set(index + 1, userProfiles.get(index));
            userProfiles.set(index, temp);
            refreshProfiles();
            selectUserProfile(userProfiles.get(index + 1));
        }
    }

    private void saveUserProfile(UserProfile userProfile) {
        // Send data to backend
    }

    public static void main(String[] args) {
        launch(args);
    }
}