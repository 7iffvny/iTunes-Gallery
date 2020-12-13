package cs1302.gallery;

import cs1302.gallery.GalleryApp;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.util.ArrayList;

/**
 * MainContentComp class that extends HBox is responsible for
 * the main components of the Gallery App.
 */
public class MainContentComp extends HBox {
    // Main component instance variables
    ArrayList<ImageView> imageList; // ArrayList used to display images
    ArrayList<String> totalURL; // ArrayList used to hold the total URLS
    ArrayList<String> twentyURL; // ArrayList used to hold the 20 URLS
    VBox firstCol;
    VBox secondCol;
    VBox thirdCol;
    VBox fourthCol;
    VBox fifthCol;
    GalleryApp galleryAppObj;

    /**
     * MainContentComp constructor that takes in a parameter of GalleryApp object.
     * @param galleryObj GalleryApp object
     */
    public MainContentComp(GalleryApp galleryObj) {
        // Creating objects of the instance variables
        galleryAppObj = galleryObj;
        imageList = new ArrayList<ImageView>();
        firstCol = new VBox();
        secondCol = new VBox();
        thirdCol = new VBox();
        fourthCol = new VBox();
        fifthCol = new VBox();
        int HBox = 4;
        int VBox = 5;

        for (int rotate = 0; rotate < VBox; rotate++) { // for loop VBox
            for (int j = 0; j < HBox; j++) { // for loop HBox
                ImageView artworkCols = new ImageView();
                artworkCols.setFitHeight(100);
                artworkCols.setFitWidth(100);
                switch (rotate) {
                case 0:
                    firstCol.getChildren().add(artworkCols);
                    imageList.add(artworkCols);
                    break;
                case 1:
                    secondCol.getChildren().add(artworkCols);
                    imageList.add(artworkCols);
                    break;
                case 2:
                    thirdCol.getChildren().add(artworkCols);
                    imageList.add(artworkCols);
                    break;
                case 3:
                    fourthCol.getChildren().add(artworkCols);
                    imageList.add(artworkCols);
                    break;
                case 4:
                    fifthCol.getChildren().add(artworkCols);
                    imageList.add(artworkCols);
                    break;
                } // switch case
            } // for loop
        } // for loop
        getChildren().addAll(firstCol, secondCol, thirdCol, fourthCol, fifthCol);
    } // MainContentComp

    /**
     * updateImages method that is responsible for updating the artwork in the Gallery App.
     * This method takes in an ArrayList String
     * @param element ArrayList object.
     */
    public void updateImages(ArrayList<String> element) {
        totalURL = element;
        twentyURL = new ArrayList<String>();
        Platform.runLater(() -> { // sets progress to 0 and changes play/pause button
            galleryAppObj.toolBarComp.changePlayPauseButton();
            galleryAppObj.progressBar.setProgress(0);
        });
        for (int image = 0; image < element.size(); image++) {
            String delayURL = "http://deelay.me/3/" + totalURL.get(image).substring(0,4)
                + totalURL.get(image).substring(5);
            Platform.runLater(() -> {
                double progressPercent =
                    galleryAppObj.progressBar.getProgress(); //getting current progress
                double progressAdd = 1.0 / element.size(); // 1/20 percent progress
                // adds 1/element size to current progress
                galleryAppObj.progressBar.setProgress(progressPercent + progressAdd);
            }); //lambda to set progress bar
            if (image < 20) { // if statement if there are less than 20 images
                twentyURL.add(totalURL.get(image));
                imageList.get(image).setImage(new Image(delayURL));
            } // if
        } // for loop
        Platform.runLater(() ->
            galleryAppObj.toolBarComp.changePlayPauseButton()); // changes play/pause button
    } // UpdateImages

} // MainContentComp
