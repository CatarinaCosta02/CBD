package org.example;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class PostSet {

    private Jedis jedis;
    public static String USERS_F = "users_f";

    public PostSet() {
        this.jedis = new Jedis();
    }

    //sadd -> Add the specified members to the set stored at key. Specified members that are already a member of this set are ignored.
    public void saveUser(String username, String followers) {
        jedis.sadd(username, followers);
    }
    public Set<String> getUser(String username) {
        return jedis.smembers(username);
    }

    public Set<String> getAllKeys() {
        return jedis.keys("*");
    }
}
