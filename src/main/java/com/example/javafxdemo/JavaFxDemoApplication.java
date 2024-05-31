package com.example.javafxdemo;

import com.example.javafxdemo.user.UserDetailsView;
import com.example.javafxdemo.user.UserProfile;
import com.example.javafxdemo.user.UserProfileView;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFxDemoApplication extends Application {

  private static final Logger LOGGER = Logger.getLogger(JavaFxDemoApplication.class.getName());

  private ObservableList<UserProfile> userProfiles;
  private HBox profilesContainer;
  private UserDetailsView userDetailsView;
  private UserProfile selectedUserProfile;

  @Override
  public void start(Stage primaryStage) {
    userProfiles =
        FXCollections.observableArrayList(
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

    Button moveLeftButton = new Button("Move Left");
    moveLeftButton.setOnAction(e -> moveProfileLeft());
    Button moveRightButton = new Button("Move Right");
    moveRightButton.setOnAction(e -> moveProfileRight());

    HBox moveButtonsContainer = new HBox(10, moveLeftButton, moveRightButton);
    moveButtonsContainer.setAlignment(Pos.CENTER);

    VBox mainLayout = new VBox(10, scrollPane, userDetailsView, moveButtonsContainer);

    Scene scene = new Scene(mainLayout, 800, 600);
    primaryStage.setScene(scene);
    primaryStage.setTitle("User Profiles");
    primaryStage.show();

    refreshProfiles();
  }

  private void refreshProfiles() {
    profilesContainer.getChildren().clear();
    for (UserProfile userProfile : userProfiles) {
      UserProfileView profileView = new UserProfileView(userProfile, this::selectUserProfile);
      profilesContainer.getChildren().add(profileView);
    }
  }

  private void selectUserProfile(UserProfile userProfile) {
    selectedUserProfile = userProfile;
    userDetailsView.setUserProfile(userProfile);
    for (javafx.scene.Node node : profilesContainer.getChildren()) {
      if (node instanceof UserProfileView) {
        ((UserProfileView) node)
            .setSelected(((UserProfileView) node).getUserProfile() == userProfile);
      }
    }
  }

  private void moveProfileLeft() {
    if (selectedUserProfile != null) {
      int index = userProfiles.indexOf(selectedUserProfile);
      if (index > 0) {
        UserProfile temp = userProfiles.get(index - 1);
        userProfiles.set(index - 1, selectedUserProfile);
        userProfiles.set(index, temp);
        refreshProfiles();
        selectUserProfile(selectedUserProfile);
      }
    }
  }

  private void moveProfileRight() {
    if (selectedUserProfile != null) {
      int index = userProfiles.indexOf(selectedUserProfile);
      if (index < userProfiles.size() - 1) {
        UserProfile temp = userProfiles.get(index + 1);
        userProfiles.set(index + 1, selectedUserProfile);
        userProfiles.set(index, temp);
        refreshProfiles();
        selectUserProfile(selectedUserProfile);
      }
    }
  }

  private void saveUserProfile(UserProfile userProfile) {
    // Send data to backend
    LOGGER.info("Saved user profile: " + userProfile.getName());
  }

  public static void main(String[] args) {
    launch(args);
  }
}
