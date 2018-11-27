package butler_java2_finalproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.*;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.WindowEvent;

/**
 *
 * @author tfran
 */
public class Butler_Java2_FinalProject extends Application implements Serializable {
    
    ButtonHand bh;
    //PurchaseHand ph = new PurchaseHand();
    
    @Override
    public void start(Stage primaryStage) {
       //Initialize Theater Array of Objects
       Theater[] theaterArr = new Theater[4];
       
       Theater t1 = new Theater("Tarzan");
       for(int i = 0; i < theaterArr.length; i++){
           theaterArr[i] = null;
       }

       //Initialize TextField Elements
       TextField tf1 = new TextField();
       TextField tf2 = new TextField();
       TextField tf3 = new TextField();
       TextField tf4 = new TextField();
       
       //Intialize Pane Elements
       BorderPane bPane = new BorderPane();
       bPane.setPadding(new Insets(5, 5, 5, 5));
       Scene scene = new Scene(bPane, 350, 200);
       
       //Stage Set-up
       primaryStage.setTitle("Movie Theater Booking System");
       primaryStage.setScene(scene);
       primaryStage.show();
       primaryStage.sizeToScene();
       
       //Check for Serialized Object Data, If Found Deserialize
       if(new File("Objects.txt").isFile()){
           try{
               FileInputStream fileIn = new FileInputStream("Objects.txt");
               ObjectInputStream in = new ObjectInputStream(fileIn);
               theaterArr[0] = (Theater) in.readObject();
               theaterArr[1] = (Theater) in.readObject();
               theaterArr[2] = (Theater) in.readObject();
               theaterArr[3] = (Theater) in.readObject();
           }
           catch (IOException | ClassNotFoundException i) {
               System.out.println("Class Not Found");
           }
           
           System.out.println("Serializing Data.");
           buildTheaterMainPanel(theaterArr, bPane, primaryStage);
       }
       else
       {
          buildTheaterAssignPanel(tf1, tf2, tf3, tf4, bPane, primaryStage, theaterArr);
       }
       
       
       
       //Event Handler to Serialize Object Data before application closes
       primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
           @Override
           public void handle(WindowEvent event) {
               try{
                   FileOutputStream fileOut = new FileOutputStream("Objects.txt");
                   ObjectOutputStream out = new ObjectOutputStream(fileOut);
                   out.writeObject(theaterArr[0]);
                   out.writeObject(theaterArr[1]);
                   out.writeObject(theaterArr[2]);
                   out.writeObject(theaterArr[3]);
                   
               }
               catch(IOException i){
                   System.out.println("Objects not found!");
               }
               
               System.out.print("Deserializing Data");
           }
       });
       
       
       
    }
    
    public static void buildTheaterAssignPanel(TextField tf1, TextField tf2, TextField tf3, TextField tf4, BorderPane bPane, Stage primaryStage, Theater[] theaterArr){
        //Initialization of content objects
        GridPane gPane1 = new GridPane();
        Button assignB = new Button("Assign Films");
        
        //EventListener for Assign Button
        assignB.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               theaterArr[0] = new Theater(tf1.getText());
               theaterArr[1] = new Theater(tf2.getText());
               theaterArr[2] = new Theater(tf3.getText());
               theaterArr[3] = new Theater(tf4.getText());
               
               buildTheaterMainPanel(theaterArr, bPane, primaryStage);
               primaryStage.sizeToScene();
           }
       });
        
        //Building/Adding of actual JavaFX Elements 
        gPane1.add(new Label("Enter film name for Theater #1"), 0, 0);
        gPane1.add(tf1, 1, 0);
        gPane1.add(new Label("Enter film name for Theater #2"), 0, 1);
        gPane1.add(tf2, 1, 1);
        gPane1.add(new Label("Enter film name for Theater #3"), 0, 2);
        gPane1.add(tf3, 1, 2);
        gPane1.add(new Label("Enter film name for Theater #4"), 0, 3);
        gPane1.add(tf4, 1, 3);
        gPane1.setAlignment(Pos.CENTER);
        
        //Set BorderPane Elements
        bPane.setTop(gPane1);
        bPane.setCenter(assignB);
    }
    
    public static void buildTheaterMainPanel(Theater[] theaterArr, BorderPane bPane, Stage primaryStage){
        //Initialization of Content Objects
        GridPane gPane2 = new GridPane();
        int count1;
        int count2;
        ImageView[] imv = new ImageView[4];
        Label[] theaterLabel = new Label[4];
        Label[] filmLabel = new Label[4];
        VBox[] vba = new VBox[5];
        Button[][] timeButton = new Button[4][3];
        
        //Assignment For Loops
        for(int i = 0; i < imv.length; i++){
            imv[i] = new ImageView();
            imv[i].setImage(new Image("images/TheaterPicture.jpg"));
            imv[i].setFitHeight(200);
            imv[i].setFitWidth(125);
        }
        for(int i = 0; i < theaterLabel.length; i++){
            theaterLabel[i] = new Label("Theater " + (i + 1)); 
        }
        for(int i = 0; i < vba.length; i++){
            vba[i] = new VBox();
            vba[i].setAlignment(Pos.CENTER);
            vba[i].setSpacing(3);
        }
        for(int i = 0; i < filmLabel.length; i++){
            filmLabel[i] = new Label(theaterArr[i].f[0].getFilmName());
        }
       
        for(int i = 0; i < 4; i++){
            for(int c = 0; c < 3; c++){
                timeButton[i][c] = new Button(theaterArr[0].f[c].getFilmTime());
                timeButton[i][c].setPrefSize(100, 20);
                
                switch(i){
                    case 0:
                        switch(c){
                            case 0:
                                timeButton[i][c].setOnAction((ActionEvent event) -> {
                                    buildFilmRoomPanel(theaterArr, bPane, primaryStage, 0, 0);
                                });
                                break;
                            case 1:
                                timeButton[i][c].setOnAction((ActionEvent event) -> {
                                    buildFilmRoomPanel(theaterArr, bPane, primaryStage, 0, 1);
                                });
                                break;
                            case 2:
                                timeButton[i][c].setOnAction((ActionEvent event) -> {
                                    buildFilmRoomPanel(theaterArr, bPane, primaryStage, 0, 2);
                                });
                                break;
                        }
                    case 1:
                        switch(c){
                            case 0:
                                timeButton[i][c].setOnAction((ActionEvent event) -> {
                                    buildFilmRoomPanel(theaterArr, bPane, primaryStage, 1, 0);
                                });
                                break;
                            case 1:
                                timeButton[i][c].setOnAction((ActionEvent event) -> {
                                    buildFilmRoomPanel(theaterArr, bPane, primaryStage, 1, 1);
                                });
                                break;
                            case 2:
                                timeButton[i][c].setOnAction((ActionEvent event) -> {
                                    buildFilmRoomPanel(theaterArr, bPane, primaryStage, 1, 2);
                                });
                                break;
                        }
                    case 2:
                        switch(c){
                            case 0:
                                timeButton[i][c].setOnAction((ActionEvent event) -> {
                                    buildFilmRoomPanel(theaterArr, bPane, primaryStage, 2, 0);
                                });
                                break;
                            case 1:
                                timeButton[i][c].setOnAction((ActionEvent event) -> {
                                    buildFilmRoomPanel(theaterArr, bPane, primaryStage, 2, 1);
                                });
                                break;
                            case 2:
                                timeButton[i][c].setOnAction((ActionEvent event) -> {
                                    buildFilmRoomPanel(theaterArr, bPane, primaryStage, 2, 2);
                                });
                                break;
                        }
                    case 3:
                        switch(c){
                            case 0:
                                timeButton[i][c].setOnAction((ActionEvent event) -> {
                                    buildFilmRoomPanel(theaterArr, bPane, primaryStage, 3, 0);
                                });
                                break;
                            case 1:
                                timeButton[i][c].setOnAction((ActionEvent event) -> {
                                    buildFilmRoomPanel(theaterArr, bPane, primaryStage, 3, 1);
                                });
                                break;
                            case 2:
                                timeButton[i][c].setOnAction((ActionEvent event) -> {
                                    buildFilmRoomPanel(theaterArr, bPane, primaryStage, 3, 2);
                                });
                                break;
                        }
                        
                }
            }
        }
        
        Button b1 = new Button("Current Status");
        Button b2 = new Button("Viewer Total");
        b1.setPrefSize(100, 20);
        b2.setPrefSize(100, 20);

        vba[0].getChildren().addAll(b1, b2);
        vba[1].getChildren().addAll(theaterLabel[0], imv[0], filmLabel[0], timeButton[0][0], timeButton[0][1], timeButton[0][2]);
        vba[2].getChildren().addAll(theaterLabel[1], imv[1], filmLabel[1], timeButton[1][0], timeButton[1][1], timeButton[1][2]);
        vba[3].getChildren().addAll(theaterLabel[2], imv[2], filmLabel[2], timeButton[2][0], timeButton[2][1], timeButton[2][2]);
        vba[4].getChildren().addAll(theaterLabel[3], imv[3], filmLabel[3], timeButton[3][0], timeButton[3][1], timeButton[3][2]);
        
        gPane2.add(vba[1], 0, 0);
        gPane2.add(vba[2], 1, 0);
        gPane2.add(vba[3], 2, 0);
        gPane2.add(vba[4], 3, 0);
        gPane2.setHgap(6);
        gPane2.setPadding(new Insets(0, 0, 0, 5));
        
        bPane.setLeft(vba[0]);
        bPane.setTop(null);
        bPane.setCenter(gPane2);
        bPane.setBottom(null);

        primaryStage.setMinWidth(650);
        primaryStage.setMinHeight(375);
    }
    
    
    public static void buildFilmRoomPanel(Theater[] theaterArr, BorderPane bPane, Stage primaryStage, int a, int b){
        //Pane Assignments
        VBox vb1 = new VBox();
        vb1.setAlignment(Pos.CENTER);
        GridPane gp1 = new GridPane();
        
        //Previous Page Button Initialization
        Button back = new Button("Previous Page");
        back.setOnAction((ActionEvent event) -> {
                buildTheaterMainPanel(theaterArr, bPane, primaryStage);
             });
        
        Button[] seat = new Button[50];
        
        //Purchase Button Initialization
        Button purchase = new Button("Purchase Tickets");
        purchase.setOnAction(new PurchaseHand(theaterArr, seat, a, b));
        
        //Key Code Button Initialization
        Button bgrey = new Button();
        Button byellow = new Button();
        Button bred = new Button();
        
        //Change Key Code Button Settings
        bgrey.setMinSize(30, 25);
        byellow.setMinSize(30, 25);
        bred.setMinSize(30, 25);
        bgrey.setDisable(true);
        byellow.setStyle("-fx-background-color: yellow");
        bred.setStyle("-fx-background-color: red");
        
        //Label Intialization
        Label l1 = new Label("= Unselected");
        Label l2 = new Label("= Selected");
        Label l3 = new Label("= Unavailable");
        
        //Add Content to GridPane
        gp1.add(bgrey, 0, 0);
        gp1.add(l1, 1, 0);
        gp1.add(byellow, 0, 1);
        gp1.add(l2, 1, 1);
        gp1.add(bred, 0, 2);
        gp1.add(l3, 1, 2);
        gp1.setHgap(5);
        gp1.setVgap(5);
        gp1.setAlignment(Pos.CENTER);
        
        
        
        
        
        TilePane tp1 = new TilePane();
        tp1.setMaxSize(300, 300);
        tp1.setAlignment(Pos.CENTER);
        tp1.setHgap(3);
        tp1.setVgap(3);
        
        
        if(seat[1] == null){
           for(int i = 0; i < seat.length; i++){
            seat[i] = new Button("" + (i + 1));
            seat[i].setMaxSize(50, 25);
            tp1.getChildren().add(seat[i]);
            seat[i].setOnAction(new ButtonHand(theaterArr, seat, a, b, i));
            } 
        }
        
        
        vb1.setSpacing(20);
        vb1.getChildren().addAll(tp1, purchase, back);
        
        
        bPane.setLeft(gp1);
        bPane.setCenter(vb1);
        

    }
    
}


