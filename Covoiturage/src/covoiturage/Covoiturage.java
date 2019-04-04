/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package covoiturage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author antoine dulhoste
 */
public class Covoiturage extends Application {
    
    @Override
    public void start(Stage primaryStage){
        PageAccueil pageAccueil = new PageAccueil();
        //Utilisateur u1 = new Utilisateur(50,"Nom","prenom","mdp",false,"a");
        /*ArrayList<Utilisateur> tab = initUtil();
        for (Utilisateur u:tab){
            u.creerPrefUtil();
        }*/
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /* public ArrayList<Utilisateur> initUtil() throws FileNotFoundException{
            ArrayList ar =new ArrayList<Utilisateur>();
            Scanner scanner = new Scanner(new FileReader("utilisateur.txt"));
            while (scanner.hasNextLine()) {
                String util=scanner.nextLine();
                String[] parts = util.split(";");
                for(String x:parts){
                    String[] parts2 = x.split(" ");
                    ar.add(new Utilisateur(Integer.parseInt(parts2[0]),parts2[1],parts2[2],parts2[3],Boolean.parseBoolean(parts2[4]),parts2[5]));
                }
            }
            return ar;
        }*/
    
}
