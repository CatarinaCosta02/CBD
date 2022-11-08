package org.example;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
//import static com.mongodb.client.model.Filters.lte;


public class AlineaC {


    public static void main(String[] args) {

        //ConnectionString connString = new ConnectionString();
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("cbd");
            MongoCollection<Document> collection = database.getCollection("restaurants");
            //Document document = collection.find(eq("gastronomia", "American")).first();


            System.out.println("Encontre os restaurantes que obtiveram uma ou mais pontuações (score) entre [80 e 100].");
            try {
                FindIterable<Document> docs = collection.find(and(gte("grades.score", 80), lte("grades.score", 100)));
                for (Document d : docs){
                    System.out.println(d.toJson());
                }
            } catch (MongoException me) {
                System.err.println("Erro ao criar um indice ascendente para a gastronomia: " + me);
            }

            System.out.println("Indique os restaurantes com latitude inferior a -95,7."); //neste caso a latitude ia dar 0 entao fiz para a longitude
            try {
                Bson projection = Projections.fields(Projections.include("restaurante_id"), Projections.include("nome"), Projections.include("address.coord"));
                FindIterable<Document> docs = collection.find(lt("address.coord.0", -95.7)).projection(projection);
                for (Document d : docs){
                    System.out.println(d.toJson());
                }
            } catch (MongoException me) {
                System.err.println("Erro ao criar: " + me);
            }

            System.out.println("Liste o restaurant_id, o nome, a localidade e a gastronomia dos restaurantes localizados em \"Staten Island\", \"Queens\", ou \"Brooklyn\".");
            try {
                Bson projection = fields(include("restaurant_id"), include("nome"), include("localidade"),include("gastronomia"), excludeId());
                FindIterable<Document> docs = collection.find(or(eq("localidade", "Staten Island" ),eq("localidade", "Queens"), eq("localidade", "Brooklyn"))).projection(projection);
                for (Document d : docs){
                    System.out.println(d.toJson());
                }
            } catch (MongoException me) {
                System.err.println("Erro ao criar: " + me);
            }

            System.out.println("Apresente os primeiros 15 restaurantes localizados no Bronx, ordenados por ordem crescente de nome.");
            try {
                FindIterable<Document> docs = collection.find(eq("localidade", "Bronx")).sort(new Document("nome", 1)).limit(15);
                for (Document d : docs){
                    System.out.println(d.toJson());
                }
            } catch (MongoException me) {
                System.err.println("Erro ao criar: " + me);
            }

            System.out.println("Liste todos os restaurantes que tenham pelo menos um score superior a 85.");
            try {
                FindIterable<Document> docs = collection.find(gt("grades.score", 85));
                for (Document d : docs){
                    System.out.println(d.toJson());
                }
            } catch (MongoException me) {
                System.err.println("Erro ao criar: " + me);
            }


        } catch (Exception e) {
            System.err.println("Erro ao conectar-se à base de dados MongoDB: " + e);
        }
    }
}
