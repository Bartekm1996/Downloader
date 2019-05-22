import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import java.net.*;
import java.util.*;
import javafx.scene.media.MediaPlayer;
import java.util.stream.Collectors;
import javafx.scene.layout.*;

public class LogInPage {


    public GridPane mainLoginPane(){



    }



    private void loginWindows(){

        GridPane logInPane = new GridPane();
                 logInPane.getStylesheets().add("/LoginPaneStyleSheet.css");

        TextField loginField = new TextField();
                  loginField.setPromptText("Enter Password Or Email to Verify ");
                  loginField.setPrefSize(200.00,50.00);

                  PasswordField passwordField = new PasswordField();

                  TextField visiblePassword = new TextField();


         HBox hbox = new HBox();
         RadioButton viewPasswordButton = new RadioButton();
                viewPasswordButton.setId("LogInButton");
                viewPasswordButton.setOnMouseClicked(event -> {
                    if(viewPasswordButton.isArmed()) {
                        logInPane.getChildren().remove(passwordField);
                        logInPane.add(visiblePassword,0,1,2,1);
                        visiblePassword.setText(passwordField.getText());
                    }else {
                        logInPane.getChildren().remove(visiblePassword);
                        logInPane.add(passwordField,0,1,2,1);
                        passwordField.setText(visiblePassword.getText());
                    }
                });


         Button logInButton = new Button("Log In");
                logInButton.setOnAction(event -> {

                });



    }

    private String conditions(String userName,String passwordField){

        if(userName.isEmpty() && passwordField.isEmpty()){
            return "Please fill in your details";
        }else if(!userName.isEmpty() && passwordField.isEmpty()){
            return "PasswordField cannot be empty";
        }else if(userName.isEmpty() && !passwordField.isEmpty()) {
            return "UserName field cannot be empty";
        }else{

        }

    }


































}
