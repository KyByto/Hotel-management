package hotel.model;

import org.bson.Document;

import com.mongodb.client.MongoDatabase;

enum Role  {
    Admin, client
}
public class utilisateur {
     MongoDatabase database = DatabaseManager.getDatabase();
    protected String id;
    protected String nom, prénom;
Role role;


     public Document toDocument() {
        return new Document("id", id)
                .append("nom", nom)
                .append("prénom", prénom)
                .append("Role", role.toString());
    }
    
}

