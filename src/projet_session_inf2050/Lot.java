package projet_session_inf2050;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gabbm
 */
public class Lot {
    private String description;
    private int nbDroitsPassages;
    private int nbServices;
    private int superficie;
    private String date;
    
    public Lot(){
    
    }
    
    
    /**
    * calcule la valeur du lot
    * @param type type du terrain
    * 
    * @return la valeur du lot
    */
    public double valeurLot(int type, double prixMin, double prixMax){
        double resultat = 0;
        
        resultat += montantSuperficie(type, prixMin, prixMax);
        resultat += montantDroitsPassage(type, resultat);
        resultat += montantServices(type);
        
        return resultat;
    }
    
    private double montantSuperficie(int type, double prixMin, double prixMax){
        double montant;
        double prixM2 = 0;
        
        switch(type){
            case 0:
                prixM2 = prixMin;
                break;
            case 1:
                prixM2 = (prixMin + prixMax) / 2;
                break;
            case 2:
                prixM2 = prixMax;
                break;
        }
        montant = prixM2 * this.getSuperficie();
        
        return montant;
    }
    
    private double montantDroitsPassage(int type, double valeurLot){
        double montant;
        double facteur = 0;
        
        switch(type){
            case 0:
                facteur = (double)5/100;
                break;
            case 1:
                facteur = (double)10/100;
                break;
            case 2:
                facteur = (double)15/100;
                break;
        }
        montant = 500 - (this.getNbDroitsPassages() * facteur * valeurLot);
        
        
        return montant;
    }
    
    private double montantServices(int type){
        double montant = 0;
        
        switch(type){
            case 0:
                break;
            case 1:
                if(this.getSuperficie() > 10000){
                    montant = 1000 * this.getNbServices();
                }
                else if(this.getSuperficie() > 500){
                    montant = 500 * this.getNbServices();                    
                }
                break;
            case 2:
                if(this.getSuperficie() > 500){
                    montant = 1500 * this.getNbServices();
                }
                else {
                    montant = 500 * this.getNbServices();
                }
                if(montant > 5000){
                    montant = 5000;
                }
                break;
        }
        
        return montant;
    }
    
    public static void verifierDescription(Lot[] liste) throws InvalideException{
        for(int i = 0; i < liste.length; i++){
            if(liste[i].getDescription().isEmpty()){
                throw new InvalideException("la description d'un lot en vide");
            }
            for(int j = i+1; j < liste.length; j++){
                if(liste[i].getDescription().equals(liste[j].getDescription())){
                    throw new InvalideException("Deux lots ne peuvent pas avoir la même descripton");
                }
            }
        }
    }
    
    //***setters***
    
    public void setDescription(String d){
        this.description = d;
    }
    
    public void setNbDroitsPassages(int p) throws InvalideException{
        if(p < 0){
            throw new InvalideException("le nombre de droit de passage du " + this.getDescription() + " est inférieure à 0");
        } else if(p > 10){
            throw new InvalideException("le nombre de droit de passage du " + this.getDescription() + " est supérieur à 10");
        }
        else{
            this.nbDroitsPassages = p;
        }
    }
    
    public void setNbServices(int s) throws InvalideException{
        if(s < 0){
            throw new InvalideException("le nombre de service du " + this.getDescription() + " est inférieure à 0");    
        } else if(s > 5){
            throw new InvalideException("le nombre de service du " + this.getDescription() + " est supérieure à 5");
        } else {
            this.nbServices = s;
        }
    }
    
    public void setSuperfice(int s) throws InvalideException{
        if(s < 0){
            throw new InvalideException("la superficie du " + this.getDescription() + " est inférieure à 0");    
        } else if(s > 50000){
            throw new InvalideException("la superficie du " + this.getDescription() + " est supérieure à 50 000");    
        } else{
            this.superficie = s;
        }
    }
    
    public void setDate(String d) throws InvalideException{
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd"); 
        formatDate.setLenient(false);
        try{
            formatDate.parse(d);
            this.date = d;
        }catch(ParseException e){
            throw new InvalideException("Le format de la date du " + this.getDescription()
                    + " ne respecte pas les normes ISO 8601" );
        }
        
    }
    
    //***getters***
    
    public String getDescription(){
        return this.description;
    }
    
    public int getNbDroitsPassages(){
        return this.nbDroitsPassages;
    }
    
    public int getNbServices(){
        //2 sevices de bases
        return this.nbServices + 2;
    }
    
    public int getSuperficie(){
        return this.superficie;
    }
    
    public String getDate(){
        return this.date;
    }
    
}
