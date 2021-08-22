import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.sql.Connection;
import java.sql.ResultSet;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class LoginScreen {
    private Button loginButton ;
    private Button signinButton;
    private Button leaderButton;
    private Button myGamesButton;

    private String passwordText;
    private String userNameText;

    TextField txtUsername = new TextField();
    PasswordField txtPassword = new PasswordField();
    Text scenetitle = new Text("Welcome");

    public Button getLoginButton(){
        return loginButton ;
    }
    public Button getSigninButton(){
        return signinButton;
    }
    public Button getLeaderButton(){
        return leaderButton;
    }
    public Button getMyGamesButton(){
        return myGamesButton;
    }
    public String getUserNameText() {
        return txtUsername.getText();
    }
    public String getPasswordText() { return txtPassword.getText(); }
    public Text getWelcomeText(){ return scenetitle; }


    public Scene getLoginScene(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 450, 275);


        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        grid.add(txtUsername, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        grid.add(txtPassword, 1, 2);

        signinButton = new Button("Sign in");
        loginButton = new Button("Login");
        leaderButton = new Button("Leaderboard");
        myGamesButton = new Button("My Games");

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(signinButton, loginButton);
        grid.add(hbBtn, 1, 4);

        HBox hbBtn2 = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(leaderButton, myGamesButton);
        grid.add(leaderButton, 1, 5);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 11, 6);

        return scene;
    }
}
