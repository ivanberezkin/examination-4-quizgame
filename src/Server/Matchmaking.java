package Server;

import java.util.HashSet;
import java.util.Set;

public class Matchmaking {

    private final static Set<Connections> matchMakingList = new HashSet<Connections>();

    public Matchmaking(Connections connections) {
     matchMakingList.add(connections);
    }
}
