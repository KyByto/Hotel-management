/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel.controller;
import hotel.model.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;

/**
 *
 * @author LENOVO
 */
public class Chambre {
    public static void AjouterUneChambre(Administrateur admin ,String numeroDeChambre , String typeDeChambre , String étatDeLaChambre)
  {
      try {
                admin.CreerUneChambre(typeDeChambre, numeroDeChambre , étatDeLaChambre);

      }
      catch(Exception e) {
          System.out.print(e.getMessage());
      }
              
         
      
    }
    
    
    public static void SupprimerUneChambre(Administrateur admin , String numeroDeChambre) {
    try {
        admin.SupprimerUneChambre(numeroDeChambre);
    }
    catch(Exception e) {
        System.out.println(e.getMessage());
    }
}
    public static void ModifierUneChambre(Administrateur admin , String numeroDeChambre , String nouveauNumeroDeChambre , String typeChambre , String étatDeLaChambre) {
        try {
            admin.ModifierUneChambre(numeroDeChambre, typeChambre, étatDeLaChambre , nouveauNumeroDeChambre);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static DefaultTableModel getALLChambre(Administrateur admin) {
        
          DefaultTableModel model = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Numéro De Chambre" , "Type De Chambre" , "état De Chambre" }
        ) {

            boolean[] canEdit = new boolean[] { false, false, false};
@Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
                
                
                
                
                
                
            }
            

            
            
           
       
        };

        try {
               ArrayList<hotel.model.Chambre> chambres = admin.getAllChambre();
               System.out.println("ALL CHAMBRE /" + chambres.toString());
                 for (hotel.model.Chambre chambre : chambres) {
            Object[] rowData = new Object[3];
            rowData[0] = chambre.numeroChambre;
            rowData[1] = chambre.typeChambre;
            rowData[2] = chambre.disponible ? "Non réservé" : "Réservé";
         
            model.addRow(rowData);
        }
               
      } 
      catch(Exception error) {
          System.out.println("Error Occured bro " +error.getMessage());
          
      }
                    return model;
        
        
    }
}
