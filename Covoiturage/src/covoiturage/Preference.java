/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package covoiturage;

import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public class Preference {
    private ArrayList<String> preferenceExistante = new ArrayList<String>(3);
    
    //Initialisation des préférences
    public Preference(){
        preferenceExistante.add("Fumeur");
        preferenceExistante.add("Animaux");
        preferenceExistante.add("Enfants");
        
    }
    
    public ArrayList<String> getPreferenceExistante(){
        return preferenceExistante;
    }
    
    public int getPref(){
        return (int)(Math.random() * (preferenceExistante.size()));
    }
    
    public int nbPref(){
        return preferenceExistante.size();
    }
}
