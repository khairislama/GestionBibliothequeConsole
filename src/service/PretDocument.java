package service;

import documents.Document;
import personnel.Adherent;
import pricipale.Bibliotheque;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class PretDocument {
    //    Les Variables
    private Adherent adherent;
    private Date dateDuPret;
    private Date dateDeRetour;
    public static final String R = "\u001B[31m";
    public static final String RES = "\u001B[0m";
    public static final String G = "\u001B[32m";

    public PretDocument(){

    }

    public PretDocument(Adherent adherent, Date dateDuPret, Date dateDeRetour) {
        this.adherent = adherent;
        this.dateDuPret = dateDuPret;
        this.dateDeRetour = dateDeRetour;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }

    public Date getDateDuPret() {
        return dateDuPret;
    }

    public void setDateDuPret(Date dateDuPret) {
        this.dateDuPret = dateDuPret;
    }

    public Date getDateDeRetour() {
        return dateDeRetour;
    }

    public void setDateDeRetour(Date dateDeRetour) {
        this.dateDeRetour = dateDeRetour;
    }
    
    public void afficherCePret(Document d){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String nomAdherent = getAdherent().getPrenom()+" "+getAdherent().getNom();
        System.out.printf("|%9s", d.getClass().getSimpleName());
        System.out.printf("|%40s", d.getTitre());
        System.out.printf("|%40s", nomAdherent);
        System.out.printf("|%13s", getAdherent().getType());
        System.out.printf("|%13s", sdf.format(getDateDuPret()));
        if (getDateDeRetour().before(date)){
//            EN ROUGE
            System.out.print("|"+R);
            System.out.printf("%13s", sdf.format(getDateDeRetour()));
            System.out.print(RES);
        }else {
//            VERT
            System.out.print("|"+G);
            System.out.printf("%13s", sdf.format(getDateDeRetour()));
            System.out.print(RES);
        }

        System.out.println("|");
    }

    public void calculerDateRetour(){
        Calendar c = Calendar.getInstance();
        c.setTime(getDateDuPret());
        c.add(Calendar.DATE, getAdherent().getDureeMaxPrets());
        setDateDeRetour(c.getTime());
    }
}
