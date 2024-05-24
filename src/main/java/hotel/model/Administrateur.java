package hotel.model;
import java.util.ArrayList;
import hotel.model.Administrateur;
import java.util.UUID;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;


public class Administrateur extends utilisateur {
    
    private  MongoCollection<Document> chambres = DatabaseManager.getDatabase().getCollection("chambres");
    private  MongoCollection<Document> administrateurs = DatabaseManager.getDatabase().getCollection("administrateurs");
    private  MongoCollection<Document> reservations = DatabaseManager.getDatabase().getCollection("reservations");
    public Administrateur( String nom , String prénom ) {
    UUID randomUUID = UUID.randomUUID();
        
    // Convert UUID to string representation
    String randomId = randomUUID.toString();
    id = randomId;
    this.nom = nom;
    this.prénom = prénom;
    role = Role.Admin;

}

    public void CreerUneChambre(String typeChambre,  String numeroDeChambre , String étatDeLaChambre) throws Exception {
        Chambre chambre = new Chambre(typeChambre, numeroDeChambre , étatDeLaChambre);
        chambres.insertOne(chambre.toDocument());
        


    }

    public void ModifierUneChambre(String numeroChambre, String typeChambre, String disponible, String nouveauNumeroDeChambre) throws userException , Exception  {
        Document query = new Document("numeroChambre", numeroChambre);
        Boolean état = disponible == "Réservé" ? true : false;
        Document update = new Document("$set", new Document("typeChambre", typeChambre).append("disponible", état).append("numeroChambre", nouveauNumeroDeChambre));
   
        Document found = chambres.find(query).first();
        if(found == null) {
            throw new userException(numeroChambre + " is not found");
        }
        chambres.updateOne(query, update);
      
    }

    public void SupprimerUneChambre(String numeroDeChambre) throws userException , Exception {
       
            Document query = new Document("numeroChambre", numeroDeChambre);
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

public void supprimerUneReservation(String id) {
    try {
        Document query = new Document("idClient", id);
        reservations.deleteOne(query);
    }
    catch(Exception e) {
        System.out.println(e.getMessage());
    }
}

public ArrayList<Réservation> getAllReservations() throws userException, Exception {
    ArrayList<Réservation> allReservations = new ArrayList<>();
    try {
        FindIterable<Document> iterable = reservations.find();
        for (Document document : iterable) {
            Réservation réservation   = new Réservation(document.getString("idClient"), document.getString("dateDebut"), document.getString("dateFin"), document.getString("dateDemande")  ,document.getBoolean("estAcceptée") ,document.getBoolean("estAnnulée") , document.getString("typeChambre"));
            allReservations.add(réservation);
        }
    } catch (Exception e) {
        throw new userException(e.getMessage());
    }
    return allReservations;
}

public ArrayList<Chambre> getAllChambre() throws userException, Exception {
    ArrayList<Chambre> allChambre = new ArrayList<>();
    try {
        FindIterable<Document> iterable = chambres.find();
        for (Document document : iterable) {
            
            Chambre chambre   = new Chambre( document.getString("typeChambre") , document.getString("numeroChambre") , document.getBoolean("disponible"));
       allChambre.add(chambre);
        }
    } catch (Exception e) {
        throw new userException(e.getMessage());
    }
    return allChambre;
}

}


