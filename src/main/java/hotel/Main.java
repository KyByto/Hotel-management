package hotel;

import java.util.ArrayList;
import org.bson.Document;

import hotel.model.*;
public class Main {

    public static void main(String[] args) {
        DatabaseManager.connectDB();
 Administrateur admin = new Administrateur("LE CHEF", "Otaku");
 Client client = new Client("zaki", "foulardo");
try {
    client.DemanderUneReservation("20/03/2023", "14/02/2021");

    client.DemanderUneReservation("30/04/2023","18/02/2021" );   
       

}
catch(Exception e) {
    System.out.println(e.getMessage());
}



    }
}