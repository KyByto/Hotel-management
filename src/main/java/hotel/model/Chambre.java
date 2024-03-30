package hotel.model;

import org.bson.Document;

enum TypeChambre {
    Simple, Double, Suite

}
public class Chambre {
    public static int nombreDeChambre = 0;
    private int numeroChambre;
    private TypeChambre typeChambre;
    private Boolean disponible;
    private int prix;
    public Chambre( String typeChambre,  int prix) {
nombreDeChambre++;
        this.numeroChambre = nombreDeChambre;
        this.typeChambre = TypeChambre.valueOf(typeChambre);
        this.disponible = true;
        this.prix = prix;
    }
    
    public Document toDocument() {
       return new Document()
               .append("numeroChambre", numeroChambre)
               .append("typeChambre", typeChambre.toString())
               .append("disponible", disponible)
               .append("prix", prix);
    }
}
