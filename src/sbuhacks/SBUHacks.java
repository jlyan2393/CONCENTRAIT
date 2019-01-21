/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbuhacks;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Jase
 */
public class SBUHacks extends Application {
    static Integer score = 0;
    static int timerBonus = 0;
    static final int MAX_TIME= 3;
    Integer timerSec=MAX_TIME;
    Label timerText=new Label();
    Label scoreText=new Label("Score: "+score.toString());
    double speed=2;
    ArrayList<Collidables> collidables=new ArrayList();
    Pane gamePane = new Pane();
    ImageView[] wallTraits = new ImageView[5];
    ImageView[] freeTraits = new ImageView[5];
    boolean freeState=true;
    Character trait;
    GridPane losePane = new GridPane();
    
    public static final int ORG_STATE=0;
    public static final int SWAP_STATE=1;
    int game_state=ORG_STATE;
    int flip_state=0;
    Label tempScoreLabel=new Label();
    Timeline timeline;
    Timeline ctimeline;
    GridPane menuScrn = new GridPane();
    Scene mainScene = new Scene(menuScrn, 1200, 900);
    @Override
    public void start(Stage primaryStage) {
        Button playbtn = new Button();
        Button exitbtn = new Button();
        Button resetbtn = new Button();
        Label gameTitle = new Label();
        gameTitle.setText("CONCENTRAIT");
        
        playbtn.setText("PLAY");
        exitbtn.setText("EXIT");
        resetbtn.setText("PLAY AGAIN");
        playbtn.setPrefSize(200, 100);
        exitbtn.setPrefSize(200, 100);
        
        wallTraits[0]=new ImageView(new Image("file:src/resources/hammer.png"));
        wallTraits[1]=new ImageView(new Image("file:src/resources/bomb.png"));
        wallTraits[2]=new ImageView(new Image("file:src/resources/ball.png"));
        wallTraits[3]=new ImageView(new Image("file:src/resources/bullet.png"));
        wallTraits[4]=new ImageView(new Image("file:src/resources/drill.png"));
        
        freeTraits[0]=new ImageView(new Image("file:src/resources/turtle.png"));
        freeTraits[1]=new ImageView(new Image("file:src/resources/women.png"));
        freeTraits[2]=new ImageView(new Image("file:src/resources/men.png"));
        freeTraits[3]=new ImageView(new Image("file:src/resources/dog.png"));
        freeTraits[4]=new ImageView(new Image("file:src/resources/cat.png"));
        
        ImageView randomImage=freeTraits[(int)(Math.random()*5)];
        trait = new Character(50,50,randomImage);
        
        menuScrn.setVgap(50);
        menuScrn.setAlignment(Pos.CENTER);
        
        menuScrn.add(gameTitle, 0,0);
        menuScrn.add(playbtn, 0,1);
        menuScrn.add(exitbtn, 0,2);
        GridPane.setConstraints(gameTitle, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(playbtn, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(exitbtn, 0, 2, 1, 1, HPos.CENTER, VPos.CENTER);
        gameTitle.setStyle("-fx-font-size:50; -fx-font-family:'Arial Black'");
        playbtn.setStyle("-fx-font-size:20; -fx-font-family:'Arial Black'; -fx-border-style:solid; -fx-border-width:5");
        exitbtn.setStyle("-fx-font-size:20; -fx-font-family:'Arial Black'; -fx-border-style:solid; -fx-border-width:5");
        
        Circle circle= new Circle(300,300,10);
        
        losePane.add(tempScoreLabel,0,1);
        ctimeline = new Timeline();
        ctimeline.setCycleCount(Timeline.INDEFINITE);
        ctimeline.getKeyFrames().add(new KeyFrame(Duration.millis(speed), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(int i=0; i < collidables.size(); i++){
                    Collidables c=collidables.get(i);
                    boolean intersects=trait.getShape().intersects(c.getShape().getBoundsInParent());
                    boolean correctColid=checkCorrectCollid(c);
                    c.setX(c.getX()-1);
                    if(c.getX()<0){
                        gamePane.getChildren().remove(c.getShape());
                        collidables.remove(i);
                        continue;
                    }
                    if(correctColid && intersects){
                        if(!c.isCurrentlyIntersecting()){
                            Label loseLabel=new Label("   You Lose! \nYour Score is");
                            losePane.add(loseLabel,0,0);
                            tempScoreLabel.setText(score.toString());
                            mainScene.setRoot(losePane);
                            losePane.add(resetbtn,0,2);
                            ctimeline.stop();
                            Button exitbtn2=new Button("Exit");
                            exitbtn2.setOnKeyPressed(e->{
                                if(e.getCode()==KeyCode.ENTER){
                                    primaryStage.close();
                                }
                            });
                            exitbtn2.setOnAction(e->{
                                primaryStage.close();
                            });
                            losePane.add(exitbtn2,0,3);
                            losePane.setVgap(50);
                            losePane.setAlignment(Pos.CENTER);
                            GridPane.setConstraints(loseLabel, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);
                            GridPane.setConstraints(tempScoreLabel, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
                            GridPane.setConstraints(resetbtn, 0, 2, 1, 1, HPos.CENTER, VPos.CENTER);
                            GridPane.setConstraints(exitbtn2, 0, 3, 1, 1, HPos.CENTER, VPos.CENTER);
                            loseLabel.setStyle("-fx-font-size:50; -fx-font-family:'Arial Black'");
                            tempScoreLabel.setStyle("-fx-font-size:50; -fx-font-family:'Arial Black'");
                            resetbtn.setStyle("-fx-font-size:20; -fx-font-family:'Arial Black'; -fx-border-style:solid; -fx-border-width:5");
                            exitbtn2.setStyle("-fx-font-size:20; -fx-font-family:'Arial Black'; -fx-border-style:solid; -fx-border-width:5");
                        }
                        return;
                    }
                    if(!correctColid && intersects){
                        if(!c.isCurrentlyIntersecting()){
                            c.setCurrentlyIntersecting(true);
                            score+=1;
                            scoreText.setText("Score: "+score.toString());
                            int stateSwapChance = (int)(Math.random()*101);
                            if(stateSwapChance <= 50){
                                System.out.println("Flipped");
                                flip_state=1;
                            }
                        }
                        continue; 
                    }
                    if(!intersects){
                        c.setCurrentlyIntersecting(false);
                    }
                }
                if(collidables.size()==0){
                    if(flip_state==1){
                        if(game_state==ORG_STATE){
                            game_state=SWAP_STATE;
                            trait.setImg(wallTraits[(int)(Math.random()*5)]);
                        }
                        else{
                            game_state=ORG_STATE;
                            trait.setImg(freeTraits[(int)(Math.random()*5)]);
                        }
                        flip_state=0;
                    }
                    createColumn();
                }
                
            }
        }));
        
        
        EventHandler<ActionEvent> playGame = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainScene.setRoot(gamePane);
                startTimer();
                ctimeline.playFromStart();
                gamePane.requestFocus();
            }
        };
        playbtn.setOnAction(playGame);
        playbtn.setOnKeyPressed(new EventHandler<KeyEvent>() {
            
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    mainScene.setRoot(gamePane);
                    startTimer();
                    ctimeline.playFromStart();
                    gamePane.requestFocus();
                }
            }
        });
        exitbtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
        exitbtn.setOnKeyPressed(new EventHandler<KeyEvent>() {
            
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    primaryStage.close();
                }
            }
        });
        resetbtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                reset();
                start(primaryStage);
                playbtn.fire();
            }
        });
        resetbtn.setOnKeyPressed(new EventHandler<KeyEvent>() {
            
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    reset();
                    start(primaryStage);
                    playbtn.fire();
                }
            }
        });
        
        timerText.setTranslateX(590);
        scoreText.setTranslateX(515);
        scoreText.setTranslateY(75);
        timerText.setStyle("-fx-font-size:48");
        scoreText.setStyle("-fx-font-size:48");
        
        primaryStage.setTitle("Concentrait");
        primaryStage.centerOnScreen();
        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.setResizable(false);
        
        gamePane.getChildren().addAll(trait.getShape(),trait.getImg(),timerText,scoreText);
        
        gamePane.setOnKeyPressed(e->{
            if(e.getCode()==KeyCode.W || e.getCode()==KeyCode.UP){
                trait.move(DirectionConstants.UP);
            }
            if(e.getCode()==KeyCode.S || e.getCode()==KeyCode.DOWN){
                trait.move(DirectionConstants.DOWN);
            }
        });
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    public void startTimer(){
        System.out.println(speed);
        timerSec=MAX_TIME;
        timerText.setText(timerSec.toString());
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Where I will change it
                timerSec--;
                timerBonus+=(timerSec);
                timerText.setText(timerSec.toString());
                if(timerSec==0){
                    timerBonus=0;
                    if(speed>0.1){
                        speed-=0.1;
                    }
                    timeline.stop();
                    startTimer();
                }
            }
        }));
        timeline.playFromStart();
    }
    
    public void createColumn(){
        int colHeight = 0;
        if ((int)(Math.random()*2)+1 == 1){
            collidables.add(new Obstacle(1150,0));
            colHeight+=collidables.get(collidables.size()-1).getShape().getHeight();
            gamePane.getChildren().add(collidables.get(collidables.size()-1).getShape());
        }
        else{
           collidables.add(new PathWay(1150,0));
           colHeight+=collidables.get(collidables.size()-1).getShape().getHeight();
           gamePane.getChildren().add(collidables.get(collidables.size()-1).getShape());
        }
        while(colHeight != 1000){
            
            if (colHeight>900){
                collidables.get(collidables.size()-1).getShape().setHeight(collidables.get(collidables.size()-1).getShape().getHeight()+colHeight+50);
                colHeight=1000;
                break;
            }
            if (collidables.get(collidables.size()-1) instanceof PathWay){
                collidables.add(new Obstacle(1150,colHeight));
                colHeight+=collidables.get(collidables.size()-1).getShape().getHeight();
                gamePane.getChildren().add(collidables.get(collidables.size()-1).getShape());
            }
            else{
                collidables.add(new PathWay(1150,colHeight));
                colHeight+=collidables.get(collidables.size()-1).getShape().getHeight();
                gamePane.getChildren().add(collidables.get(collidables.size()-1).getShape());
            }
        }
    }
    
    public boolean checkCorrectCollid(Collidables c){
        if(c.getCollidable() && game_state==ORG_STATE){
            return true;
        }
        if(c.getCollidable() && game_state==SWAP_STATE){
            return false;
        }
        if(!c.getCollidable() && game_state==ORG_STATE){
            return false;
        }
        if(!c.getCollidable() && game_state==SWAP_STATE){
            return true;
        }
        System.out.println("ERROR");
        return true;
    }
    
    public void reset(){
                menuScrn.getChildren().removeAll(menuScrn.getChildren());
                gamePane.getChildren().removeAll(gamePane.getChildren());
                losePane.getChildren().removeAll(losePane.getChildren());
                ctimeline.stop();
                timeline.stop();
                score = 0;
                collidables=new ArrayList();
                timerBonus = 0;
                timerSec=MAX_TIME;
                timerText=new Label();
                scoreText=new Label("Score: "+score.toString());
                speed=2;
                collidables=new ArrayList();
                gamePane = new Pane();
                wallTraits = new ImageView[5];
                freeTraits = new ImageView[5];
                freeState=true;
                losePane = new GridPane();
                game_state=ORG_STATE;
                flip_state=0;
                tempScoreLabel=new Label();
                
                mainScene.setRoot(menuScrn);
    }
}
