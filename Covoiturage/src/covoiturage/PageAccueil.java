package covoiturage;
import javafx.scene.input.KeyEvent;
import javafx.geometry.Insets;
import java.io.File;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 *
 * @author p1606751
 */
public class PageAccueil{
    
    public TextField user;
    public PasswordField mdp;
    public Stage stage;
    public Pane root;
    public Platform platform;
    public boolean userEmpty, mdpEmpty;
    public Button btnConnexion;
    
    

    public PageAccueil(){
        this.stage=new Stage();
        this.root = new Pane();
        String imageURI = new File("icone.jpg").toURI().toString(); 
        Image image = new Image(imageURI);
        this.stage.getIcons().add(image);
        Text t = new Text();
        t.setText("Identifiants   : ");
        t.setFont(Font.font ("Comic Sans MS",FontWeight.BOLD,20));
        t.setFill(Color.RED);
        Text t1 = new Text();
        t1.setText("Mot de passe :");
        t1.setFont(Font.font ("Comic Sans MS",FontWeight.BOLD,20));
        t1.setFill(Color.RED);
        user = new TextField ();
        mdp = new PasswordField();
        btnConnexion = new Button();
        btnConnexion.setText("Connexion");
        btnConnexion.setOnAction(new Connexion(this));
        Button btnInscription = new Button();
        btnInscription.setText("Pas encore inscrit ?");
        String imageURI2 = new File("fond.png").toURI().toString(); 
        Image image2 = new Image(imageURI2);
        ImageView imageView = new ImageView(image2); 
        imageView.setFitWidth(1200); 
        imageView.setFitHeight(675); 
        root.getChildren().setAll(imageView);
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(t,45 , 60);
        grid.add(user, 46, 60);
        grid.add(t1, 45, 65);
        grid.add(mdp, 46, 65);
        grid.add(btnConnexion, 46, 70);
        grid.add(btnInscription, 46, 75);
        btnInscription.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent t) {
                                    stage.close();
                                    new PageUtilisateur();
                                }
                            });
        root.getChildren().add(grid);
        Scene scene = new Scene(root, 1200, 675);
        scene.addEventFilter(KeyEvent.KEY_PRESSED,new ActionEntree(this));
        stage.setTitle("Covoiturage"); 
        stage.setScene(scene);
        stage.show();
        //Listener sur Textfields utilisateur + mdp
        btnConnexion.setDisable(true);
        userEmpty=true;
        mdpEmpty=true;
        //On ajoute les listeners
        user.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            userEmpty = newValue.trim().isEmpty();
            //On appelle la fonction pour rendre visible si les 2 champs sont remplis
            disableConnexion(userEmpty,mdpEmpty,btnConnexion);
        });
        mdp.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            mdpEmpty = newValue.trim().isEmpty();
            disableConnexion(userEmpty,mdpEmpty,btnConnexion);
        });
    }
    //Fonction qui test si les 2 champs sont remplis
    public void disableConnexion(boolean userEmpty, boolean mdpEmpty, Button btnConnexion){
        if(userEmpty==false && mdpEmpty==false){
            btnConnexion.setDisable(false);
        } else {
            btnConnexion.setDisable(true);
        }
    }
    public class ActionEntree implements EventHandler<KeyEvent>{
        public PageAccueil p;
        public boolean b;
        public ActionEntree(PageAccueil p){
            this.p=p;
            this.b=false;
        }
        @Override
          public void handle(KeyEvent e) {
              if(e.getCode() == KeyCode.ENTER && !this.p.btnConnexion.isDisable()&&b==true){
                  b=false;
                  Connexion c=new Connexion(this.p);
                  ActionEvent event = null;
                  c.handle(event);
              }else{
                  b=true;
              }
        }
    }
}
