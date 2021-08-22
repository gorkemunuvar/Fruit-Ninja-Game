import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.Duration;

import java.awt.*;
import java.lang.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ThreadLocalRandom;

public class Game extends Application {
    Timeline gameLoop;
    Pane root = new Pane();
    private static Stage gameStage;
    boolean gameState = false;
    private  Text scoreText, durationText, gameOverText;
    int score = 0, duration = 1;

    LoginScreen loginScreen = new LoginScreen();

    public Text getGameOverText(){ return gameOverText;}

    public static Stage getGameStage() {
        return gameStage;
    }

    private Fruit getRandomFruit(){
        int rnd = (int)(Math.random() * 4);

        if(rnd == 0)
            return new Watermelon();
        else if(rnd == 1)
            return new Orange();
        else if(rnd == 2)
            return new Pomegranate();

        return new Raspberry();
    }

    private void setHalfFruit(String imgUrl, double size, int X, int Y){
        HalfFruits halfFruit = new HalfFruits(imgUrl, size);

        halfFruit.setCenterX(X + 50);
        halfFruit.setCenterY(Y);

        halfFruit.fall();

        root.getChildren().addAll(halfFruit);
    }

    private Fruit createFruit(){
        Fruit fruit = getRandomFruit();

        fruit.setOnMouseDragEntered(event -> {
            changeScore(fruit);

            PointerInfo a = MouseInfo.getPointerInfo();
            Point b = a.getLocation();
            int X = (int) b.getX();
            int Y = (int) b.getY();

            fruit.setVisible(false);

            if(fruit.getClass() == Watermelon.class){
                setHalfFruit("/watermelonone.png", 35.0f, X + 15, Y);
                setHalfFruit("/watermelonone.png", 35.0f, X - 15, Y);
            }
            else if(fruit.getClass() == Orange.class){
                setHalfFruit("/halforange.png", 25.0f, X + 15, Y);
                setHalfFruit("/halforange.png", 25.0f, X - 15, Y);
            }
            else if(fruit.getClass() == Pomegranate.class){
                setHalfFruit("/halfpo.png",  20.0f,X + 15, Y);
                setHalfFruit("/halfpo.png",  20.0f,X - 15, Y);
            }
            else if(fruit.getClass() == Raspberry.class){
                setHalfFruit("/halfrasp.png",  15.0f,X + 15, Y);
                setHalfFruit("/halfrasp.png",  15.0f,X - 15, Y);
            }
        });

        return fruit;
    }

    private void changeScore(Fruit fruit){
        if(gameState){
            score += fruit.getScore();
            scoreText.setText("SCORE: " + score);
        }
    }

    void initGame(){
        gameLoop = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                durationText.setText("Duration:   " + duration);
                int rnd;
                if(duration % 7  == 0){
                    BlackBomb bomb = new BlackBomb();

                    bomb.setOnMouseDragEntered(event -> {
                        bomb.explode();

                        gameOver();
                    });

                    root.getChildren().add(bomb);
                    bomb.jump();
                }

                if(duration % 5 == 0){
                    Fruit fruit;
                    rnd = ThreadLocalRandom.current().nextInt(1, 6);
                    for(int i=0; i<rnd; i++){
                        fruit = createFruit();
                        root.getChildren().add(fruit);
                        fruit.jump();
                    }
                }

                duration++;
            }
        }));

        gameLoop.setCycleCount(-1);
    }

    void initBackground(){
        Button startButton = new Button("Start");
        Button pauseButton = new Button("Pause");

        startButton.setLayoutX(15);
        startButton.setLayoutY(40);
        startButton.setPrefSize(60, 30);

        pauseButton.setLayoutX(15);
        pauseButton.setLayoutY(90);
        pauseButton.setPrefSize(60, 30);

        startButton.setOnMouseClicked(event ->{
            gameLoop.play();
            gameState = true;
        });

        pauseButton.setOnMouseClicked(event ->{
            gameLoop.stop();
            gameState = false;
        });

        Font scoreFont = Font.font("Courier New", FontWeight.BOLD, 30);
        scoreText = new Text("SCORE: " + score);
        scoreText.setFill(javafx.scene.paint.Color.RED);
        scoreText.setFont(scoreFont);
        scoreText.setLayoutX(10);
        scoreText.setLayoutY(25);

        Font durationFont = Font.font("Courier New", FontWeight.BOLD, 30);
        durationText = new Text("Duration: 0");
        durationText.setFill(javafx.scene.paint.Color.RED);
        durationText.setFont(durationFont);
        durationText.setLayoutX(1100);
        durationText.setLayoutY(25);

        Image image = new Image("/background.png");
        BackgroundImage backImg = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background back = new Background(backImg);

        Image img = new Image(getClass().getResourceAsStream("/blade.png"));
        ImageCursor cursor = new ImageCursor(img, 500, 500);

        root.setCursor(cursor);
        root.setBackground(back);
        root.setOnDragDetected(event -> root.startFullDrag());

        root.getChildren().addAll(scoreText, durationText, startButton, pauseButton);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = loginScreen.getLoginScene();

        Button signinButton = loginScreen.getSigninButton();
        Button loginButton = loginScreen.getLoginButton();
        Button leaderButton = loginScreen.getLeaderButton();
        Button mygamesButton = loginScreen.getMyGamesButton();

        signinButton.setOnMouseClicked(event ->  {
            Stage newStage = new Stage();

            RegisterScreen registerScreen = new RegisterScreen();
            Scene registerScene = registerScreen.getRegisterScene();

            newStage.setScene(registerScene);
            newStage.show();
        });

        loginButton.setOnMouseClicked(event -> {
            if(Database.loginControl(loginScreen.getUserNameText(), loginScreen.getPasswordText())){
                System.out.println("Giris basarili");

                initBackground();
                initGame();

                Scene newScene = new Scene(root);

                stage.setScene(newScene);
                stage.setMaximized(true);
                stage.setTitle("Fruit Ninjaaaaa");
            }
            else{
                System.out.println("Hatalı giris");
                loginScreen.getWelcomeText().setText("Hatalı Giriş.");
            }

        });

        leaderButton.setOnMouseClicked((event -> {
            Stage newStage = new Stage();

            newStage.setTitle("Leaderboard");
            Group root = new Group();

            tableview = new TableView();
            buildData("SELECT score, duration, username FROM game ORDER BY score DESC;");

            root.getChildren().add(tableview);

            Scene newScene = new Scene(root,250,400);
            newStage.setScene(newScene);

            newStage.show();
        }));

        mygamesButton.setOnMouseClicked((event -> {
            Stage newStage = new Stage();

            newStage.setTitle("My Games");
            Group root = new Group();

            tableview = new TableView();
            String kAdi = loginScreen.getUserNameText();
            buildData("SELECT score, duration, username FROM game WHERE username='"  + kAdi + "';");

            root.getChildren().add(tableview);

            Scene newScene = new Scene(root,250,400);
            newStage.setScene(newScene);

            newStage.show();
        }));

        stage.setScene(scene);
        stage.show();
    }

    public Scene getGameScene(){
        Scene scene = new Scene(root);

        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void gameOver(){
        gameState = false;
        gameLoop.stop();

        Font gameOverFont = Font.font("Courier New", FontWeight.BOLD, 300);
        gameOverText = new Text("GAME OVER");
        gameOverText.setFill(javafx.scene.paint.Color.RED);
        gameOverText.setFont(gameOverFont);
        gameOverText.setLayoutX(185);
        gameOverText.setLayoutY(500);

        saveScore(loginScreen.getUserNameText(), Integer.toString(duration), score);

        root.getChildren().add(gameOverText);

    }

    public void saveScore(String userName, String drtn, int scr){
        try {
            Statement st = Database.getConnection().createStatement();
            st.executeUpdate("INSERT INTO game(username, duration, score) VALUES('"+
                    userName +"','" + drtn + "','" + scr + "')");
        }catch(Throwable t){
            t.printStackTrace();
        }
    }












    //TABLE VIEW AND DATA
    private ObservableList<ObservableList> data;

    private TableView tableview;

    //CONNECTION DATABASE
    public void buildData(String SQL) {
        Connection c;
        data = FXCollections.observableArrayList();
        try {
            c = Database.getConnection();
            ResultSet rs = c.createStatement().executeQuery(SQL);

            //TABLE COLUMN ADDED DYNAMICALLY
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tableview.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }

            //Data added to ObservableList
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            tableview.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }




}