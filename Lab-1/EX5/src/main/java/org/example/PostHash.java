package org.example;

import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Set;

public class PostHash {

    private Jedis jedis;
    public static String USERS = "users";

    public PostHash() { this.jedis = new Jedis(); }

    //hmset -> Sets the specified fields to their respective values in the hash stored at key. This command overwrites any specified fields already existing in the hash.
    public void addUser (Map<String, String> m) { jedis.hmset(USERS, m); }
    //hgetall -> Returns all fields and values of the hash stored at key. In the returned value, every field name is followed by its value, so the length of the reply is twice the size of the hash.
    public Map<String, String> getUserSet() { return jedis.hgetAll(USERS); }

    public Set<String> getAllKeys() { return jedis.keys("*"); }


}
