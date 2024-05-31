package com.example.javafxdemo.user;

import java.util.Objects;
import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class UserProfileView extends GridPane {

  private final UserProfile userProfile;
  private final Consumer<UserProfile> onSelect;

  public UserProfileView(UserProfile userProfile, Consumer<UserProfile> onSelect) {
    this.userProfile = userProfile;
    this.onSelect = onSelect;

    String imagePath = "/images/" + userProfile.getImagePath();
    ImageView profileImage =
        new ImageView(
            new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm()));

    profileImage.setFitWidth(100);
    profileImage.setFitHeight(100);
    profileImage.setOnMouseClicked(this::handleClick);

    this.add(profileImage, 0, 0);
    this.setAlignment(Pos.CENTER);
    this.setVgap(10);
  }

  private void handleClick(MouseEvent event) {
    onSelect.accept(userProfile);
  }

  public void setSelected(boolean selected) {
    this.setStyle(selected ? "-fx-border-color: blue;" : "-fx-border-color: none;");
  }

  public UserProfile getUserProfile() {
    return userProfile;
  }
}
