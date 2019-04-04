/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package covoiturage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import static javafx.geometry.HPos.LEFT;
import static javafx.geometry.HPos.RIGHT;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author user
 */
public class PageMessage extends Fenetre{
    
    private int index;
       
    public PageMessage(Utilisateur user,Discussion disc) throws IOException{
        super(user);
        GridPane grid = new GridPane();
        ScrollPane scroll = new ScrollPane();
        VBox vpane = new VBox();
        ArrayList<String> messagesString=disc.recupererConversation();
        
        String infos ="A : ";
        if(user.nom.equals(disc.getConducteur().nom))
        {
            infos+= disc.getVoyageur().nom;
            index=2;
        }
        else
        {
            infos+= disc.getConducteur().nom;
            index=3;
        }
        infos+=" | Voyage : "+disc.getVoyage() + " | Prix de base : "+Integer.toString(disc.getPrix())+"€";
        
        Label info = new Label(infos);
        info.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(10), Insets.EMPTY)));
        info.setPadding(new Insets(10, 10, 10, 10));
        
        messagesString.forEach((s) -> {
            Label msg = new Label(s);
            Color color= Color.WHITE;
            if(index%2==0){
                GridPane.setHalignment(msg,LEFT);
                grid.add(msg,0,index);
            }else{
                color= Color.LIGHTGREEN;
                GridPane.setHalignment(msg,RIGHT);
                grid.add(msg,1,index);
            }
            
            msg.setBackground(new Background(new BackgroundFill(color, new CornerRadii(10), Insets.EMPTY)));
            msg.setPadding(new Insets(10, 10, 10, 10));
            index++;
        });
        
        Button retour = new Button("Retour");
        retour.setOnAction((ActionEvent t) -> {
            stage.close();
            try {
                new PageAccueilUtil(user);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PageMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        GridPane.setHalignment(retour,RIGHT);
        GridPane.setMargin(retour, new Insets(20, 0, 0, 0));
        grid.add(retour,1,index);
        
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
        vpane.getChildren().addAll(info,grid);
        vpane.setAlignment(Pos.CENTER);
        scroll.setContent(vpane);
        scroll.setFitToWidth(true);
        root.setCenter(scroll);
    }
}

