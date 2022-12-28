package org.example;

// inserção, edição e
//pesquisa de registos na base de dados

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class AlineaA {
    private Session session;

    public static void main(String[] args) {
        Cluster b = Cluster.builder().addContactPoint("127.0.0.1").build();
        Session session = b.connect("cbd3_2");

        // Inserção de um User:
        Insert insert = QueryBuilder
                .insertInto("cbd3_2", "User")
                .value("username", "Mário")
                .value("platform_time_stamp","2022-12-06 16:26:28.030000+0000")
                .value("name", "Maŕio Sá Carneiro")
                .value("email", "mario@ua.pt");
        System.out.println(insert.toString());
        ResultSet result = session.execute(insert.toString());
        System.out.println(result);

        // edição de um User:
        session.execute("UPDATE User SET name='Lila' WHERE username='Mário';");
        result = session.execute("SELECT * FROM User;");
        System.out.println(result);
// UPDATE foo SET v=? WHERE k=?;
        //

        




    }

}
