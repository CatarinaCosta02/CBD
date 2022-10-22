package org.example;

import redis.clients.jedis.Jedis;

import java.io.File;
import java.util.*;


public class AutoCompleate {
    //raciocinio: fazer uma lista de todos os nomes,
    // meter sem repeticoes, ou seja usar um set primeiro
    // fazer uma função que dada uma letra, mete numa lista diferente todos os nomes que contenham essa letra
    // ordenar por ordem alfabetica a lista a fornecer
    // abrir o ficheiro
    // ler as linhas do ficheiro
    // fazer um input da palavra a meter
    // dar o search de todas os nomes que tenham a letra
    // devolver a lista
    private Jedis jedis;

    public static String NAMES = "names";

    //construtor

    public AutoCompleate(){
        this.jedis = new Jedis();
    }

    //setter com os nomes dos users, para que nao haja repetição
     public void addUser(String user){

        jedis.sadd(NAMES,user);
     }

    public Set<String> getUser() {
        return jedis.smembers(NAMES);
    }

    //fazer uma função que dada uma letra, mete numa lista diferente todos os nomes que contenham essa letra
    public ArrayList<String> getComplete (String c){
        Set <String> names = getUser();
        ArrayList<String> complete = new ArrayList<String>();
        for(String name: names){
            if(name.startsWith(c)){
                complete.add(name);
            }
        }
    // sort por ordem alfabetica
        Collections.sort(complete);
        return complete;
    }


    public static void main(String[] args) {

        AutoCompleate auto = new AutoCompleate();

        //abrir o ficheiro dos nomes
        try{
            File file = new File("names.txt");
            //fazer o input

            Scanner sc = new Scanner(file);
            // ler as linhas do ficheiro
            while(sc.hasNextLine()){
                auto.addUser(sc.nextLine());
            }
            sc.close();
        }catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("Search for ('Enter' for quit): \n");

        // fazer um input da palavra ou letra a meter
        Scanner sc = new Scanner (System.in);
        String pal;

        // dar o search de todas os nomes que tenham a letra
        while (true){

            System.out.println("Search for ('Enter' for quit): \n");
            pal = sc.nextLine();

            if( pal.length() == 0){
                break;
            }

            System.out.println();
            auto.getComplete(pal).forEach(System.out::println);

        }

    }

}
