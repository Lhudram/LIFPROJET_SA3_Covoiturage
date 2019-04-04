/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package covoiturage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author user
 */
public class PageProposerVoyage extends Fenetre{
    public DatePicker Date;
    public TextField Depart;
    public TextField Arrivee;
    public TextField Prix;
    public TextField NbPlaces;
    
    public PageProposerVoyage(Utilisateur util) {
        super(util);
        GridPane grid= new GridPane();
        grid.setPadding(new Insets(20));
        Label labelTitle = new Label("Veuillez entrer les informations ci-dessous pour proposer un voyage");
        grid.add(labelTitle, 0, 0, 2, 1);
        
        Label labelDepart = new Label("Ville de départ : ");
        Depart = new TextField();
        GridPane.setHalignment(labelDepart, HPos.RIGHT);
        grid.add(labelDepart, 0, 1);
        GridPane.setHalignment(Depart, HPos.LEFT);
        grid.add(Depart, 1, 1);
        
        
        Label labelArrivee = new Label("Ville d'arrivée : ");
        Arrivee = new TextField();
        GridPane.setHalignment(labelArrivee, HPos.RIGHT);
        grid.add(labelArrivee, 0, 2);
        GridPane.setHalignment(Arrivee, HPos.LEFT);
        grid.add(Arrivee, 1, 2);
        
        Label labelPrix = new Label("Prix : ");
        Prix = new TextField();
        GridPane.setHalignment(labelPrix, HPos.RIGHT);
        grid.add(labelPrix, 0, 3);
        GridPane.setHalignment(Prix, HPos.LEFT);
        grid.add(Prix, 1, 3);
        
        Label labeNbPlaces = new Label("Nombre de places disponible : ");
        NbPlaces = new TextField();
        GridPane.setHalignment(labeNbPlaces, HPos.RIGHT);
        grid.add(labeNbPlaces, 0, 4);
        GridPane.setHalignment(NbPlaces, HPos.LEFT);
        grid.add(NbPlaces, 1, 4);
        
        Label labelDate = new Label("Date : ");
        Date = new DatePicker();
        GridPane.setHalignment(labelDate, HPos.RIGHT);
        grid.add(labelDate, 0, 5);
        GridPane.setHalignment(Date, HPos.LEFT);
        grid.add(Date, 1, 5);
        
        Button searchButton = new Button("Chercher un trajet");
               
        // Horizontal alignment for search button.
        GridPane.setHalignment(searchButton, HPos.RIGHT);
        grid.add(searchButton, 1, 6);

        searchButton.setOnAction((ActionEvent t) -> {
            try {
                proposer();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PageRecherche.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PageRecherche.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
        root.setCenter(grid);
    }

    public void proposer() throws FileNotFoundException, IOException{
        String depart = Depart.getText();
        String arrivee = Arrivee.getText();
        LocalDate date = Date.getValue();
        
        if (depart.isEmpty() && arrivee.isEmpty() && date == null){
            boiteDialogueError(1);
        } else if (depart.isEmpty() || arrivee.isEmpty()){
            boiteDialogueError(2);
        } else if (date == null){
            boiteDialogueError(3);
        } else{
            Utilisateur conducteur = this.utilisateurRandom();
            //random entre 10 et 1000 compris
            int prixRnd=  (int)(Math.random() * ((1000-10) + 1));
            
            Voyage voyage = new Voyage(3,depart,arrivee);
            Discussion disc = new Discussion(this.util,conducteur,prixRnd,voyage,date.toString());
            
            stage.close();
            voyage.sauvegardeVoyage();
            disc.conversation();
            new PageMessage(this.util,disc);
        }
    }
    //info=1 : Tout vide
    //info=2 : Pas de départ ni d'arrivée
    //info=3 : pas de date
    //info autre : Format date incorrecte
    public void boiteDialogueError(int info){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText("Informations manquantes !");
        switch (info) {
            case 1:
                alert.setContentText("Veuillez entrer soit une ville de départ, soit une ville d'arrivée ainsi qu'une date");
                break;
            case 2:
                alert.setContentText("Veuillez entrer une ville de départ et une ville d'arrivée");
                break;
            case 3:
                alert.setContentText("Veuillez entrer une date");
                break;
            default:
                alert.setContentText("Format de la date incorrecte (Rappel du format : JJ/MM/AAAA");
                break;
        }
        
        alert.showAndWait();
    }
}
