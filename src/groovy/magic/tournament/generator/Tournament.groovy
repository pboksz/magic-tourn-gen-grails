package magic.tournament.generator

/**
 * The main class that creates the tournament
 * Author: Phillip Boksz
 * Date: 2/24/12
 * Time: 10:37 AM
 */
class Tournament {
    private static int round = 1
    private static int maxRound = 3

    static int getRound() {
        return round
    }

    static void nextRound() {
        Tournament.round += 1
    }

    static int getMaxRound(){
       return maxRound
    }

    static void setMaxRound(int max)
    {
       this.maxRound = max
    }

    static void registerPlayers(ArrayList<String> playerNames) {
        playerNames.each { name ->
            PlayerPool.addNewPlayer(name)
        }
    }

    static SortedMap<String, PlayerInfo> getRoundPairings()
    {
       RoundPairings rp = new RoundPairings()
       rp.getRoundPairings()
    }
}
