package hotel.model;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import java.util.UUID;

import com.mongodb.client.MongoCollection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.mongodb.client.model.UpdateOptions;
import java.util.ArrayList;


public class Client extends utilisateur {
    private  MongoCollection<Document> clients = DatabaseManager.getDatabase().getCollection("clients");
    private MongoCollection<Document> réservation = DatabaseManager.getDatabase().getCollection("reservations");
public Client( String id) {
    // Convert UUID to string representation
    this.id = id;
     
     role = Role.client;
}

public void DemanderUneReservation(String typeChambre ,String dateDebut , String DateFin) throws userException , Exception  {
System.out.println("Demander une réserv");
    LocalDateTime currentDateTime = LocalDateTime.now();
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateTime = currentDateTime.format(formatter);
   Réservation reservation = new Réservation(id , dateDebut , DateFin ,  formattedDateTime, false , false , typeChambre);
        réservation.insertOne(reservation.toDocument());
    
  

}



public void ModifierRéservation(String dateDebut , String dateFin , String typeChambre) throws userException , Exception {
Document query = new Document("idClient", id);
Document found = réservation.find(query).first();
if(found == null) {
    throw new userException("User is not found");
}
if(found.getBoolean("estAcceptée")) {
    throw new userException("Réservation is already accepted");
}
Document update = new Document("$set", new Document("dateDebut", dateDebut).append("dateFin", dateFin).append("typeChambre" , typeChambre));
System.out.println("Update the réservation "  + typeChambre);

réservation.updateOne(query, update );

}


public void ModifierRéservation(String typeChambre) throws userException , Exception {
   System.out.println("Type Chambre " + typeChambre);

    Document query = new Document("idClient", id);
Document found = réservation.find(query).first();
if(found == null) {
    throw new userException("User is not found");
}

Document update = new Document("$set", new Document("typeChambre", typeChambre));

réservation.updateOne(query, update );

}
    


public void AnnulerRéservation(String typeChambre , String dateDebut , String dateFin) throws Exception , userException {
        Document query = new Document("idClient", id).append("typeChambre", typeChambre).append("dateDebut" , dateDebut).append("dateFin" , dateFin);
        Document clientDocument = réservation.find(query).first();
        if (clientDocument == null) {
            throw new userException("User is not found");
        }
        
        
        réservation.deleteOne(query);

    

}

public ArrayList<Réservation> getAllReservationsClient() throws userException, Exception {
    ArrayList<Réservation> allReservations = new ArrayList<>();
            Document query = new Document("idClient", id);

    try {
        FindIterable<Document> iterable = réservation.find(query);
        for (Document document : iterable) {
            Réservation reservation   = new Réservation(document.getString("idClient"), document.getString("dateDebut"), document.getString("dateFin"), document.getString("dateDemande")  ,document.getBoolean("estAcceptée") ,document.getBoolean("estAnnulée") , document.getString("typeChambre"));
            allReservations.add(reservation);
        }
    } catch (Exception e) {
        throw new userException(e.getMessage());
    }
    return allReservations;
}




}
