package org.example;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;


public class AlineaA {
    public static void main(String[] args) {

        //ConnectionString connString = new ConnectionString();
        try(MongoClient mongoClient = MongoClients.create( "mongodb://localhost:27017" )){
            MongoDatabase database = mongoClient.getDatabase("cbd");
            MongoCollection<Document> collection = database.getCollection("restaurants");
            //Document document = collection.find(eq("gastronomia", "American")).first();

            //test insertion

            try {
                InsertOneResult result = collection.insertOne(new Document()
                        .append("_id", new ObjectId())
                        .append("nome", "Morris Park Bake Shop")
                        .append("building", "1007")
                        .append("rua","Morris Park Ave")
                        .append("zipcode","10462")
                        .append("coord", Arrays.asList(-73.856077, 40.848447)));
                System.out.println("Success! Inserted document id: " + result.getInsertedId());
            } catch (MongoException me) {
                System.err.println("Unable to insert due to an error: " + me);
            }
            System.out.println("Restaurante inserido!");

            //Edit a restaurant

            try {
                UpdateResult result = collection.updateOne(eq("nome", "Cantiflas"), Updates.set("nome","Restaurante da Vila"));
                System.out.println("Modified document count: " + result.getModifiedCount());
            } catch (MongoException me) {
                System.err.println("Unable to update due to an error: " + me);
            }
            System.out.println("Restaurante editado!");

            //Search a restaurant
            //example

            System.out.println("Liste todos os restaurantes que tenham gastronomia American:");
            try {
                FindIterable<Document> docs = collection.find(eq("gastronomia", "American"));
                for (Document d : docs) {
                    System.out.println(d.toJson());
                }
            }catch (Exception e){
                System.err.println("Error reading from MongoDB collection: " + e);
            }

        }catch (Exception e) {
            System.err.println("Erro ao conectar-se à base de dados MongoDB: " + e);
        }

    }
}








