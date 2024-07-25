

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package projet_session_inf2050;

import java.io.IOException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *class pour gérer le fichier d'entrée
 *
 * @author gabbm
 */
public class FichierEntree {
    int type;
    double prixMin;
    double prixMax;
    String min;
    String max;
    JSONObject json;
    
    
    /**
    * récupère le fichier et les infos du terrain au format JSON 
    * @param entree String représentant lot'emplacement du fichier
    *
    * @throws InvalideException si une information est manquante ou erroné
    */
    public FichierEntree(String entree) throws InvalideException{
        chargerFichier(entree);
        FichierEntree.verifierInfo(this.json);
        chargerInfo(this.json);
    }
    
    
    /**
    * charge les lots à partir d'un objet JSON
    * @param objet objet JSON contenant les lots
    * 
    * @return les lots sous forme d'une liste
    * @throws InvalideException si une information est manquante ou erroné
    */
    public static Lot[] chargerLots(JSONObject objet) throws InvalideException{
        FichierEntree.validerNbLot(objet);
        JSONArray lotissements = objet.getJSONArray("lotissements");
        
        Lot[] listeLots = new Lot[lotissements.size()];
        
        for (int i = 0; i < lotissements.size(); i++){
            FichierEntree.verifierLot(lotissements.getJSONObject(i));
            listeLots[i] = FichierEntree.jsonALot(lotissements.getJSONObject(i));
        }
        
        Lot.verifierDescription(listeLots);
        
        return listeLots;
    }
    
    /**
    * convertie un objet json en objet Lot
    * @param jsonLot json contenant les infos du lot
    * 
    * @return un objet Lot contenant les infos
    * 
    * @throws InvalideException si une information est érroné
    */
    public static Lot jsonALot(JSONObject jsonLot) throws InvalideException{
        Lot lot = new Lot();
        lot.setDescription(jsonLot.getString("description"));
        lot.setNbDroitsPassages(jsonLot.getInt("nombre_droits_passage"));
        lot.setNbServices(jsonLot.getInt("nombre_services"));
        lot.setSuperfice(jsonLot.getInt("superficie"));
        lot.setDate(jsonLot.getString("date_mesure"));
        
        return lot;
    }
    
    private void chargerInfo(JSONObject objet) throws InvalideException{
        
        this.setType(objet.getInt("type_terrain"));
        
        
        min = objet.getString("prix_m2_min").replace(',', '.');
        max = objet.getString("prix_m2_max").replace(',', '.');
        
        this.setPrixMin(Double.parseDouble(min.substring(0, min.length()-2)));
        this.setPrixMax(Double.parseDouble(max.substring(0, max.length()-2)));
        
        }
    
    private void chargerFichier(String entree){
        String s;
        try{
            s = FileReaders.loadFileIntoString(entree,"UTF-8");
            chargerJSON(s);
            
        } catch(IOException e) {
            System.out.println("Fichier indisponible");
        } 
        
    } 
    
    
    private void chargerJSON(String jsonString){
        json = JSONObject.fromObject( jsonString );
    }
    
    private static void verifierInfo(JSONObject json) throws InvalideException{
        if(!json.has("type_terrain")){
            throw new InvalideException("Il manque le type de terrain");
        }
        if(!json.has("prix_m2_min")){
            throw new InvalideException("Il manque le prix minmimum au m2");
        }
        if(!json.has("prix_m2_max")){
            throw new InvalideException("Il manque le prix maximum au m2");
        }
        if(!json.has("lotissements")){
            throw new InvalideException("Il manque la liste des lots");
        }
    }
    
    private static void verifierLot(JSONObject jsonLot) throws InvalideException{
        if(!jsonLot.has("description")){
            throw new InvalideException("un lot manque une description");
        }
        if(!jsonLot.has("nombre_droits_passage")){
            throw new InvalideException("un lot manque le nombre de droits de passage");
        }
        if(!jsonLot.has("nombre_services")){
            throw new InvalideException("un lot manque le nombre de services");
        }
        if(!jsonLot.has("superficie")){
            throw new InvalideException("un lot manque la superficie");
        }
        if(!jsonLot.has("date_mesure")){
            throw new InvalideException("un lot manque la date de mesure");
        }
    } 
    
    private static void validerNbLot(JSONObject objet) throws InvalideException{
        JSONArray lotissements = objet.getJSONArray("lotissements");
        if(lotissements.size() < 1){
            throw new InvalideException("Le terrain doit contenir au moins un lot");
        } else if(lotissements.size() > 10){
            throw new InvalideException("Le terrain contient plus de 10 lots");
        }
    }
    
    
    public JSONObject getJSON(){
        return this.json;
    }
    
    public int getType(){
        return this.type;
    }
    
    public double getPrixMin(){
        return this.prixMin;
    }
    
    public double getPrixMax(){
        return this.prixMax;
    }
    
    private void setPrixMin(double d) throws InvalideException{
        if(d < 0){
            throw new InvalideException("Le Prix minimum ne peut pas être négatif");
        }
        this.prixMin = d;
    }
    
    private void setPrixMax(double d) throws InvalideException{
        if(d < 0){
            throw new InvalideException("Le Prix maximum ne peut pas être négatif");
        }
        this.prixMax = d;
    }
    
    private void setType(int i) throws InvalideException{
        if(i < 0 || i > 2){
            throw new InvalideException("Le type du terrain doit être 0 , 1 ou 2");
        }
        this.type = i;
    }
}
