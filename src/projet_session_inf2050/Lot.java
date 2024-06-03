package projet_session_inf2050;

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
    
    
    
    public double valeurLot(int type, double prixMin, double prixMax){
        double resultat = 0;
        
        resultat += montantSuperficie(type, prixMin, prixMax);
        resultat += montantDroitsPassage(type, resultat);
        resultat += montantServices(type);
        
        return resultat;
    }
    
    /**
    *   calcule le montant de la valeur de la superficie du lot
    * 
    * @param type       type du terrain
    * @param prixMin    prix minimum du terrain
    * @param prixMax    prix maximum du terrain
    * @return           valeur de la superficie
    */
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
    
    /**
    *   calcule le montant pour les droits de passage du lot
    * 
    * @param type       type du terrain
    * @param valeurLot  valeur du lot
    * @return           montant des droits de passage
    */
    private double montantDroitsPassage(int type, double valeurLot){
        double montant;
        double facteur = 0;
        
        switch(type){
            case 0:
                facteur = 5/100;
                break;
            case 1:
                facteur = 10/100;
                break;
            case 2:
                facteur = 15/100;
                break;
        }
        montant = 500 - (this.getNbDroitsPassages() * facteur * valeurLot);
        
        return montant;
    }
    
    /**
    *   calcule le montant pour les services du lot
    * 
    * @param type       type du terrain
    * @return           montant des services
    */
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
    
    
    //***simple setters***
    
    public void setDescription(String d){
        this.description = d;
    }
    
    public void setNbDroitsPassages(int p){
        if(p >= 0){
            this.nbDroitsPassages = p;
        }
    }
    
    public void setNbServices(int s){
        if(s >= 0){
            this.nbServices = s;
        }
    }
    
    public void setSuperfice(int s){
        if(s >= 0){
            this.superficie = s;
        }
    }
    
    public void setDate(String d){
        this.date = d;
    }
    
    //***simple getters***
    
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
