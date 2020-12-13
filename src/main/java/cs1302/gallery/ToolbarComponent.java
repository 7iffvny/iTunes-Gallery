package cs1302.gallery;

import cs1302.gallery.GalleryApp;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import java.io.InputStreamReader;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.layout.HBox;
import javafx.scene.image.*;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Thread;
import java.lang.Math;
import java.net.URL;

/**
 * ToolbarComponent class that extends the HBox class.
 * This class is responsible for constructing the images of the
 * artwork on the GalleryApp. Also contains code for the search query
 * and update images feature of the program.
 */
public class ToolbarComponent extends HBox {
    // ToolbarComponent instance variables
    Button playPauseButton;
    Button imageUpdate;
    Separator separatorSep;
    TextField queryTextSearch;
    Timeline imageTimeline;
    Boolean nowPlaying;
    GalleryApp galleryAppObj;
    Label searchQueryLabel;

    /**
     * ToolbarComponent constructor that takes in parameter of GalleryApp object.
     * @param galleryObj GalleryApp object
     */
    public ToolbarComponent(GalleryApp galleryObj) {
        galleryAppObj = galleryObj;
        nowPlaying = true; // sets nowPlaying boolean to true
        playPauseButton = new Button(); // creates new button
        separatorSep = new Separator(Orientation.VERTICAL);
        queryTextSearch = new TextField();
        imageUpdate = new Button();
        imageTimeline = new Timeline();
        searchQueryLabel = new Label();

        searchQueryLabel.setText("Search Query: "); // sets label to "Search Query"
        queryTextSearch.setText("Country"); // sets "Pop" as search item
        playPauseButton.setText("Pause"); // sets button to say "Pause"
        playPauseButton.setMaxWidth(Double.MAX_VALUE); // sets text to fill in button
        imageUpdate.setText("Update Images"); // sets button text to "Update Images"
        imageUpdate.setMaxWidth(Double.MAX_VALUE);

        // sets the duration of the images changing at 2 seconds
        KeyFrame imageDur = new KeyFrame(Duration.seconds(2), changeImage -> {
            int generatedArtwork = (int) (Math.random() * 20);
            int generatedNum = (int)(Math.random() * galleryAppObj.mainContentComp.totalURL.size());
            while (galleryAppObj.mainContentComp.twentyURL.contains
                (galleryAppObj.mainContentComp.totalURL.get(generatedNum))) {
                generatedNum = (int)(Math.random() * galleryAppObj.mainContentComp.totalURL.size());
            }
            galleryAppObj.mainContentComp.imageList.get(generatedArtwork)
                    .setImage(new Image(galleryAppObj.mainContentComp.totalURL.get(generatedNum)));
        });

        imageTimeline.setCycleCount(imageTimeline.INDEFINITE);
        imageTimeline.getKeyFrames().add(imageDur);
        playPauseButton.setOnAction(playPause ->
            changePlayPauseButton()); // change play/pause button

        imageUpdate.setOnAction(imageUpdate -> {
            Thread update = new Thread(this::clickUpdate);
            update.setDaemon(true);
            update.start();
        }); // imageUpdate

        Thread appUpdate = new Thread(this::clickUpdate);

        // Container
        getChildren().addAll
            (playPauseButton, separatorSep, searchQueryLabel, queryTextSearch, imageUpdate);
        setAlignment(Pos.CENTER); // aligns text to be center
        setHgrow(playPauseButton, Priority.ALWAYS); // sets buttons to fill in space
        setHgrow(imageUpdate, Priority.ALWAYS);
        setPadding(new Insets(6,5,6,5)); // sets padding
        setSpacing(5);
        appUpdate.setDaemon(true);
        appUpdate.start();
    } // ToolBarComponent

    /**
     * updateClick method allows for updating the url.
     */
    public void clickUpdate() {
        String galleryLink = "https://itunes.apple.com/search?term="; // iTunes API
        // adds the searched term to the URL
        galleryLink += queryTextSearch.getText().trim().replace(' ' , '+');
        try {
            URL galleryUrl = new URL(galleryLink); // takes in the iTunes website URL
            InputStreamReader inStreamReader = new InputStreamReader(galleryUrl.openStream());

            // JSON variables
            JsonElement je = JsonParser.parseReader(inStreamReader);
            JsonObject root = je.getAsJsonObject();
            JsonArray results = root.getAsJsonArray("results");
            int numResults = results.size();

            if (numResults > 21) {
                ArrayList<String> iTunesURLS = new ArrayList<String>();
                for (int i = 0; i < numResults; i++) {
                    JsonObject result = results.get(i).getAsJsonObject();
                    JsonElement artworkUrl100 = result.get("artworkUrl100"); // artworkUrl100 member
                    if (artworkUrl100 != null) {
                        iTunesURLS.add(artworkUrl100.getAsString()); // adds to ArrayList
                    } // if
                } // for
                galleryAppObj.mainContentComp.updateImages(iTunesURLS);
            } else { // if the size is less than 21, run notificationAlert method
                Platform.runLater(() -> { // displays a warning if the image result is low
                    Alert notificationDisplayed = new Alert(Alert.AlertType.ERROR);
                    notificationDisplayed.setTitle("Error");
                    notificationDisplayed.setHeaderText("The search result is invalid");
                    notificationDisplayed.setContentText("Less than 21 distinct artwork URLS.");
                    notificationDisplayed.showAndWait();
                });
            } // if else
        } catch (IOException IOE) {
            System.out.println("File not found for the searched term: "
                + queryTextSearch.getText());
        } // try catch
    } // updateClick

    /**
     * changeButton method that allows for the play/pause button to change.
     */
    public void changePlayPauseButton() {
        if (!nowPlaying) {
            nowPlaying = true; // is playing
            playPauseButton.setText("Pause"); // "Pause" button displayed
            imageTimeline.play(); // currently playing
        } else {
            nowPlaying = false; // not playing
            playPauseButton.setText("Play"); // "Play" button displayed
            imageTimeline.pause(); // currently on pause
        } // if else
    } // changePlayPauseButton

} // ToolBarComponent
