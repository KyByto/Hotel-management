package hotel.model;
import org.bson.Document;
import java.util.UUID;

import com.mongodb.client.MongoCollection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Client extends utilisateur {
    private  MongoCollection<Document> clients = database.getCollection("clients");
    private MongoCollection<Document> réservation = database.getCollection("reservations");
public Client( String nom , String prénom) {
    UUID randomUUID = UUID.randomUUID();
    // Convert UUID to string representation
    String randomId = randomUUID.toString();
    this.id = randomId;
     this.nom = nom;
     this.prénom = prénom;
     role = Role.client;
     clients.insertOne(super.toDocument());
}

public void DemanderUneReservation(String dateDebut , String DateFin) throws userException , Exception  {
   
    LocalDateTime currentDateTime = LocalDateTime.now();
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateTime = currentDateTime.format(formatter);
        Réservation reservation = new Réservation(id , dateDebut , DateFin ,  formattedDateTime, false , false);

        réservation.insertOne(reservation.toDocument());
    
  

}



public void ModifierRéservation(String dateDebut , String dateFin) throws userException , Exception {

Document query = new Document("idClient", id);
Document found = réservation.find(query).first();
if(found == null) {
    throw new userException("User is not found");
}
if(found.getBoolean("accepter") == true) {
    throw new userException("Réservation is already accepted");
}
Document update = new Document("$set", new Document("dateDebut", dateDebut).append("dateFin", dateFin));

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
