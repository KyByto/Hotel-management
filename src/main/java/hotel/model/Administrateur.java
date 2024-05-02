package hotel.model;
import java.util.ArrayList;
import java.util.UUID;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;


public class Administrateur extends utilisateur {
    private  MongoCollection<Document> chambres = database.getCollection("chambres");
    private  MongoCollection<Document> administrateurs = database.getCollection("administrateurs");
    private  MongoCollection<Document> reservations = database.getCollection("reservations");
    public Administrateur( String nom , String prénom) {
    UUID randomUUID = UUID.randomUUID();
        
    // Convert UUID to string representation
    String randomId = randomUUID.toString();
    id = randomId;
    this.nom = nom;
    this.prénom = prénom;
    role = Role.Admin;
    administrateurs.insertOne(super.toDocument());

}

    public void CreerUneChambre(String typeChambre,  int prix) throws Exception {
        Chambre chambre = new Chambre(typeChambre, prix);
        chambres.insertOne(chambre.toDocument());
        


    }

    public void ModifierUneChambre(int numeroChambre, String typeChambre, Boolean disponible, int prix) throws userException , Exception  {
        Document query = new Document("numeroChambre", numeroChambre);
        Document update = new Document("$set", new Document("typeChambre", typeChambre).append("disponible", disponible).append("prix", prix));
   
        Document found = chambres.find(query).first();
        if(found == null) {
            throw new userException(numeroChambre + " is not found");
        }
        chambres.updateOne(query, update);
      
    }

    public void SupprimerUneChambre(int numeroDeChambre) throws userException , Exception {
       
            Document query = new Document("numeroDeChambre", numeroDeChambre);
            Document chambreDocument = chambres.find(query).first();
            if (chambreDocument == null) {
                throw new userException(numeroDeChambre + " is not found");
            }
            chambres.deleteOne(query);

        
    }
public void AccepterUneRéservation(String id) throws userException , Exception {
   
        Document chambreQuery = new Document("disponible", true);
        Document found = chambres.find(chambreQuery).first();
        
        if(found == null) {
            throw new userException("Pas de chambres disponibles");
        }
        Document query = new Document("idClient", id);
        Document update = new Document("$set", new Document("estAcceptée", true).append("numeroChambre", found.getInteger("numeroChambre")));

        System.out.println("Numero chambre" + found.getInteger("numeroChambre"));

        
        reservations.updateOne(query, update);
    
}


public void RefuserUneRéservation(String id) throws userException , Exception {
   
    Document query = new Document("idClient", id);
    Document found = reservations.find(query).first();
    if(found == null) {
        throw new userException(id + " is not found");
    }
    Document update = new Document("$set", new Document("estAnnulée", true));
    reservations.updateOne(query, update);




}

public ArrayList<Réservation> getAllReservations() throws userException, Exception {
    ArrayList<Réservation> allReservations = new ArrayList<>();
    try {
        FindIterable<Document> iterable = reservations.find();
        for (Document document : iterable) {
            Réservation réservation   = new Réservation(document.getString("idClient"), document.getString("dateDebut"), document.getString("dateFin"), document.getString("dateDemande")  ,document.getBoolean("estAcceptée") ,document.getBoolean("estAnnulée"));
            allReservations.add(réservation);
        }
    } catch (Exception e) {
        throw new userException(e.getMessage());
    }
    return allReservations;
}
}


