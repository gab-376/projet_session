/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projet_session_inf2050;

import java.text.ParseException;

/**
 *
 * @author gabbm
 */
public class Projet_session_inf2050 {
    
    /**
     * @param args the command line arguments
     * 
     */
    public static void main(String[] args) throws InvalideException {
        
        Statistique stats = new Statistique();
        
        String[] emplacementsFichiers = gestionArguments(args, stats);
        
        FichierSortie out = new FichierSortie(emplacementsFichiers[1]);
        
        try {
            
            FichierEntree fichierEntree = new FichierEntree(emplacementsFichiers[0]);
            
            Lot[] listeLots = FichierEntree.chargerLots(fichierEntree.getJSON());
            
            Traiter.calculer(listeLots, out, fichierEntree.getType(), fichierEntree.getPrixMin(), fichierEntree.getPrixMax());
            out.ajouterInfo("lotissements", out.getLotissements());
            stats.nouvelleDemande(listeLots, fichierEntree.getType(), fichierEntree.getPrixMin(), fichierEntree.getPrixMax());
            Traiter.observations(listeLots, out, fichierEntree.getType(), fichierEntree.getPrixMin(), fichierEntree.getPrixMax());
            
            stats.enregistrerStatistique();
            out.enregistrer(emplacementsFichiers[1]);
        } catch (InvalideException e) {
            out.ajouterMessage(e.getMessage());
        } catch (Exception e) { 
        }
        
        
        
    }
    
    private static String[] gestionArguments(String[] args, Statistique stats) throws InvalideException{
        String[] emplacementsFichiers = new String[2];
        emplacementsFichiers[1] = "";
        for(int i = 0; i < args.length; i++){
            if(args[i].charAt(0)=='-'){
                gestionOption(args[i], stats);
            } else {
                try {
                    emplacementsFichiers[0] = args[i];
                    i++;
                    emplacementsFichiers[1] = args[i];
                } catch(Exception e){
                    System.out.println("nombres arguments invalide");
                    System.exit(1);
                }
            }
        }
        return emplacementsFichiers;
    }
    
    private static void gestionOption(String option, Statistique stats) throws InvalideException{
        option = option.toLowerCase();
        if(option.matches("^-s$")){
            stats.afficherStatistiques();
        }else if(option.matches("^-sr$")){
            stats.resetStatistiques();
        }
    }
    
}
