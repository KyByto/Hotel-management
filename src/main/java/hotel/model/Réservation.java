package hotel.model;
import java.util.UUID;
import org.bson.Document;

public class Réservation {
    
    private String id;
    private String idClient;
    String dateDemande;
    String dateDebut;
    String dateFin;
    Boolean accepté;
    Boolean decliné;
    String numeroChambre;
    public Réservation( String idClient, String dateDebut, String dateFin, String dateDemande , Boolean accepté, Boolean decliné) {
        UUID randomUUID = UUID.randomUUID();
        
        // Convert UUID to string representation
        String randomId = randomUUID.toString();
        this.id = randomId;
        this.idClient = idClient;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.accepté = accepté;
        this.decliné = decliné;
        this.dateDemande = dateDemande;
    }
    public Réservation( String idClient, String dateDebut, String dateFin, String dateDemande , Boolean accepté, Boolean decliné, String numeroChambre) {
        this(idClient, dateDebut, dateFin, dateDemande, accepté, decliné);
        this.numeroChambre = numeroChambre;
    }
    public  Document toDocument() {
        return new Document("idClient", idClient)
                .append("dateDebut", dateDebut)
                .append("dateFin", dateFin)
                .append("estAcceptée", accepté)
                .append("estAnnulée", decliné)
                .append("dateDemande", dateDemande)
                .append("numeroChambre", numeroChambre );
    }
public   Réservation toRéservation(Document document) {
    return new Réservation(document.getString("idClient"), document.getString("dateDebut"), document.getString("dateFin"), document.getString("dateDemande") , document.getBoolean("estAcceptée"), document.getBoolean("estAnnulée") , document.getString("numeroChambre"));
}
public String getId() {
    return id;
}
public String getIdClient() {
    return idClient;
}
public String getDateDebut() {
    return dateDebut;
}
public String getDateFin() {
    return dateFin;
}
public Boolean getAccepté() {
    return accepté;
}
public Boolean getDecliné() {
    return decliné;
}
public String getDateDemande() {
    return dateDemande;

}
public String getNumeroChambre() {
    return numeroChambre;
}

}