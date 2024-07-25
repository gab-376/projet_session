/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projet_session_inf2050;

import java.io.IOException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 *
 * @author gabbm
 */
public class FichierSortie {
    JSONObject terrain = new JSONObject();
    JSONArray lotissements = new JSONArray();
    JSONArray observations = new JSONArray();
    
    public FichierSortie(String sortie){
        try{
            FileWriters.saveStringIntoFile(sortie , "");
        } catch(IOException e) {
            System.out.println("impossible de créer le fichier");
        }
    }
    
    public void ajouterInfo(String nom, String info){
        terrain.accumulate(nom, info);
    }
    
    public void ajouterInfo(String nom, JSONArray info){
        terrain.accumulate(nom, info);
    }
    
    public void ajouterMessage(String info){
        ajouterInfo("message", info);
    }
    
    public void ajouterLots(JSONObject objet){
        lotissements.add(objet);
    }
    
    public void ajouterObservations(String observation){
        observations.add(observation);
    }
    
    public void enregistrer(String sortie){
        try {
            FileWriters.saveStringIntoFile(sortie , terrain.toString());
        } catch (IOException ex){
        }
    }
    
    public JSONArray getObservations(){
        return this.observations;
    }
    
    public JSONArray getLotissements(){
        return this.lotissements;
    }
}
