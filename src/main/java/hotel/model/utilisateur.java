package hotel.model;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import com.mongodb.client.MongoDatabase;

enum Role  {
    Admin, client
}
public class utilisateur {
       private  static MongoCollection<Document> clients = DatabaseManager.getDatabase().getCollection("clients");
    private  static MongoCollection<Document> admins = DatabaseManager.getDatabase().getCollection("administrateurs");
    public String id;
    public String nom, prénom;
Role role;



     public Document toDocument() {
        return new Document("id", id)
                .append("nom", nom)
                .append("prénom", prénom)
                .append("Role", role.toString());
    }
     public static String isClient(String email , String password) {
                 Document query = new Document("email", email).append("password", password);
            Document client = clients.find(query).first();
            
            if(client !=null) {
                return client.getString("id");
            }
            else return null;
         
     }
     
      public static Document isAdmin(String email , String password) {
                 Document query = new Document("email", email).append("password", password);
            Document admin = admins.find(query).first();
            
            if(admin !=null) {
                return admin;
            }
            else return null;
         
     }
    
}

