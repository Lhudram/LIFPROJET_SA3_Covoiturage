package covoiturage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class PageAccueilUtil extends Fenetre {

    public PageAccueilUtil(Utilisateur util) throws FileNotFoundException {
        super(util);

        TableView<Discussion> tableAnnonces = new TableView();
        TableView<Discussion> tableReservations = new TableView();
        ObservableList<Discussion> dataAnnonces = dataAnnonces();
        ObservableList<Discussion> dataReservations = dataReservations();

        TableColumn dateAnnonces = new TableColumn("Date");
        dateAnnonces.setCellValueFactory(new PropertyValueFactory<Discussion, String>("date"));
        dateAnnonces.setSortType(TableColumn.SortType.DESCENDING);
        TableColumn dateReservations= new TableColumn("Date");
        dateReservations.setCellValueFactory(new PropertyValueFactory<Discussion, String>("date"));
        dateReservations.setSortType(TableColumn.SortType.DESCENDING);
        
        TableColumn voyageAnnonces = new TableColumn("Voyage");
        voyageAnnonces.setCellValueFactory(new PropertyValueFactory<Discussion, String>("voyage"));
        TableColumn voyageReservations = new TableColumn("Voyage");
        voyageReservations.setCellValueFactory(new PropertyValueFactory<Discussion, String>("voyage"));
        
        TableColumn conducteur = new TableColumn("Conducteur");
        conducteur.setCellValueFactory(new PropertyValueFactory<Discussion, String>("nomconducteur"));
        TableColumn voyageur = new TableColumn("Voyageur");
        voyageur.setCellValueFactory(new PropertyValueFactory<Discussion, String>("nomvoyageur"));

        tableReservations.getColumns().addAll(dateReservations, voyageReservations, voyageur);
        tableReservations.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableReservations.setCursor(Cursor.CLOSED_HAND);
        tableReservations.getItems().setAll(dataReservations);
        tableReservations.getSortOrder().add(dateReservations);
        tableReservations.setRowFactory(tv -> {
            TableRow<Discussion> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Discussion rowData = row.getItem();
                    stage.close();
                    try {
                        PageMessage pagemessage = new PageMessage(this.util, rowData);
                    } catch (IOException ex) {
                        Logger.getLogger(PageAccueilUtil.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row;
        });

        tableAnnonces.getColumns().addAll(dateAnnonces, voyageAnnonces, conducteur);
        tableAnnonces.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableAnnonces.setCursor(Cursor.CLOSED_HAND);
        tableAnnonces.getItems().setAll(dataAnnonces);
        tableAnnonces.getSortOrder().add(dateAnnonces);
        tableAnnonces.setRowFactory(tv -> {
            TableRow<Discussion> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Discussion rowData = row.getItem();
                    stage.close();
                    try {
                        PageMessage pagemessage = new PageMessage(this.util, rowData);
                    } catch (IOException ex) {
                        Logger.getLogger(PageAccueilUtil.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row;
        });

        VBox vb = new VBox();
        vb.getChildren().addAll(tableAnnonces, tableReservations);
        root.setCenter(vb);
    }

    public ObservableList<Discussion> dataAnnonces() throws FileNotFoundException {
        ObservableList<Discussion> data = FXCollections.observableArrayList();
        String[] listeDiscussion;
        listeDiscussion = listerRepertoire(new File("messages"));
        for (String s : listeDiscussion) {
            String[] parts = s.split("-");
            if (parts[0].equals(Integer.toString(this.util.id))) {
                data.add(getDisc(s,parts));
            }
        }
        return data;
    }
    public ObservableList<Discussion> dataReservations() throws FileNotFoundException {
        ObservableList<Discussion> data = FXCollections.observableArrayList();
        String[] listeDiscussion;
        listeDiscussion = listerRepertoire(new File("messages"));
        for (String s : listeDiscussion) {
            String[] parts = s.split("-");
            if (parts[1].equals(Integer.toString(this.util.id))) {
                data.add(getDisc(s,parts));
            }
        }
        return data;
    }

    public Discussion getDisc(String s,String[] parts) throws FileNotFoundException{
        Scanner scanner = new Scanner(new FileReader("messages\\" + s));
        String scan = scanner.nextLine();
        String[] discussion = scan.split("#");
        Utilisateur util1 = this.trouverUtil(parts[0]);
        Utilisateur util2 = this.trouverUtil(parts[1]);
        Voyage voyage = new Voyage(3,discussion[1], discussion[2]);
        
        int test = Integer.parseInt("2");
        
        int prix=Integer.parseInt(discussion[0]);
        
        System.out.println(discussion[0]);
        
        Discussion dis = new Discussion(util1, util2, prix, voyage, discussion[3]);
        return dis;
    }
    
    public String[] listerRepertoire(File repertoire) {
        String[] listefichiers;
        listefichiers = repertoire.list();
        return listefichiers;
    }
}
