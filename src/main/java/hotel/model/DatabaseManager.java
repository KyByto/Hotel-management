package hotel.model;

import com.mongodb.client.MongoClients;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DatabaseManager {
    private static MongoDatabase database;
    private static MongoClient mongoClient;

    public static void connectDB() {
        try {
            mongoClient = MongoClients.create("mongodb+srv://zakoronaldo77:kybyto123@hotel.anq10kn.mongodb.net/?retryWrites=true&w=majority&appName=hotel");
            database = mongoClient.getDatabase("my_database");
            System.out.println("Connected to the database successfully");
        } catch (Exception e) {
            System.err.println("Error connecting to MongoDB: " + e.getMessage());
        }
    }

    public static MongoDatabase getDatabase() {
        if (database == null) {
            connectDB(); // Connect to the database if not already connected
        }
        return database;
    }

    public static void closeDB() {
        try {
            if (mongoClient != null) {
                mongoClient.close();
                System.out.println("Closed MongoDB connection");
            }
        } catch (Exception e) {
            System.err.println("Error closing MongoDB connection: " + e.getMessage());
        }
    }





}
