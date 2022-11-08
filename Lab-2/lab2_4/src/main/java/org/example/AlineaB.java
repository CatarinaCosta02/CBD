package org.example;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;

public class AlineaB {
    public static void main(String[] args) {

        //ConnectionString connString = new ConnectionString();
        try(MongoClient mongoClient = MongoClients.create( "mongodb://localhost:27017" )){
            MongoDatabase database = mongoClient.getDatabase("cbd");
            MongoCollection<Document> collection = database.getCollection("restaurants");
            //Document document = collection.find(eq("gastronomia", "American")).first();

            //pesquisa por indices
            System.out.println("Pesquisa por indices");
            System.out.println("localidade");

            try {
                String resultCreateIndex = collection.createIndex(Indexes.ascending("localidade"));
                System.out.println(String.format("Index created: %s", resultCreateIndex));
            } catch (MongoException me) {
                System.err.println("Erro ao criar um indice ascendente para a localidade: " + me);
            }

            System.out.println("gastronomia");
            try {
                String resultCreateIndex = collection.createIndex(Indexes.ascending("gastronomia"));
                System.out.println(String.format("Index created: %s", resultCreateIndex));
            } catch (MongoException me) {
                System.err.println("Erro ao criar um indice ascendente para a gastronomia: " + me);
            }

            System.out.println("nome");
            try {
                String resultCreateIndex = collection.createIndex(Indexes.text("name"));
                System.out.println(String.format("Index created: %s", resultCreateIndex));
            } catch (MongoException me) {
                System.err.println("Erro ao criar um indice ascendente para texto: " + me);
            }

            for (Document index : collection.listIndexes()) {
                System.out.println(index.toJson());
            }

        }catch (Exception e) {
            System.err.println("Erro ao conectar-se à base de dados MongoDB: " + e);
        }

    }
}
