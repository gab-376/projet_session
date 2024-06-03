/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projet_session_inf2050;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 *
 * @author gabbm
 */
public class Projet_session_inf2050 {
    
    public static String montantToString(Double montant){
        String s = Double.toString(montant);
        return s + " $";
    }

    /**
     * @param args the command line arguments
     * 
     * @throws IOException si le document n'existe pas
     */
    public static void main(String[] args) throws IOException {
        int type;
        double prixMin;
        double prixMax;
        String min;
        String max;
        double valeurTotal = 733.77;
        String entree = "";
        String sortie = "";
        
        if(args.length == 2){
            entree = args[0];
            sortie = args[1];
        } else {
            System.out.println("nombres arguments invalide");
            System.exit(1);
        }
        
        String myJSON = FileReaders.loadFileIntoString(entree,"UTF-8");
        
        //charge les variables du terrain
        JSONObject terrain = JSONObject.fromObject( myJSON );
        type = terrain.getInt("type_terrain");
        min = terrain.getString("prix_m2_min");
        max = terrain.getString("prix_m2_max");
        prixMin = Double.parseDouble(min.substring(0, min.length()-2));
        prixMax = Double.parseDouble(max.substring(0, max.length()-2));

        JSONArray lotissements =  terrain.getJSONArray("lotissements");
        
        //charge les lots du terrain dans une liste de lots
        Lot[] listeLots = new Lot[lotissements.size()];
        for (int i = 0; i < lotissements.size(); i++){
            JSONObject JSONCourant = lotissements.getJSONObject(i);
            Lot lotCourant = new Lot();
            lotCourant.setDescription(JSONCourant.getString("description"));
            lotCourant.setNbDroitsPassages(JSONCourant.getInt("nombre_droits_passage"));
            lotCourant.setNbServices(JSONCourant.getInt("nombre_services"));
            lotCourant.setSuperfice(JSONCourant.getInt("superficie"));
            lotCourant.setDate(JSONCourant.getString("date_mesure"));
            
            listeLots[i] = lotCourant;
        }
        
        
        terrain.clear();
        lotissements.clear();
        
        
        //calcule la valeur de chaque lot
        for(int i = 0; i < listeLots.length; i++){
            JSONObject JSONCourant = new JSONObject();
            double valeurCourante;
            
            Lot lotCourant = listeLots[i];
            valeurCourante = lotCourant.valeurLot(type, prixMin, prixMax); 
            valeurTotal += valeurCourante;
            
            JSONCourant.accumulate("description", lotCourant.getDescription());
            JSONCourant.accumulate("valeur_par_lot", valeurCourante);
            lotissements.add(JSONCourant);
        }
        //arrondit la valeur 
        if(valeurTotal*10%0.5!=0.00){
            valeurTotal += (0.5-valeurTotal*10%0.5)/10;
        }
        double TXScolaire = valeurTotal*0.012;
        double TXMunicipale = valeurTotal*0.025;
        if(TXScolaire*10%0.5!=0.00){
            TXScolaire += (0.5-TXScolaire*10%0.5)/10;
        }
        if(TXMunicipale*10%0.5!=0.00){
            TXMunicipale += (0.5-TXMunicipale*10%0.5)/10;
        }
        
        terrain.accumulate("valeur_fonciere_totale", valeurTotal);
        terrain.accumulate("taxe_scolaire", TXScolaire);
        terrain.accumulate("taxe_ municipale", TXMunicipale);
        terrain.accumulate("lotissements", lotissements);
        
        FileWriters.saveStringIntoFile(sortie , terrain.toString());
        
        //System.out.println("valeur total : " + valeurTotal);
    }
    
}
