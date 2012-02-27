package magic.tournament.generator

/**
 * Holds a list of players and adds records of wins and such
 * Author: Phillip Boksz
 * Date: 2/24/12
 * Time: 10:33 AM
 */
class PlayerPool {
    private static SortedMap<String, PlayerInfo> listOfPlayers = new TreeMap<String, PlayerInfo>()

    static SortedMap<String, PlayerInfo> getListOfPlayers() {
        return listOfPlayers
    }

    static def void createNewPlayer(String name) {
        listOfPlayers.put(name, new PlayerInfo(name))
    }

    //TODO remove this and put it in PlayerInfo
    private static SortedMap<Integer, ArrayList<PlayerPair>> listOfRoundPairs = new TreeMap<Integer, ArrayList<PlayerPair>>()

    static SortedMap<Integer, ArrayList<PlayerPair>> getListOfRoundPairs() {
        return listOfRoundPairs
    }

    static void addRoundToListOfRoundPairs(int round, ArrayList<PlayerPair> pairings) {
        listOfRoundPairs.put(round, pairings)
    }

    /**
     * Sets a won round to the player with individual wins and losses as well
     * @param name the player name
     * @param individualWins how many wins
     * @param individualLosses how many losses
     */
    static def void setWonRound(String name, int individualWins, int individualLosses) {
        def player = listOfPlayers.get(name)
        player.wonRound()
        player.addIndividualWins(individualWins)
        player.addIndividualLosses(individualLosses)
        listOfPlayers.remove(name)
        listOfPlayers.put(name, player)
    }

    /**
     * Sets a lost round to the player with individual wins and losses as well
     * @param name the player name
     * @param individualWins how many wins
     * @param individualLosses how many losses
     */
    static def void setLostRound(String name, int individualWins, int individualLosses) {
        def player = listOfPlayers.get(name)
        player.lostRound()
        player.addIndividualWins(individualWins)
        player.addIndividualLosses(individualLosses)
        listOfPlayers.remove(name)
        listOfPlayers.put(name, player)
    }

    /**
     * Sets a bye round to specific player
     * @param name the player name
     */
    static def void setByeRound(String name) {
        def player = listOfPlayers.get(name)
        player.byeRound()
        listOfPlayers.remove(name)
        listOfPlayers.put(name, player)
    }
}
