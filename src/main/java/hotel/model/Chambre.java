package hotel.model;
import org.bson.Document;

enum TypeChambre {
    Simple, Double, Triple , Quadriple

}
public class Chambre {
    public String numeroChambre;
    public TypeChambre typeChambre;
    public Boolean disponible;
    public Chambre( String typeChambre,  String numeroChambre , String étatDeLaChambre) {
        this.numeroChambre = numeroChambre;
        this.typeChambre = TypeChambre.valueOf(typeChambre);
        this.disponible = étatDeLaChambre == "Réservé" ? true : false;
    }
    public Chambre(String typeChambre , String numeroChambre , Boolean disponible) {
          this.numeroChambre = numeroChambre;
        this.typeChambre = TypeChambre.valueOf(typeChambre);
        this.disponible = disponible;
    }
    
    public Document toDocument() {
       return new Document()
               .append("numeroChambre", numeroChambre)
               .append("typeChambre", typeChambre.toString())
               .append("disponible", disponible);
    }
}
