package com.projetoMaven.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Filters.*;

import org.bson.Document;

public class AlineaD {
    static MongoCollection<Document> collection;

    public static void main(String[] args) {

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {

            MongoDatabase database = mongoClient.getDatabase("cbd");
            collection = database.getCollection("restaurants");

            AlineaD alineaD = new AlineaD();

            try {

                System.out.println("Numero de localidades distintas: " + alineaD.countLocalidades());

                System.out.println();
                System.out.println("Numero de restaurantes por localidade:");
                Map<String, Integer> restByLocalidade = alineaD.countRestByLocalidade();
                for (String key : restByLocalidade.keySet()) {
                    System.out.println("-> " + key + " - " + restByLocalidade.get(key));
                }

                System.out.println();
                System.out.println("Nome de restaurantes contendo 'Park' no nome:");
                List<String> lista= alineaD.getRestWithNameCloserTo("Park");
                for (String restaurante : lista) {
                    System.out.println("-> " + restaurante);
                }

                System.out.close();
            } catch (Exception e) {
                System.err.println("Erro ao conectar-se à base de dados MongoDB: " + e);
            }


        } catch (Exception e) {
            System.err.println("Error connecting to MongoDB database: " + e);
        }
    }

    public int countLocalidades() {

        MongoCursor<String> cursor = collection.distinct("localidade", String.class).iterator();

        int count = 0;
        try {
            while (cursor.hasNext()) {
                count++;
                cursor.next();
            }
        } finally {
            cursor.close();
        }
        return count;
    }

    Map<String, Integer> countRestByLocalidade(){
        Map<String, Integer> mapa = new HashMap<>();
        try {
            AggregateIterable<Document> docs = collection.aggregate(Arrays.asList(group("$localidade", Accumulators.sum("num", 1))));
            for (Document d : docs) {
                mapa.put(d.get("_id").toString(), (int) (d.get("num")));
            }

        }catch (Exception e){
            System.err.println("Error reading from MongoDB collection: " + e);
        }
        return mapa;
    }

    List<String> getRestWithNameCloserTo(String name) {
        List<String> lst = new ArrayList<String>();
        try {
            FindIterable<Document> docs = collection.find(regex("nome", String.format("(%s)", name)));
            for (Document d : docs) {
                lst.add((String) d.get("nome"));
            }

        }catch (Exception e){
            System.err.println("Error reading from MongoDB collection: " + e);
        }
        return lst;
    }
}
