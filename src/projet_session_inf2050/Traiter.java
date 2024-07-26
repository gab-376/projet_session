/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projet_session_inf2050;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author gabbm
 */
public class Traiter {
    
    /**
     * Fait les observations (commentaires) et les enregistre dans le fichier de sortie
     * 
     * @param listeLots liste des lots à calculer
     * @param out fichier de sortie où enregistrer les infos
     * @param type le type du terrain
     * @param prixMin le prix minimum du terrain
     * @param prixMax le prix maximum du terrain
     */
    public static void observations(Lot[] listeLots, FichierSortie out, int type, double prixMin, double prixMax) throws ParseException{
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd"); 
        Date dateVieux = formatDate.parse(listeLots[0].getDate());
        Date dateRecente = formatDate.parse(listeLots[0].getDate());
        
        if(prixMax > prixMin * 2){
            out.ajouterObservations("Le prix maximum dépasse le double du prix minimum");
        }
        
        for(int i = 0; i < listeLots.length; i++){
            Traiter.observationsLot(listeLots[i], listeLots[i].valeurLot(type, prixMin, prixMax), out);
            
            if(dateVieux.compareTo(formatDate.parse(listeLots[i].getDate())) > 0){
                dateVieux = formatDate.parse(listeLots[i].getDate());
            } else if(dateRecente.compareTo(formatDate.parse(listeLots[i].getDate())) < 0){
                dateRecente = formatDate.parse(listeLots[i].getDate());
            }
        }
        Traiter.comparerDate(dateVieux, dateRecente, out);
        if(!out.getObservations().equals(new JSONArray())){
            out.ajouterInfo("observations", out.getObservations());
        }
        
    }
    
    /**
     * calcule la valeur de chaque lots de la liste et les ajoutes au fichier de sortie
     * 
     * @param listeLots liste des lots à calculer
     * @param out fichier de sortie où enregistrer les infos
     * @param type le type du terrain
     * @param prixMin le prix minimum du terrain
     * @param prixMax le prix maximum du terrain
     */
    public static void calculer(Lot[] listeLots, FichierSortie out, int type, double prixMin, double prixMax) throws InvalideException{
        
        double total = 733.77;
        
        for(int i = 0; i < listeLots.length; i++){
            JSONObject JSONCourant = new JSONObject();
            
            double valeurCourante = listeLots[i].valeurLot(type, prixMin, prixMax); 
            total += valeurCourante;
            
            JSONCourant.accumulate("description", listeLots[i].getDescription());
            JSONCourant.accumulate("valeur_par_lot", Traiter.formatNombre(Traiter.arrondir(valeurCourante)));
            out.ajouterLots(JSONCourant);
        }
        
        Traiter.calculeTaxes(total, out);
    }
    
    /**
     * Arrondit un montant au 5 sous le plus près
     * 
     * @param nombre le montant à arrondir
     * 
     * @return nombre arrondie
     */
    public static double arrondir(double nombre){
        if(nombre*10%0.5 <= 0.25){
            nombre -= (nombre*10%0.5)/10;
        } else {
            nombre += (nombre*10%0.5)/10;
        }
        return nombre;
    }
    
    /**
     * ajuste le format d'un montant pour l'affichage et l'enregistrement
     * 
     * @param nombre à formater
     * 
     * @return  montant formaté
     * @throws InvalideException si le montant d'argent est négatif
     */
    public static String formatNombre(double nombre) throws InvalideException{
        if(nombre < 0){
            throw new InvalideException("un montant d'argent ne peut pas être négatif");
        }
        String resultat = String.format("%.2f", nombre);
        resultat += " $";
        
        return resultat;
    }
    
    private static void observationsLot(Lot lot, double valeurLot, FichierSortie out){
        
        if(valeurLot > 45000){
                out.ajouterObservations("La valeur par lot du " + lot.getDescription() 
                        + " est trop dispendieuse");
        }
        if(lot.getSuperficie() > 45000){
            out.ajouterObservations("La superficie du lot " + lot.getDescription() + " est trop grande");
        }
        
    }
    
    private static void comparerDate(Date vieux, Date recent, FichierSortie out){
        vieux.setMonth(vieux.getMonth() + 6);
        if(vieux.compareTo(recent) < 0){
            out.ajouterObservations("L’écart maximal entre les dates de mesure des lots d’un même terrain"
                    +" devrait être de moins de 6 mois.");
        }
    }
    
    private static void calculeTaxes(double valeurTerrain, FichierSortie out) throws InvalideException{
        out.ajouterInfo("valeur_fonciere_totale", Traiter.formatNombre(valeurTerrain));
        double txScolaire = Traiter.arrondir(valeurTerrain*0.012);
        double txMunicipale = Traiter.arrondir(valeurTerrain*0.025);
        
        if(valeurTerrain > 300000){
            out.ajouterObservations("La valeur total du terrain est trop dispendieuse");
        }
        if(txScolaire > 500){
            out.ajouterObservations("La taxe scolaire payable par le propriétaire nécessite deux versements.");
        }
        if(txMunicipale > 1000){
            out.ajouterObservations("La taxe municipale payable par le propriétaire nécessite deux versements.");
        }
        
        out.ajouterInfo("taxe_scolaire", Traiter.formatNombre(txScolaire));
        out.ajouterInfo("taxe_ municipale", Traiter.formatNombre(txMunicipale));
    }
}
