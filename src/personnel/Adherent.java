package personnel;

import com.sun.deploy.uitoolkit.ui.ConsoleController;
import service.Historique;
import pricipale.Bibliotheque;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;

public class Adherent {
    //    les variables
    private String nom, prenom, adresse, type;
    private int id, nbMaxPrets, dureeMaxPrets, nbPret = 0, nbPretTotale=0;
    // toujours faux, vrais sous condition que duree pret < date aujourd'hui - date pret (en semaines)
    private boolean retard = false;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";


    public Adherent(){
    }

    public Adherent(int id, String nom, String prenom, String adresse, String type, int nbMaxPrets, int dureeMaxPrets) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.type = type;
        this.nbMaxPrets = nbMaxPrets;
        this.dureeMaxPrets = dureeMaxPrets;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbMaxPrets() {
        return nbMaxPrets;
    }

    public void setNbMaxPrets(int nbMaxPrets) {
        this.nbMaxPrets = nbMaxPrets;
    }

    public int getDureeMaxPrets() {
        return dureeMaxPrets;
    }

    public void setDureeMaxPrets(int dureeMaxPrets) {
        this.dureeMaxPrets = dureeMaxPrets;
    }

    public int getNbPret() {
        return nbPret;
    }

    public void setNbPret(int nbPret) {
        this.nbPret = nbPret;
    }

    public boolean isRetard() {
        return retard;
    }

    public void setRetard(boolean retard) {
        this.retard = retard;
    }

    public int getNbPretTotale() {
        return nbPretTotale;
    }

    public void setNbPretTotale(int nbPretTotale) {
        this.nbPretTotale = nbPretTotale;
    }

    public String toString() {
        String ch= type +" : [ " +id+", "+nom.toUpperCase()+", "+prenom+", adresse : "+adresse+", situation : ";
        if (retard)
            ch += "retardataire";
        else
            ch += "n'est pas retardataire";
        ch += " ]";
        return ch;
    }

    public void afficherCetAdherent(){
        System.out.printf("|%11s", getType());
        System.out.printf("|%4d", getId());
        System.out.printf("|%20s", getNom());
        System.out.printf("|%20s", getPrenom());
        System.out.printf("|%30s", getAdresse());
        if (getNbPret()==getNbMaxPrets()){
            System.out.print("|"+ANSI_RED);
            System.out.printf("%8d", getNbPret());
            System.out.print(ANSI_RESET);
        }else
            System.out.printf("|%8d", getNbPret());
        if (isRetard()){
            System.out.print("|"+ANSI_RED);
            System.out.printf("%8s", "OUI");
            System.out.print(ANSI_RESET);
        }
        else
            System.out.printf("|%8s", "NON");
        System.out.printf("|%13d", getNbPretTotale());
        System.out.println("|");
    }

    public void modifierCetAdherent(Scanner sc, ArrayList<Historique> historique){
        System.out.println("\t Que voulez vous modifier de "+getPrenom()+" "+getNom());
        String choix, nouveauNom, nouveauPrenom, nouvelleAdresse;
        Bibliotheque b = new Bibliotheque();
        do {
            System.out.println("\t 0) Sortire");
            System.out.println("\t 1) Nom ("+getNom()+")");
            System.out.println("\t 2) Prenom ("+getPrenom()+")");
            System.out.println("\t 3) Adresse ("+getAdresse()+")");
            System.out.println("\t 4) Type ("+getType()+")");
            choix = sc.nextLine();
            switch (choix) {
                case "0": return;
                case "1":
                    System.out.println("\t Entrer nouveau nom");
                    nouveauNom = sc.nextLine();
                    historique.add(new Historique(new Date(), "Adherent", "Modification",getId(), getPrenom()+" "+getNom()+" par "+getPrenom()+" "+nouveauNom));
                    setNom(nouveauNom);
                    break;
                case "2":
                    System.out.println("\t Entrer nouveau prenom");
                    nouveauPrenom = sc.nextLine();
                    historique.add(new Historique(new Date(), "Adherent", "Modification",getId(), getPrenom()+" "+getNom()+" par "+nouveauPrenom+" "+getNom()));
                    setPrenom(nouveauPrenom);
                    break;
                case "3":
                    System.out.println("\t Entrer nouvelle adresse");
                    nouvelleAdresse = sc.nextLine();
                    historique.add(new Historique(new Date(), "Adherent", "Modification",getId(), "Adresse de "+getPrenom()+" "+getNom()+" de "+getAdresse()+" a "+nouvelleAdresse));
                    setAdresse(nouvelleAdresse);
                    break;
                case "4":
                    String ancienType = getType();
                    entreeTypeAdherent(sc);
                    historique.add(new Historique(new Date(), "Adherent", "Modification",getId(), getPrenom()+" "+getNom()+" de "+ancienType+" a "+getType()));
                    break;
                default: System.out.println("\t Erreur! Taper 0 pour sortire"); b.pause(500); break;
            }
        }while (true);
    }

    public void entreeTypeAdherent(Scanner sc){
        String choixDuType;
        Bibliotheque b = new Bibliotheque();
        do {
            System.out.println("\t Veillez selectionner un de ces types");
            System.out.println("\t 1) Etudiant");
            System.out.println("\t 2) Enseignant");
            System.out.println("\t 3) Visiteur");
            choixDuType = sc.nextLine();
            switch (choixDuType){
                case "1":
                    setType("Etudiant");
                    setNbMaxPrets(2);
                    setDureeMaxPrets(7);
                    return;
                case "2":
                    setType("Enseignant");
                    setNbMaxPrets(4);
                    setDureeMaxPrets(21);
                    return;
                case "3":
                    setType("Visiteur");
                    setNbMaxPrets(1);
                    setDureeMaxPrets(7);
                    return;
                default:
                    System.out.println("\t Choix du type adherent est non valide veillez réessayer");
                    b.pause(500);
                    break;
            }
        }while (true);
    }

}
