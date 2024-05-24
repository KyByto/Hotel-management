/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel.controller;

import hotel.model.Administrateur;
import hotel.model.Client;
import hotel.model.Réservation;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;

/**
 *
 * @author LENOVO
 */
public class Reservation {
     public static void demanderuneréservation(Client client,String dateDebut,String dateFin  , String typeChambre){
       try{
           client.DemanderUneReservation(typeChambre, dateDebut,dateFin );}
       catch(Exception e){
           e.printStackTrace();
       }
    }
    public static void annuleruneréservation(Client client){
        try{client.AnnulerRéservation();}
        catch(Exception e){
            
        }
    }
    public static void accepteruneréservation(Administrateur administrateur,String id){
        try{administrateur.AccepterUneRéservation(id);}
        catch(Exception e){
            
        }
    }
    public static void modifieruneréservation(Client client,String DateDebut,String DateFin , String typeChambre){
        try{client.ModifierRéservation(DateDebut, DateFin , typeChambre);}
        catch(Exception e){
            
        }
    }
    public static void modifieruneréservation(Client client , String typeChambre) {
        try {
            client.ModifierRéservation(typeChambre);
        }
        catch(Exception e) {
            System.out.print(e.getMessage());
        }
        
    }
    public static void refuseruneréservation(Administrateur administrateur,String id){
        try{administrateur.RefuserUneRéservation(id);}
        catch(Exception e){
                        System.out.println(e.getMessage());

        }
        
    }
    public static void supprimerUneReservation(Administrateur administrateur , String id) {
        try {
            administrateur.supprimerUneReservation(id);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
      public static javax.swing.table.DefaultTableModel remplirLeTableauDeReservation(    Administrateur admin) {
   DefaultTableModel model = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Nom Client" , "Prénom Client" , "type de chambre", "date debut", "date fin" , "estAccepté" , "idClient" }
        ) {

            boolean[] canEdit = new boolean[] { false, false, false, false , false , false, false , false , false };
@Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
                
                
                
                
                
                
            }
            

            
            
           
       
        };
   model.addRow(new Object[]{"" , "" , "" , "" , "" , "" });

        try {
               ArrayList<Réservation> réservation = admin.getAllReservations();
                 for (Réservation reservation : réservation) {
            Object[] rowData = new Object[9];
            Document client = reservation.getClient();
            rowData[0] = client.get("nom");
            rowData[1] = client.get("prénom");
            rowData[2] = reservation.getTypeChambre();
            rowData[3] = reservation.getDateDebut();
            rowData[4] = reservation.getDateFin();
            rowData[5] = reservation.getAccepté() ? "Oui" : "Non";
   rowData[6] = reservation.getIdClient();
            model.addRow(rowData);
        }
               
      } 
      catch(Exception error) {
          System.out.println(error.getMessage());
          
      }
                    return model;
   
    }
}
