/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package covoiturage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class Discussion {

    private Utilisateur voyageur;
    private String nomvoyageur;
    private Utilisateur conducteur;
    private String nomconducteur;
    private int prix;
    private Voyage voyage;
            
    public Discussion(Utilisateur user1,Utilisateur user2, int prix, Voyage voyage,String date){
        this.voyageur=user1;
        this.nomvoyageur=this.voyageur.nom;
        this.conducteur=user2;
        this.nomconducteur=this.conducteur.nom;
        this.prix=prix;
        this.voyage=voyage;
    }
   
    public void enregisterConversation(ArrayList<String> messages) throws IOException{
        String ajout=this.prix+"#"+this.voyage.getVilleArrivee()+"#"+this.voyage.getVilleDepart()+"#"+this.voyage.getDate()+"#\n";
        for(String s : messages){
            ajout+=s+";\n";
        }
        
        String path = "messages/"+this.getVoyageur().id+"-"+this.getConducteur().id+"-"+this.voyage.getId()+".txt";
        
        FileWriter fw = new FileWriter(path,true);
        fw.write(ajout);
        fw.close();
    }
    
    public ArrayList<String> recupererConversation() throws IOException{
        String path = this.voyageur.id+"-"+this.conducteur.id+"-"+this.voyage.getId()+".txt";
        
        File f = new File("messages/"+path);
        ArrayList<String> str=new ArrayList();
        if(f.exists() && !f.isDirectory()) { 
            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()){
                String temp = sc.nextLine();
                if(!"#".equals(temp.substring(temp.length() - 1))){
                    temp=temp.substring(0,temp.length() - 1);
                    str.add(temp);
                }                    
            }
        }
        return str;
    }
    
    public ArrayList<String> recupererPreference(boolean estConducteur) throws FileNotFoundException{
        String pattern;
        if (estConducteur){
            pattern = this.conducteur.id+"";
        } else {
            pattern = this.voyageur.id+"";
        }
        ArrayList<String> str2=new ArrayList();
        Scanner scanner = new Scanner(new FileReader("preference/"+pattern+".txt"));
        while (scanner.hasNextLine()) {
            String utili=scanner.nextLine();
            String[] parts = utili.split(";");
            for(String x:parts){
                str2.add(x);
            }
        }
        return str2;
    }
    
    @Override
    public String toString(){
        return this.getPrix()+" "+this.getVoyage()+" "+this.voyage.getId();
    }
    
    public void conversation() throws IOException{
        ArrayList messages = new ArrayList<>();
        boolean estUtilisateur = false;
        //Tableaux des préférences de l'utilisateur et du conducteur
        ArrayList <String> tabPrefUtilisateur;
        ArrayList <String> tabPrefConducteur;
        
        tabPrefUtilisateur = recupererPreference(false);
        tabPrefConducteur = recupererPreference(true);
        for (String s : tabPrefUtilisateur){
            System.out.println("tabPrefUtilisateur : "+s);
        }
        for (String s : tabPrefConducteur){
            System.out.println("tabPrefConducteur : "+s);
        }
        int prixUtilActuel=(this.prix)-(this.prix*60/100);
        //Prix max utilisateur = random entre 0 et 20% du prix de base en moins
        int pourcentageRnd = (int)(Math.random() * (21));
        int prixMaxUtilisateur = (this.prix)-(this.prix*pourcentageRnd/100);
        //Prix minimum conducteur = random entre 0 et 25% du prix de base en moins
        pourcentageRnd = (int)(Math.random() * (26));
        int prixMinConducteur = (this.prix)-(this.prix*pourcentageRnd/100);
        int nbreUtil = negociationPrix(prixUtilActuel,prixMaxUtilisateur);
        String newMessage;
        newMessage="L'utilisateur souhaite voyager";
        messages.add(newMessage);
        boolean CEstFumeur = false;
        boolean UEstFumeur = false;
        boolean CEstAnimaux = false;
        boolean CEstEnfants = false;
        boolean UEstAnimaux = false;
        boolean UEstEnfants = false;
        boolean discussionFini = false;
        boolean tombentDaccord = false;
        boolean poserQuestion = false;
        boolean QFumer = false;
        boolean virg = false;
        //
        //Affichage des préférences Conducteur
        //
        if (tabPrefConducteur.size()>0){
            newMessage = "Le conducteur indique que son voyage ";
            for (int i=0;i<tabPrefConducteur.size();i++){
                if (i>0){
                    newMessage+=  ",";
                }
                if(tabPrefConducteur.get(i).equals("Fumeur")){
                    newMessage+=  "est Fumeur";
                    CEstFumeur = true;
                } else {
                    if(tabPrefConducteur.get(i).equals("Enfants")){
                        CEstEnfants = true;
                    }
                    if(tabPrefConducteur.get(i).equals("Animaux")){
                        CEstAnimaux = true;
                    }
                    newMessage+=  "accepte les "+tabPrefConducteur.get(i);
                }
            }
            messages.add(newMessage);
        }else{
            newMessage = "Le conducteur demande si l'utilisateur n'a pas de preference pour son voyage ";
            messages.add(newMessage);
        }
        
        //
        //Affichage des préférences Utilisateur
        //
        System.out.println(tabPrefUtilisateur.size());
        if(tabPrefUtilisateur.size()>0){
            System.out.println("ici");
            newMessage="L'utilisateur";
             for (int i=0;i<tabPrefUtilisateur.size();i++){
                if (i>0){
                    newMessage+=  ",";
                }
                if(tabPrefUtilisateur.get(i).equals("Fumeur")){
                    UEstFumeur = true;
                    //Si conducteur est non fumeur
                    if (!CEstFumeur){
                        newMessage+=  " indique qu'il est Fumeur";
                        //Random pour savoir si il accepte de voyager avec un coducteur qui ne fume pas
                        int rndAccepte = negociationPrix(0,2);
                        System.out.println(rndAccepte);
                        //Accepte de voyager
                        if (rndAccepte == 2){
                            newMessage+=  " et accepte de voyager dans un voyage non fumeur";
                        //N'accepte pas
                        } else if (rndAccepte == 1){
                            newMessage+=  " et demande si il pourra fumer pendant le voyage";
                            QFumer = true;
                            poserQuestion = true;
                        }else{
                            newMessage+=  " et n'accepte pas de voyager dans un voyage non fumeur";
                            discussionFini = true;
                        }
                    }
                } else if(tabPrefUtilisateur.get(i).equals("Animaux")){
                    UEstAnimaux = true;
                    if (!CEstAnimaux){
                        newMessage+=" demande au conducteur si il accepte les animaux";
                        poserQuestion = true;
                    }
                //Enfants
                } else {
                    UEstEnfants = true;
                    if (!CEstEnfants){
                        newMessage+=" demande au conducteur si il accepte les enfants";
                        poserQuestion = true;
                    }
                }
            }
            if (!UEstFumeur && CEstFumeur ){
                virg=true;
                newMessage+=  " n'est pas Fumeur";
                int rndAccepte = negociationPrix(0,1);
                //Accepte de voyager
                if (rndAccepte == 1){
                    newMessage+=  " et accepte de voyager dans un voyage fumeur";
                //N'accepte pas
                } else {
                    newMessage+=  " et n'accepte pas de voyager dans un voyage fumeur";
                    discussionFini = true;
                }
            }
            if (CEstAnimaux && !UEstAnimaux){
                if(virg) newMessage+=  ",";
                else virg=true;
                int rndAccepte = negociationPrix(0,1);
                //Accepte de voyager
                if (rndAccepte == 1){
                    newMessage+=  " accepte un voyage avec des animaux";
                //N'accepte pas
                } else {
                    newMessage+=  " n'accepte pas de voyage avec des animaux";
                    discussionFini = true;
                }
            }
            if (CEstEnfants && !UEstEnfants){
                if(virg) newMessage+=  ",";
                else virg=true;
                int rndAccepte = negociationPrix(0,1);
                //Accepte de voyager
                if (rndAccepte == 1){
                    newMessage+=  " accepte un voyage avec des enfants";
                //N'accepte pas
                } else {
                    newMessage+=  " n'accepte pas de voyage avec des enfants";
                    discussionFini = true;
                }
            }
            if(CEstAnimaux==UEstAnimaux&&CEstEnfants==UEstAnimaux&&CEstFumeur==UEstFumeur)
                newMessage+=  " accepte les conditions du voyage";
            if (poserQuestion||discussionFini)
            messages.add(newMessage);
        }
        virg=false;
        if (!discussionFini && poserQuestion){
            newMessage="Le conducteur";
            if(UEstEnfants && !CEstEnfants){
                virg = true;
                int rndAccepte = negociationPrix(0,1);
                //Accepte de voyager
                if (rndAccepte == 1){
                    newMessage+=  " tolère les enfants";
                //N'accepte pas
                } else {
                    newMessage+=  " n'accepte pas de voyager avec des enfants";
                    discussionFini = true;
                }
            }
            if(UEstAnimaux && !CEstAnimaux){
                if(virg) newMessage+=  ",";
                else virg=true;
                int rndAccepte = negociationPrix(0,1);
                //Accepte de voyager
                if (rndAccepte == 1){
                    newMessage+=  " tolère les animaux";
                //N'accepte pas
                } else {
                    newMessage+=  " n'accepte pas de voyager avec des animaux";
                    discussionFini = true;
                }
            }
            if(QFumer){
                if(virg) newMessage+=  ",";
                else virg=true;
                int rndAccepte = negociationPrix(0,1);
                //Accepte de voyager
                if (rndAccepte == 1){
                    newMessage+=  " accepte le fumeur";
                //N'accepte pas
                } else {
                    newMessage+=  " n'accepte pas de fumeur dans son voyage";
                    discussionFini = true;
                }
            }
            messages.add(newMessage);
        }
        
        //Première proposition
        if (!discussionFini){
            newMessage = "L'utilisateur propose "+nbreUtil+"€";
            messages.add(newMessage);
        }
        
        
        boolean premierIteration = true;
        //Début Négociation
        int nbreNegocie=0;
        int compteur =1;
        int prixC = 0;
        int prixUtil = 0;
        //
        //Négociation du prix
        //
        while (!discussionFini){
            //Utilisateur
            if (estUtilisateur){
                if (prixUtil==1){
                    newMessage="Utilisateur ne montera pas au dessus de "+nbreUtil+"€";
                    discussionFini = true;
                }else if (nbreNegocie<=prixMaxUtilisateur){
                    newMessage="Utilisateur ok pour "+nbreNegocie+"€";
                    discussionFini = true;
                    tombentDaccord = true;
                } else {
                    if (nbreUtil == prixMaxUtilisateur){
                        prixUtil++;
                    } else {
                        nbreUtil = negociationPrix(nbreUtil,prixMaxUtilisateur);
                    }
                    newMessage = "Utilisateur propose "+ nbreUtil +"€";
                    estUtilisateur=false;
                }
            //conducteur
            } else {
                if (prixC==1){
                    newMessage="Conducteur ne descendra pas en dessous de "+nbreNegocie+"€";
                    discussionFini = true;
                }else if (nbreUtil>=prixMinConducteur){
                    newMessage="Conducteur ok pour "+nbreUtil+"€";
                    discussionFini = true;
                    tombentDaccord = true;
                } else {
                    //1ère itération
                    if (premierIteration){
                        nbreNegocie = this.prix;
                        premierIteration = false;
                    }
                    if (nbreNegocie == prixMinConducteur){
                        prixC++;
                        
                    } else {
                        nbreNegocie=negociationPrix(prixMinConducteur,nbreNegocie);
                    }
                    newMessage = "Conducteur propose "+ nbreNegocie +"€";
                    estUtilisateur=true;
                }
            }
            compteur++;
            if (compteur==20){
                newMessage="Conducteur abandonne car négociation trop longue";
                discussionFini = true;
            }
            messages.add(newMessage);
        }
        enregisterConversation(messages);
    }
    
    
    public int negociationPrix(int min,int max){
        int rndNumber = min + (int)(Math.random() * ((max - min) + 1));
        if(rndNumber<=0){
            return 0;
        } else {
            return rndNumber;
        }
    }
    
    public int getPrix() {
        return prix;
    }

    
    public String getVoyage() {
        return this.voyage.getVilleArrivee() + "-" + this.voyage.getVilleDepart();
    }
    
    public String getDate() {
        return this.voyage.getDate();
    }

    public Utilisateur getVoyageur() {
        return voyageur;
    }

    public Utilisateur getConducteur() {
        return conducteur;
    }

    public String getNomvoyageur() {
        return nomvoyageur;
    }

    public String getNomconducteur() {
        return nomconducteur;
    }
    
    
    
}
