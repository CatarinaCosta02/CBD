from neo4j import GraphDatabase

class lab44:

    def __init__(self, uri, user, password):
        self.driver = GraphDatabase.driver(uri, auth=(user, password))
        self.titles = ["1) List all players",
        "2) List all the teams with 'Toronto' in the name",
        "3) List the top3 oldest players",
        "4) List all the players in the 2020 seasons"
        "5) List all the teams LeBron James played for",
        "6) List all the players that were awarded in the 2019 season",
        "7) List all the players that are in the 'West' conference",
        "8) List the age of the players that pay in the 'SG' position",
        "9) List all the teams that have 'Toronto' in the name",
        "10) Liste the player that has the lowest weight"]
        
        self.queries = ["match (p:Player) return p",
        "match (t:Team) where t.name contains 'Toronto' return t",
        "match (p:Player) return distinct(p) as Player_age order by Player_age desc limit 3",
        "match (p:Player)-[r:WILL_PLAY]->(s:Season) where s.season = '2020' return p",
        "match (p:Player)-[r:PLAYS]->(t:Team) where p.name = 'LeBron James' return t",
        "match (p:Player)-[r:AWARDED]->(s:Season) where s.season = '2019' return p",
        "match (p:Player)-[r:PLAYS]->(t:Team) where t.conf = 'West' return p",
        "match (p:Player) where p.position = 'SG' return p.age",
        "match (p:Player)-[r:PLAYS]->(t:Team) where t.name contains 'Toronto' return p",
        "match (p:Player) return p order by p.weight limit 1"]


    def create(self):
        self.driver.session().run(
            "LOAD CSV WITH HEADERS FROM 'file:///NBA_player_of_the_week.csv' AS row \
            MERGE (p:Player {name:row.Player, position:row.Position,height:row.Height,weight:row.Weight,age:row.Age,draft_year:row.Draft_Year,value:row.Real_value}) \
            MERGE (t:Team {name:row.Team,conf:row.Conference}) \
            MERGE (s:Season {seasons:row.Seasons_in_league,shortcut:row.Season_short,season:row.Season}) \
            MERGE (p)-[r1:PLAYS]->(t) \
            MERGE (p)-[r2:AWARDED {date:row.Date}]->(s)\
            MERGE (p)-[r3:WILL_PLAY]->(s)")

    def queriesAndSave(self):
        f = open("CBD_L44c_output.txt", "a",encoding='utf-8')
        for i, query in enumerate(self.queries):
            try:
                result = self.driver.session().run(query)
                f.write("\n\n" + self.titles[i] + "\n")
                f.write(query + "\n\n---")
                results = [r for r in result.data()]

                for k in results[0].keys():
                    f.write(f"{k}")
            
                f.write("---")

                for r in results[:10]:
                    f.write("\n")
                    for v in r.values():
                        f.write(f" {str(v)}             ")

                if(len(results)>10):
                    f.write("\n")

                f.write("\n")
            except:
                pass
        f.close()

    def close(self):
        self.driver.close()
                    

if __name__ == "__main__":
    greeter = lab44("bolt://localhost:7687", "neo4j", "password")
    print("Connected to database!")
    greeter.create()
    greeter.queriesAndSave()
    greeter.close()