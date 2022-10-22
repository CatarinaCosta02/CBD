package org.example;

import java.util.Map;
import java.util.Set;
import redis.clients.jedis.Jedis;
import java.util.HashMap;

public class Main {

    //usado o codigo do SimplePost, disponivel no elearning
    private Jedis jedis;
    public static String USERS = "users"; // Key set for users' name

    public Main() {
        this.jedis = new Jedis();
    }

    //Sets the specified fields to their respective values in the hash stored at key. This command overwrites any specified fields already existing in the hash. If key does not exist, a new key holding a hash is created.

    public void saveUser(Map<String, String> username) {
        jedis.hmset(USERS, username);
    }

    //hgetall method is used to get all the fields and its associated values contained in the hash value stored at a key.
    public Map<String,String> getUser() {
        return jedis.hgetAll(USERS);
    }

    public Set<String> getAllKeys() {
        return jedis.keys("*");
    }

    public static void main(String[] args) {
        Main board = new Main();

        Map<String,String> map = new HashMap<>();
        // set some users
        //String[] users = { "Ana", "Pedro", "Maria", "Luis" };

        map.put("Ana", "17");
        map.put("Pedro", "18");
        map.put("Maria", "19");
        map.put("Luís", "20");

        board.saveUser(map);
        System.out.print("keys");
        board.getAllKeys().stream().forEach(System.out::println);

        Map <String, String> mapa_users = board.getUser();

        for (String user: map.keySet())
            System.out.println(mapa_users.keySet());
        //board.getAllKeys().stream().forEach(System.out::println);
        //board.getUser().forEach(System.out::println);
    }
}