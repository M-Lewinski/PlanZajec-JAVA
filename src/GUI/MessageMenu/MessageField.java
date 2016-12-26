package GUI.MessageMenu;

import GUI.Controller;
import GUI.Main;
import GUI.MessageMenu.Error.ErrorField;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import jdk.internal.dynalink.beans.StaticClass;

/**
 * Created by lewin on 12/25/16.
 */
public abstract class MessageField extends TextArea {
    protected double fontSize = 15.0;
    protected String imageUrl;
    protected MessageButton button;
    private double height = 60.0;

    public MessageField(Controller controller, String text) {
        super();
        setStyleSettings(text);
        setAnchorPane(controller);
    }

    private void setAnchorPane(Controller controller) {
        AnchorPane.setBottomAnchor(this, 0.0);
        AnchorPane.setTopAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        anchorPane.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        anchorPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        anchorPane.getChildren().add(this);
        this.button = new MessageButton(controller.getVbox(), anchorPane, this);
        controller.getVbox().getChildren().add(anchorPane);
    }

    private void setStyleSettings(String text) {
        this.setEditable(false);
        this.setText(text);
//        this.getStyleClass().add("MessageField");
        this.setFont(new Font(this.fontSize));
        this.setPrefHeight(this.height);
        this.setMinHeight(Region.USE_PREF_SIZE);
        this.setMaxHeight(Region.USE_PREF_SIZE);
    }

    private class MessageButton extends Button {
        private AnchorPane anchorPane;
        private MessageField messageField;
        private VBox vBox;
        private double width = 10.0;
        private double height = 10.0;

        public MessageButton(VBox vBox, AnchorPane anchorPane, MessageField messageField) {
            super();
            this.messageField = messageField;
            this.vBox = vBox;
            this.setStyle("-fx-background-color: transparent; -fx-font-weight: bold");
            this.setPrefSize(this.width, this.height);
            this.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            this.setText("X");
            this.anchorPane = anchorPane;
            AnchorPane.setRightAnchor(this, 0.0);
            AnchorPane.setBottomAnchor(this, 0.0);
            AnchorPane.setTopAnchor(this, 0.0);
            this.anchorPane.getChildren().add(this);
            this.setOnAction(event -> {
                this.vBox.getChildren().remove(this.anchorPane);
                this.delete();
            });
        }

        private void delete() {
            this.vBox = null;
            this.anchorPane = null;
            this.messageField.delete();
            this.messageField = null;
        }
    }

    public double getFontSize() {
        return fontSize;
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
    }

    public void delete() {
        this.button = null;
    }

    protected void addImage() {
        if (this.imageUrl != null) {
            Image image = new Image(getClass().getResourceAsStream(this.imageUrl));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(20.0);
            this.button.setGraphic(imageView);
        }
    }

}
