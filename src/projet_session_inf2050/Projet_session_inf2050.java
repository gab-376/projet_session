/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projet_session_inf2050;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabbm
 */
public class Projet_session_inf2050 {
    
    /**
     * @param args the command line arguments
     * 
     */
    public static void main(String[] args) {
        String entree = "";
        String sortie = "";
        
        if(args.length == 2){
            entree = args[0];
            sortie = args[1];
        } else {
            System.out.println("nombres arguments invalide");
            System.exit(1);
        }
        
        FichierSortie out = new FichierSortie(sortie);
        
        try {
            FichierEntree fichierEntree = new FichierEntree(entree);
            
            Lot[] listeLots = FichierEntree.chargerLots(fichierEntree.getJSON());
            
            Traiter.calculer(listeLots, out, fichierEntree.getType(), fichierEntree.getPrixMin(), fichierEntree.getPrixMax());
            out.ajouterInfo("lotissements", out.getLotissements());
            Traiter.observations(listeLots, out, fichierEntree.getType(), fichierEntree.getPrixMin(), fichierEntree.getPrixMax());

        } catch (InvalideException e) {
            out.ajouterMessage(e.getMessage());
        } catch (ParseException e) { 
        }
        
        
        out.enregistrer(sortie);
        
    }
    
    
    
}
