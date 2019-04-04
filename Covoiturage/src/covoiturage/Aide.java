/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package covoiturage;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author antoine dulhoste
 */
public class Aide extends Fenetre {
    public Aide(Utilisateur util){
        super(util);
        text();
    }
    public void text(){
        Font police = Font.loadFont(Aide.class.getResourceAsStream("/GOTHICB0.TTF"), 20);;
        Text aide=new Text();
        aide.setText("  Logiciel de covoiturage\n\n            crée par     \n\n    Antoine DULHOSTE\n\n      Martin DULHOSTE\n\n      Lucas VIALATOUX");
        aide.setFont(police);
        this.root.setCenter(aide);
    }
}
