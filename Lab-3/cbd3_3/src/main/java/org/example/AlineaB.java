package org.example;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

import javax.xml.transform.Result;

public class AlineaB {
    public static void main(String[] args) {

        Cluster b = Cluster.builder().addContactPoint("127.0.0.1").build();
        Session session = b.connect("cbd3_2");

        //1. Os últimos 3 comentários introduzidos para um vídeo;

        ResultSet result = session.execute("SELECT * FROM Comentario_Video LIMIT 3;");
        System.out.println(result.all());
        //2. Lista das tags de determinado vídeo;

        result = session.execute("SELECT etiquetas FROM Video WHERE id = 2;");
        System.out.println(result.all());

        //4. Os últimos 5 eventos de determinado vídeo realizados por um utilizador;
        result = session.execute("SELECT * FROM Eventos WHERE video_id = 3  and username = 'Eça';");
        System.out.println(result.all());

        //5. Vídeos partilhados por determinado utilizador (maria1987, por exemplo) num determinado período de tempo (Agosto de 2017, por exemplo);
        result = session.execute("SELECT * from Video WHERE username = 'Cristiano' and upload_time_stamp < '2022-12-10 17:13:16.379Z' ALLOW FILTERING;");
        System.out.println(result.all());

        //7. Todos os seguidores (followers) de determinado vídeo;
        result = session.execute("SELECT username from Video_followers WHERE video_id = 14;");
        System.out.println(result.all());



    }
}
