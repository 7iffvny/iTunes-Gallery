package cs1302.gallery;

import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * MenuComponent class that contains all the options that the menu
 * bar is composed of such as File, Themes, and Help.
 */
public class MenuComponent extends MenuBar {

    // Menu bar instance variables
    Menu menuBar;
    Menu helpMenu;
    Menu themeBar;
    MenuItem exitOption;
    MenuItem aboutMenu;
    MenuItem darkTheme;
    MenuItem defaultTheme;
    MenuItem radiantTheme;
    MenuItem ugaTheme;

    /**
     * MenuComponent constructor that takes in a parameter {@code Stage}.
     * @param stageObj A Stage object.
     */
    public MenuComponent(Stage stageObj) {
        // File Menu
        menuBar = new Menu("File"); // Creates a menu object named "File"
        exitOption = new MenuItem("Exit"); // Creates a new menu item named "Exit"
        exitOption.setOnAction(e -> stageObj.close());
        menuBar.getItems().add(exitOption); // Adds Exit to the menu bar under File

        // Theme Menu
        themeBar = new Menu("Theme"); // Creates a menu object named "Theme"
        darkTheme = new MenuItem("Dark"); // Creates a new menu item named "Dark"
        defaultTheme = new MenuItem("Default");
        radiantTheme = new MenuItem("Radiant");
        ugaTheme = new MenuItem("UGA");
        // add the theme files under the Theme menu
        themeBar.getItems().addAll
            (darkTheme, defaultTheme, radiantTheme, ugaTheme);

        // Help Menu
        helpMenu = new Menu("Help"); // Creates a menu object named "Help"
        aboutMenu = new MenuItem("About"); // Creates a new menu item named "About"
        helpMenu.getItems().add(aboutMenu); // Adds About under Help menu
        getMenus().addAll(menuBar, themeBar, helpMenu); // Adds to Menu bar
        aboutMenu.setOnAction(aboutMenuEH -> {
            Stage aboutStage = new Stage();
            VBox aboutVBox = new VBox(10);
            HBox aboutHBox = new HBox();
            Scene aboutScene = new Scene(aboutVBox);
            ImageView myPhoto = new ImageView(new Image("https://i.imgur.com/LOiBhaO.jpg"));

            // about
            aboutStage.setTitle("About Tiffany Nguyen");
            aboutStage.setMaxWidth(350);
            aboutStage.setMaxHeight(350);

            // photo
            myPhoto.setFitWidth(300);
            myPhoto.setFitHeight(300);

            // aboutInfo
            Text myName = new Text(" Author: Tiffany Nguyen");
            Text myEmail = new Text(" Email: ttn29300@uga.edu");
            Text programVersion = new Text(" Version: 2.21");

            // stage
            aboutHBox.getChildren().add(myPhoto);
            aboutVBox.getChildren().addAll(myName, myEmail, programVersion, aboutHBox);
            aboutStage.setScene(aboutScene);
            aboutStage.setResizable(false);
            aboutStage.sizeToScene();
            aboutStage.show();
        }); // aboutMenu
    } // MenuComponent Stage

} // MenuComponent
