package org.example;

import redis.clients.jedis.Jedis;

import java.util.List;
//import java.util.Set;

public class SimplePost {
    private Jedis jed;

    public static String USERS_KEY = "users"; // Key set for users' name

    //construtor

    public SimplePost(){

        this.jed = new Jedis();
    }

    //rpush :- It insert one or more elements at the tail of the list value. If list value does not exist, then it first creates a key holding empty list value before performing the insert operation.
    public void saveUser(String username) {

        jed.rpush(USERS_KEY, username);
    }

    //lrange method is used to get one or elements from the list value, defined by the offsets argument.

    public List<String> getUser(){

        return jed.lrange(USERS_KEY, 0,-1);
    }

    public static void main(String[] args) {
        SimplePost jedis = new SimplePost();
        // some users
        String[] users = { "Ana", "Pedro", "Maria", "Luis" };
        // jedis.del(USERS_KEY); // remove if exists to avoid wrong type
        for (String user : users)
            jedis.saveUser(user);
        jedis.getUser().forEach(System.out::println);
        //edis.close();
    }
}
