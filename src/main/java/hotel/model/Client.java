package hotel.model;
import org.bson.Document;
import java.util.UUID;

import com.mongodb.client.MongoCollection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Client extends utilisateur {
    private  MongoCollection<Document> clients = DatabaseManager.getDatabase().getCollection("clients");
    private MongoCollection<Document> réservation = DatabaseManager.getDatabase().getCollection("reservations");
public Client( String id) {
    // Convert UUID to string representation
    this.id = id;
     
     role = Role.client;
}

public void DemanderUneReservation(String typeChambre ,String dateDebut , String DateFin) throws userException , Exception  {
   
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
if(found.getBoolean("accepter") == true) {
    throw new userException("Réservation is already accepted");
}
Document update = new Document("$set", new Document("dateDebut", dateDebut).append("dateFin", dateFin).append("typeChambre" , typeChambre));

réservation.updateOne(query, update);

}


public void ModifierRéservation(String typeChambre) throws userException , Exception {

Document query = new Document("idClient", id);
Document found = réservation.find(query).first();
if(found == null) {
    throw new userException("User is not found");
}
if(found.getBoolean("accepter") == true) {
    throw new userException("Réservation is already accepted");
}
Document update = new Document("$set", new Document("typeChambre", typeChambre));

réservation.updateOne(query, update);

}
    


public void AnnulerRéservation() throws Exception , userException {
   
        Document query = new Document("idClient", id);
        Document clientDocument = réservation.find(query).first();
        if (clientDocument == null) {
            throw new userException("User is not found");
        }
        if(clientDocument.getBoolean("accepter") == true) {
            throw new userException("Réservation is already accepted");
        }
        réservation.deleteOne(query);

    

}
}
