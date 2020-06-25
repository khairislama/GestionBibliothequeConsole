package documents;

import service.Historique;
import pricipale.Bibliotheque;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Livre extends Document {
    //    Variables
    private String nomAuteur, nomEditeur;
    private Date dateEdition;

    public Livre(){

    }

    public Livre(int id, String titre, String localisation, int nbExemplaires, String nomAuteur, String nomEditeur, Date dateEdition) {
        super(id, titre, localisation, nbExemplaires);
        this.nomAuteur = nomAuteur;
        this.nomEditeur = nomEditeur;
        this.dateEdition = dateEdition;
    }

    public String getNomAuteur() {
        return nomAuteur;
    }

    public void setNomAuteur(String nomAuteur) {
        this.nomAuteur = nomAuteur;
    }

    public String getNomEditeur() {
        return nomEditeur;
    }

    public void setNomEditeur(String nomEditeur) {
        this.nomEditeur = nomEditeur;
    }

    public Date getDateEdition() {
        return dateEdition;
    }

    public void setDateEdition(Date dateEdition) {
        this.dateEdition = dateEdition;
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Livre [ "+super.toString()+", N.A: "+nomAuteur+", N.E: "+nomEditeur+", D.E: "+sdf.format(dateEdition)+" ]";
    }

    public void afficherCeDocument(int disponible, int nb){
        double k;
        if (nb==0)
            k=0;
        else
            k= Math.ceil(((double) getNbPretTotale()/nb)*100);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.printf("|%9s", getClass().getSimpleName());
        System.out.printf("|%4d", getId());
        System.out.printf("|%36s", getTitre());
        System.out.printf("|%20s", getNomAuteur());
        System.out.printf("|%20s", getNomEditeur());
        System.out.printf("|%10s", sdf.format(getDateEdition()));
        System.out.print("|   ------   ");
        System.out.printf("|%12s", getLocalisation());
        System.out.printf("|%12d", getNbExemplaires());
        if (disponible==0){
            System.out.print("|"+ANSI_RED);
            System.out.printf("%12d", disponible);
            System.out.print(ANSI_RESET);
        }else
            System.out.printf("|%12d", disponible);
        System.out.printf("|%13d", getNbPretTotale());
        if (k>30){
            System.out.print("|"+ANSI_GREEN);
            System.out.printf("%5s", k);
            System.out.print("%");
            System.out.print(ANSI_RESET);
        }else{
            System.out.printf("|%5s", k);
            System.out.print("%");
        }
            System.out.printf("|%5s", k);
        System.out.print("%");
        System.out.println("|");
    }

    public void modifierCeDocument(Scanner sc, ArrayList<Historique> historique){
        String choix, str;
        Bibliotheque b = new Bibliotheque();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("\t Que voulez vous modifier de ce Livre : ");
        do {
            System.out.println("\t 0) Sortire");
            System.out.println("\t 1) Titre ("+getTitre()+")");
            System.out.println("\t 2) Localisation ("+getLocalisation()+")");
            System.out.println("\t 3) Nombre Exemplaires ("+getNbExemplaires()+")");
            System.out.println("\t 4) Nom auteur ("+getNomAuteur()+")");
            System.out.println("\t 5) Nom editeur ("+getNomEditeur()+")");
            System.out.println("\t 6) Date publication ("+sdf.format(getDateEdition())+")");
            choix = sc.nextLine();
            switch (choix){
                case "0": return;
                case "1":
                    String ancienTitre = getTitre();
                    System.out.println("\t Entrer nouveau titre");
                    setTitre(sc.nextLine());
                    historique.add(new Historique(new Date(), "Document", "Modification",getId(), "Titre de "+ancienTitre+" par "+getTitre()));
                    System.out.println("\t Modification avec succees.");
                    b.pause(1000);
                    break;
                case "2":
                    String loc = getLocalisation();
                    System.out.println("\t Entrer nouvelle localisation (Salle/Rayon)");
                    setLocalisation(sc.nextLine());
                    System.out.println("\t Modification avec succees.");
                    historique.add(new Historique(new Date(), "Document", "Modification", getId(), "Localisation de "+loc+" par "+getLocalisation()));
                    b.pause(1000);
                    break;
                case "3":
                    System.out.println("\t Entrer nouveau nombre d'exemplaires");
                    boolean verifier=false;
                    int nb = getNbExemplaires();
                    do {
                        try{
                            setNbExemplaires(sc.nextInt());
                            verifier = true;
                        } catch (Exception e){
                            System.out.println("\t Erreur! Veillez entrer des chiffres...");
                            System.out.println("\t Entrer nouveau nombre d'exemplaires");
                            b.pause(500);
                            sc.nextLine();
                        }
                    }while (!verifier);
                    sc.nextLine();
                    historique.add(new Historique(new Date(), "Document", "Modification", getId(), "Nb exemplaires de "+nb+" a "+getNbExemplaires()+" exemplaires en totale"));
                    System.out.println("\t Modification avec succees.");
                    b.pause(1000);
                    break;
                case "4":
                    String ancienNomAuteur = getNomAuteur();
                    System.out.println("\t Entrer nouveau nom d'auteur");
                    setNomAuteur(sc.nextLine());
                    historique.add(new Historique(new Date(), "Document", "Modification",getId(), "Nom auteur de "+ancienNomAuteur+" par "+getNomAuteur()));
                    System.out.println("\t Modification avec succees.");
                    b.pause(1000);
                    break;
                case "5":
                    String ancienEditeur=getNomEditeur();
                    System.out.println("\t Entrer nouveau nom d'éditeur");
                    setNomEditeur(sc.nextLine());
                    historique.add(new Historique(new Date(), "Document", "Modification",getId(), "Nom editeur de "+ancienEditeur+" par "+getNomEditeur()));
                    System.out.println("\t Modification avec succees.");
                    b.pause(1000);
                    break;
                case "6":
                    String ancienneDate = sdf.format(getDateEdition());
                    System.out.println("\t Entrer nouvelle date de publication sous forme (DD/MM/YYYY)");
                    str = sc.nextLine();
                    try {
                        setDateEdition(sdf.parse(str));
                        System.out.println("\t Modification avec succees.");
                        historique.add(new Historique(new Date(), "Document", "Modification",getId(), "Date publication de "+ancienneDate+" par "+sdf.format(getDateEdition())));
                        b.pause(1000);
                    }catch (ParseException e){
                        System.out.println("\t Forme non respecter!");
                        b.pause(1000);
                    }
                    break;
                default:
                    System.out.println("\t Choix invalide! veillez réessayer");
                    b.pause(500);
                    break;
            }
        }while (true);
    }
}
