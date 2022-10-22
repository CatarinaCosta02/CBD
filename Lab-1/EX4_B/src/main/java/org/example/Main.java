package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.Tuple;

import java.io.*;
import java.util.Set;
import java.util.*;

import static java.util.stream.Collectors.*;

//raciocinio
// neste exercicio o pensamento é parecido so que com listas, uma vez que, o zrangeByScore, nao funciona para setters
// o ficheiro tem tratamento de dados
// usei o zrevrangeWithScore
//fazer com listas



public class Main {
    private Jedis jedis;

    public static String NAMES = "names";

    //construtor
    public Main(){
        this.jedis = new Jedis();
    }

    public void addUser(String user, int score){
        jedis.zadd(NAMES, score, user);
    }

    public List<Tuple> getUser (){

        return jedis.zrevrangeWithScores(NAMES,0,-1);

    }

    public HashMap<String, Integer> getCompletions(String s) {
        List<Tuple> names = getUser();
        HashMap<String, Integer> completions = new HashMap<String,  Integer>();
        for(Tuple tuple : names){
            if(tuple.getElement().startsWith(s)){
                completions.put(tuple.getElement(), (int) tuple.getScore());
            }
        }
        return completions;
    }

    public Set<String> getAllKeys(){
        return jedis.keys("*");
    }

    public static void main( String[] args) throws IOException {


        Main csvList = new Main();

        try{
            FileInputStream file = new FileInputStream("nomes-registados-2020.csv");
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                String[] data = line.split(";");
                csvList.addUser(data[0], Integer.parseInt(data[1]));

            }sc.close();
        }catch(IOException e) {
            e.printStackTrace();
        }

        Scanner l = new Scanner(System.in);
        String pal;

        while (true) {
            System.out.println("Search for ('Enter' for quit): ");
            pal = l.nextLine();
            String input = l.nextLine();

            if (input.length() == 0) {
                break;
            }
            Map<String, Integer> filter = csvList.getCompletions(pal).entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect((toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new)));
            //System.out.println(input);
            for (Map.Entry<String,Integer> entry : filter.entrySet()) {

                    System.out.println(entry.getKey());

            }

        }

        l.close();

    }
}
