package cs1302.gallery;

import cs1302.gallery.ToolbarComponent;
import cs1302.gallery.MainContentComp;
import cs1302.gallery.MenuComponent;
import javafx.scene.control.ProgressBar;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Represents an iTunes GalleryApp.
 */
public class GalleryApp extends Application {
    // GalleryApp instance variables
    MainContentComp mainContentComp;
    ToolbarComponent toolBarComp;
    ProgressBar progressBar;
    MenuComponent menuComp;
    Label progressBarText;
    HBox bottomBar;

    /**
     * Start method that iniates the program to run.
     * {@inheritdoc}
     */
    @Override
    public void start(Stage stage) {
        VBox VBox = new VBox();
        bottomBar = new HBox();
        Scene gallery = new Scene(VBox);
        mainContentComp = new MainContentComp(this);
        toolBarComp = new ToolbarComponent(this);
        menuComp = new MenuComponent(stage);
        progressBar = new ProgressBar(0); // sets progress to 0
        progressBarText = new Label("  Images provided courtesy of iTunes");

        // adds menu items to the top bar
        VBox.getChildren().addAll(menuComp, toolBarComp, mainContentComp, bottomBar);

        // adds the progress bar and label to the bottom bar
        bottomBar.getChildren().addAll(progressBar, progressBarText);

        // Theme Layout
        menuComp.defaultTheme.setOnAction( e -> {
            gallery.getStylesheets().clear(); // Clears theme to default
        }); // Default
        menuComp.darkTheme.setOnAction( e -> {
            gallery.getStylesheets().clear(); // Clears theme before applying new theme
            gallery.getStylesheets().add(getClass().getResource("DARK.css").toExternalForm());
        }); // Dark
        menuComp.radiantTheme.setOnAction(e -> {
            gallery.getStylesheets().clear();
            gallery.getStylesheets().add(getClass().getResource("RADIANT.css").toExternalForm());

        }); // Radiant
        menuComp.ugaTheme.setOnAction(e -> {
            gallery.getStylesheets().clear();
            gallery.getStylesheets().add(getClass().getResource("UGA.css").toExternalForm());
        }); // UGA

        // Stage
        stage.setMaxHeight(520);
        stage.setMaxWidth(500);
        stage.setTitle("GalleryApp!");
        stage.setResizable(false);
        stage.setScene(gallery);
        stage.sizeToScene();
        stage.show();
    } // start

} // GalleryApp
