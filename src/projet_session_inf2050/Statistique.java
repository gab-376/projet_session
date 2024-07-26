/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projet_session_inf2050;

import java.io.IOException;
import net.sf.json.JSONObject;

/**
 *
 * @author gabbm
 */
public class Statistique {
    final String EMPLACEMENT_PAR_DEFAUT = "statistiques_application.json";
    
    int nombreTotalLots = 0;
    
    int nombreLotsValeurFaible = 0;
    int nombreLotsValeurMoyenne = 0;
    int nombreLotsValeurEleve = 0;
    
    int nombreLotsType0 = 0;
    int nombreLotsType1 = 0;
    int nombreLotsType2 = 0;
    
    int maxSuperficieLot = 0;
    double maxValeurLot = 0.00;
    
    String emplacement;

    /**
     * crée un objet statistique avec l'emplacement du fichier par défaut
     * 
     */
    public Statistique() {
        this.emplacement = EMPLACEMENT_PAR_DEFAUT;
        this.chargerDonnees(this.emplacement);
        
    }
    
    /**
     * enregistre les statistiques sur le disque
     * 
     */
    public void enregistrerStatistique(){
        JSONObject donneesJSON = this.donneesToJSON();
        try {
            FileWriters.saveStringIntoFile(this.emplacement , donneesJSON.toString());
        } catch (IOException ex){
        }
    }
    
    /**
     * remet toutes les statistiques à 0
     * 
     */
    public void resetStatistiques(){
        this.resetDonnees();
        try {
            FileWriters.saveStringIntoFile(this.emplacement , "");
            System.out.println("Les statistiques ont été réinitialiser");
        } catch (IOException ex){
        }
    }
    
    /**
     * affiche les statistiques à la console
     * 
     */
    public void afficherStatistiques() throws InvalideException{
        System.out.println("******************************");
        System.out.println("*********STATISTIQUES*********");
        System.out.println("******************************");
        System.out.println("Nombre Total de lots :  " + this.nombreTotalLots);
        System.out.println();
        System.out.println("Nombre de lots valant moins de 1 000$ :  " + this.nombreLotsValeurFaible);
        System.out.println("Nombre de lots valant entre 1 000$ et 10 000$ :  " + this.nombreLotsValeurMoyenne);
        System.out.println("Nombre de lots valant plus de 10 000$ :  " + this.nombreLotsValeurEleve);
        System.out.println();
        System.out.println("Nombre Total de lots Agricoles :  " + this.nombreLotsType0);
        System.out.println("Nombre Total de lots Résidentiels :  " + this.nombreLotsType1);
        System.out.println("Nombre Total de lots Commercials :  " + this.nombreLotsType2);
        System.out.println();
        System.out.println("Superficie maximale soumise pour un lot :  " + this.maxSuperficieLot);
        System.out.println("Valeur maximale pour un lot :  " + Traiter.formatNombre(Traiter.arrondir(this.maxValeurLot)) );
    }
    
    /**
     * Mets à jour les statistiques suite à une nouvelle demande
     * 
     * @param listeLots liste des lots à calculer
     * @param type le type du terrain
     * @param prixMin le prix minimum du terrain
     * @param prixMax le prix maximum du terrain
     */
    public void nouvelleDemande(Lot[] listeLots, int type, double prixMin, double prixMax){
        for(Lot lot : listeLots){
            this.nombreTotalLots++;
            double valeurLot = lot.valeurLot(type, prixMin, prixMax);
            
            if(valeurLot < 1000){
                this.nombreLotsValeurFaible++;
            } else if(valeurLot <= 10000){
                this.nombreLotsValeurMoyenne++;
            } else {
                this.nombreLotsValeurEleve++;
            }
            
            switch(type){
                case 0:
                    this.nombreLotsType0++;
                    break;
                case 1:
                    this.nombreLotsType1++;
                    break;
                case 2:
                    this.nombreLotsType2++;
                    break;
            }
             this.verifierMaximums(valeurLot, lot);
        }
    }
    
    private void verifierMaximums(double valeurLot, Lot lot){
        if(valeurLot > this.maxValeurLot){
            this.maxValeurLot = valeurLot;
        }
        if(lot.getSuperficie() > this.maxSuperficieLot){
            this.maxSuperficieLot = lot.getSuperficie();
        }
    }
    
    private void resetDonnees(){
        this.nombreTotalLots = 0;
    
        this.nombreLotsValeurFaible = 0;
        this.nombreLotsValeurMoyenne = 0;
        this.nombreLotsValeurEleve = 0;

        this.nombreLotsType0 = 0;
        this.nombreLotsType1 = 0;
        this.nombreLotsType2 = 0;

        this.maxSuperficieLot = 0;
        this.maxValeurLot = 0.00;
    }
    
    private JSONObject donneesToJSON(){
        JSONObject objet = new JSONObject();
        objet.accumulate("nombre_total_lots", this.nombreTotalLots);
        objet.accumulate("nombre_lots_valeur_faible", this.nombreLotsValeurFaible);
        objet.accumulate("nombre_lots_valeur_moyenne", this.nombreLotsValeurMoyenne);
        objet.accumulate("nombre_lots_valeur_eleve", this.nombreLotsValeurEleve);
        
        objet.accumulate("nombre_lots_type0", this.nombreLotsType0);
        objet.accumulate("nombre_lots_type1", this.nombreLotsType1);
        objet.accumulate("nombre_lots_type2", this.nombreLotsType2);
        
        objet.accumulate("max_superficie_lot", this.maxSuperficieLot);
        objet.accumulate("max_valeur_lot", this.maxValeurLot);
        
        return objet;
    }
    
    private void chargerDonnees(String emplacementFichier){
        String s;
        try{
            s = FileReaders.loadFileIntoString(emplacementFichier,"UTF-8");
            jsonToDonnees(s);
            
        } catch(IOException e) {
            try{
            FileWriters.saveStringIntoFile(emplacementFichier , "");
            } catch(IOException ex) {
            System.out.println("impossible de créer le fichier");
            }
        } catch(Exception e){
        } 
    }
    
    private void jsonToDonnees(String jsonString){
        JSONObject json = JSONObject.fromObject( jsonString );
        
        this.nombreTotalLots = json.getInt("nombre_total_lots");
        this.nombreLotsValeurFaible = json.getInt("nombre_lots_valeur_faible");
        this.nombreLotsValeurMoyenne = json.getInt("nombre_lots_valeur_moyenne");
        this.nombreLotsValeurEleve = json.getInt("nombre_lots_valeur_eleve");
        
        this.nombreLotsType0 = json.getInt("nombre_lots_type0");
        this.nombreLotsType1 = json.getInt("nombre_lots_type1");
        this.nombreLotsType2 = json.getInt("nombre_lots_type2");

        this.maxSuperficieLot = json.getInt("max_superficie_lot");
        this.maxValeurLot = json.getDouble("max_valeur_lot");
    }
}
