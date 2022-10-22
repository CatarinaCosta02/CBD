package org.example;

import redis.clients.jedis.Jedis;

import java.util.*;

public class Main {
    private Jedis jedis;


    public Main(){
        this.jedis = new Jedis();
    }


    public static void main(String[] args) {

        PostSet postSet = new PostSet();
        PostList postList = new PostList();
        PostHash postHash = new PostHash();

        List<String> followers_list = new ArrayList<String>();
        Map<String, String> users_hash = new HashMap<String, String>();
        Map<String, String> user_subs = new HashMap<String, String>();

        int numberUsers = 1;
        int numberMsg = 0;

        Scanner sc = new Scanner(System.in);

        // --------------------------------------------------------------- MENU ---------------------------------------------------------------------//

        while(true){
            System.out.println("Message System\n" +
                    "Chose your option\n" +
                    "1) Create a user\n" +
                    "2) Send Message\n" +
                    "3) Add a friend\n" +
                    "4) Check User friends messages\n" +
                    "5) Exit"
            );

            int option = sc.nextInt();

            switch (option){
                case 1: //opcao de criar um Utilizador

                    Scanner sc_user = new Scanner(System.in);
                    System.out.println("Username?");
                    String username = sc_user.nextLine();

                    if (!users_hash.containsKey(username)) {
                        users_hash.put("user" + String.valueOf(numberUsers), username);
                        postHash.addUser(users_hash);
                        numberUsers++;
                    }
                    System.out.println(postHash.getUserSet());
                    break;

                case 2: //mandar mensagem
                    Scanner sc_which_user = new Scanner(System.in);
                    Scanner sc_user_message = new Scanner(System.in);

                    System.out.println("User you want to chat?");
                    String user = sc_which_user.nextLine();

                    System.out.println("Message?");
                    String message = sc_user_message.nextLine();

                    postList.saveMessage(user,message);
                    System.out.println(postList.getMessages(user));
                    break;


                case 3: //adicionar um amigo

                    Scanner user_add_friend = new Scanner(System.in);
                    Scanner user_inline = new Scanner(System.in);

                    System.out.println(postHash.getUserSet());

                    System.out.printf("Which user are you? ");
                    String current_user = user_inline.nextLine();

                    System.out.println(postHash.getUserSet());

                    System.out.println("Who do you wanna be friends with? ");
                    String add_friend= user_inline.nextLine();

                    postSet.saveUser(current_user, add_friend);
                    break;

                case 4: //ver as mensagens do amigo

                    Scanner c_user = new Scanner(System.in);

                    System.out.printf("Which user are you? ");
                    String current_user2 = c_user.nextLine();

                    Set<String> following = postSet.getUser(current_user2);
                    if (following.size() == 0)
                        System.out.println("You don't follow anyone");
                    else {
                        for (String s_u: following) {
                            System.out.println("Message from " + s_u + ":");
                            List<String> msgs = postList.getMessages(s_u);
                            for(String m: msgs)
                                System.out.println(m);
                        }

                    }break;

            }while (sc.nextInt() != 5);


        }

        //System.out.println("Hello world!");
    }
}