package magic.tournament.generator

/**
 * Holds a list of players and adds records of playerWins and such
 * Author: Phillip Boksz
 * Date: 2/24/12
 * Time: 10:33 AM
 */
class PlayerPool {
    private static SortedMap<String, PlayerInfo> mapOfPlayers = new TreeMap<String, PlayerInfo>()
    private static Random seed = new Random()

    static SortedMap<String, PlayerInfo> getMapOfPlayers() {
        return mapOfPlayers
    }

    static void addNewPlayer(ArrayList<String> playerNames, String name) {
        mapOfPlayers.put(name, new PlayerInfo(name, seed.nextInt(100), playerNames))
    }

    //TODO drop player from each possible opponentName list
    //This is postponed for now
    static void dropPlayer(int round, String dropped, String getsBye) {
        mapOfPlayers.remove(dropped)
        def player = mapOfPlayers.get(getsBye)
        player.byeRound()
        player.removeRoundPairing(round)
        save(player)
    }

    static void dropAllPlayers() {
        mapOfPlayers.clear()
    }

    static ArrayList<PlayerInfo> getListOfPlayers() {
        ArrayList<PlayerInfo> listOfPlayers = new ArrayList<PlayerInfo>()
        mapOfPlayers.each { infoEntry ->
            listOfPlayers.add(infoEntry.value)
        }
        return listOfPlayers
    }

    /**
     * sets the outcome of the round for each player
     * @param playerName the player's playerName
     * @param opponentName the opponentName's playerName
     * @param wins the player's wins
     * @param losses the player's losses
     */
    static void setPlayerOutcome(String playerName, String opponentName, int wins, int losses) {
        def player = mapOfPlayers.get(playerName)
        if (player) {
            if (opponentName != "Bye") {
                if (wins > losses) {
                    player.wonRound()
                }
                else {
                    player.lostRound()
                }
                player.addIndividualWins(wins)
                player.addIndividualLosses(losses)
            }
            else {
                player.byeRound()
            }
            save(player)
        }
    }

    /**
     * Sets both players info object to record the round and opponentName for the round
     * @param round number
     * @param playerName name of player
     * @param opponentName name of opponent
     */
    static void setRoundPairing(int round, String playerName, String opponentName) {
        if (playerName != "Bye") {
            def player = mapOfPlayers.get(playerName)
            player.addRoundPairing(round, opponentName)
            player.removePossibleOpponent(opponentName)
            save(player)
        }

        if (opponentName != "Bye") {
            def opponent = mapOfPlayers.get(opponentName)
            opponent.addRoundPairing(round, playerName)
            opponent.removePossibleOpponent(playerName)
            save(opponent)
        }
    }

    /**
     * saves the player to the map object by removing the old one, and adding the new one
     * @param playerName the player's playerName
     * @param player the PlayerInfo object for that player
     */
    private static void save(PlayerInfo player) {
        def name = player.name
        mapOfPlayers.remove(name)
        mapOfPlayers.put(name, player)
    }
}
