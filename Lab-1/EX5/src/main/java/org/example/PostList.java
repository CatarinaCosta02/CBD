package org.example;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

public class PostList {

    private Jedis jedis;

    //public static String USERS_KEY = "users"; // Key set for users' name

    //construtor

    public PostList(){

        this.jedis = new Jedis();
    }

    //lpush :- It insert one or more elements at the head of the list value. If list value does not exist, then it first creates a key holding empty list value before performing the insert operation.
    public void saveMessage(String user, String username) {

        jedis.lpush(user, username);
    }

    //lrange method is used to get one or elements from the list value, defined by the offsets argument.

    public List<String> getMessages(String user){

        return jedis.lrange(user, 0,-1);
    }

    public Set<String> getAllKeys(){
        return jedis.keys("*");
    }
}
