package org.example;

import redis.clients.jedis.Jedis;

public class Main {
    public static String USERS = "users"; // Key set for users' name
    public static void main(String[] args) {

        Jedis jedis = new Jedis();
        String[] users = { "Ana", "Pedro", "Maria", "Luis" };
// jedis.del(USERS_KEY); // remove if exists to avoid wrong type
        for (String user : users)
            jedis.sadd(USERS, user);
        jedis.smembers(USERS).forEach(System.out::println);
        jedis.close();




        //System.out.println("Hello world!");
    }
}