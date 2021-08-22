import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.Context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegisterScreen {
    final Text actiontarget = new Text();

    public Scene getRegisterScene(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 450, 275);

        Text scenetitle = new Text("Register Form");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField txtUserName = new TextField();
        grid.add(txtUserName, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField txtPassword = new PasswordField();
        grid.add(txtPassword, 1, 2);

        Label email = new Label("E-mail:");
        grid.add(email, 0, 3);

        TextField txtEmail = new TextField();
        grid.add(txtEmail, 1, 3);

        Button signinButton = new Button("Sign in");

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(signinButton);
        grid.add(hbBtn, 0, 5);


        grid.add(actiontarget, 11, 6);

        signinButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //delete();
                createAccount(txtUserName.getText(), txtPassword.getText(), txtEmail.getText());
            }
        });

        return scene;
    }

    public void createAccount(String userName, String pass, String email){
        try{
            actiontarget.setFill(Color.FIREBRICK);

            if(Database.searchRecord(userName))
                actiontarget.setText("Invalid username.");
            else{
                Statement st=Database.getConnection().createStatement();
                st.executeUpdate("INSERT INTO users(username,password,email) VALUES('"+
                        userName +"','" + pass + "','" + email + "')");

                actiontarget.setText("Successfully registered.");
            }
        }
        catch(Throwable ex){
            ex.printStackTrace();
        }
    }

    public void delete(){
        try{
            actiontarget.setFill(Color.FIREBRICK);
            Statement st=Database.getConnection().createStatement();
            st.executeUpdate("DELETE FROM users");
        }
        catch(Throwable ex){
            ex.printStackTrace();
        }
    }

}
