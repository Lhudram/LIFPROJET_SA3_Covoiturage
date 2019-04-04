package covoiturage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
/**
 *
 * @author lucas
 */
public class PageUtilisateur{
    public TextField fieldLastName;
    public TextField fieldFirstName;
    public TextField fieldEmail;
    public PasswordField fieldPassword;
    public Stage stage;
    public BorderPane root;
    
    public PageUtilisateur(){
        this.stage=new Stage();
        this.root = new BorderPane();
        Scene scene = new Scene(this.root, 1200, 675);
        this.stage.setScene(scene);
        this.stage.show();
        this.stage.setTitle("Covoiturage");
        String imageURI = new File("icone.jpg").toURI().toString(); 
        Image image = new Image(imageURI);
        this.stage.getIcons().add(image);
        
        root.setStyle("-fx-background-color: #efefef;");        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        Label labelTitle = new Label("Veuillez entrer les informations suivantes vous concernant");
 
        // Put on cell (0,0), span 2 column, 1 row.
        grid.add(labelTitle, 0, 0, 2, 1);
        
        Label labelLastName = new Label("Nom : ");
        fieldLastName = new TextField();
        
        Label labelFirstName = new Label("Prénom : ");
        fieldFirstName = new TextField();
        
        Label labelEmail = new Label("Adresse mail : ");
        fieldEmail = new TextField();
        
        Label labelPassword = new Label("Mot de passe :");
        fieldPassword = new PasswordField();
        
        Button createButton = new Button("Créer");
        createButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent t) {
                                    try {
                                        Creation();
                                    } catch (IOException ex) {
                                        Logger.getLogger(PageUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });
        
        Button backButton = new Button("Retour");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent t) {
                                        stage.close();
                                        new PageAccueil();
                                }
                            });
 
       // Put on cell (0,1)
       GridPane.setHalignment(labelLastName, HPos.RIGHT);
       grid.add(labelLastName, 0, 1);
       
       GridPane.setHalignment(labelFirstName, HPos.RIGHT);
       grid.add(labelFirstName, 0, 2);
       
       GridPane.setHalignment(labelEmail, HPos.RIGHT);
       grid.add(labelEmail, 0, 3);
       
       GridPane.setHalignment(labelPassword, HPos.RIGHT);
       grid.add(labelPassword, 0, 4);
 
       // Horizontal alignment for last Name field.
       GridPane.setHalignment(fieldLastName, HPos.LEFT);
       grid.add(fieldLastName, 1, 1);
       
       // Horizontal alignment for first Name field.
       GridPane.setHalignment(fieldFirstName, HPos.LEFT);
       grid.add(fieldFirstName, 1, 2);
       
       // Horizontal alignment for e-mail field.
       GridPane.setHalignment(fieldEmail, HPos.LEFT);
       grid.add(fieldEmail, 1, 3);
       
       // Horizontal alignment for password field.
       GridPane.setHalignment(fieldPassword, HPos.LEFT);
       grid.add(fieldPassword, 1, 4);
        
       // Horizontal alignment for create button.
       GridPane.setHalignment(createButton, HPos.RIGHT);
       grid.add(createButton, 1, 5);
       
       // Horizontal alignment for back button.
       GridPane.setHalignment(backButton, HPos.LEFT);
       grid.add(backButton, 1, 5);

       grid.setVgap(4);
       grid.setHgap(10);
       grid.setAlignment(Pos.CENTER);
       root.setCenter(grid);
        
       
    }
    public boolean Creation() throws IOException{
        String lastName = fieldLastName.getText();
        String firstName = fieldFirstName.getText();
        String email = fieldEmail.getText();
        String pwd = fieldPassword.getText();
        
        if (lastName.isEmpty() || firstName.isEmpty()
                || email.isEmpty() || pwd.isEmpty() || userExist(email)){
            if (userExist(email)){
                boiteDeDialogueMail();
            } else {
                boiteDeDialogueE();
            }
            return false;
        } else {
            ArrayList<Integer> tabPref = new ArrayList(10);
            Utilisateur u = new Utilisateur(lastID(),lastName,firstName,pwd,false,email);
            u.creerUtil();
            boiteDeDialogueI();
            return true; 
        }

        
        
        
    }
    
    public int lastID() throws FileNotFoundException{
        int lastID=0;
        Scanner scanner = new Scanner(new FileReader("utilisateur.txt"));
        while (scanner.hasNextLine()) {
            String util=scanner.nextLine();
            String[] parts = util.split(";");
            for(String x:parts){
                String[] parts2 = x.split(" ");
                lastID =Integer.parseInt(parts2[0]); 
            }
        }
        return lastID+1;
    }
    
    public boolean userExist(String newmail) throws FileNotFoundException{
        String mail = "";
        Scanner scanner = new Scanner(new FileReader("utilisateur.txt"));
        while (scanner.hasNextLine()) {
            String util=scanner.nextLine();
            String[] parts = util.split(";");
            for(String x:parts){
                String[] parts2 = x.split(" ");
                mail =parts2[5];
                if (newmail.equals(mail)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public void boiteDeDialogueE(){ 
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Inscription");
        alert.setHeaderText("ERREUR!");
        alert.setContentText("Information manquantes !");
        alert.showAndWait();   
    }
    
    public void boiteDeDialogueMail(){ 
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Inscription");
        alert.setHeaderText("ERREUR!");
        alert.setContentText("Un compte est déjà associé à cet e-mail !");
        alert.showAndWait();   
    }
    
    public void boiteDeDialogueI(){ 
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.setTitle("Inscription");
        alert.setHeaderText("Inscription validée");
        alert.setContentText("Merci pour votre inscription !");
        Optional<ButtonType> result = alert.showAndWait();
        //button ok pressed
        if(result.isPresent() && result.get() == ButtonType.OK ){
            stage.close();
            new PageAccueil();
        }
    }
}
