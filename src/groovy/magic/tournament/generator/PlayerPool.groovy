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

    static void addNewPlayer(String name) {
        mapOfPlayers.put(name, new PlayerInfo(name, seed.nextInt(100)))
    }

    static void dropPlayer(int round, String dropped, String getsBye) {
        mapOfPlayers.remove(dropped)
        def player = mapOfPlayers.get(getsBye)
        player.byeRound()
        player.removeRoundPairing(round)
        save(getsBye, player)
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
     * Sets the round outcome for both the player and opponentName based on player playerWins and playerLosses
     * @param playerName of the player
     * @param opponentName playerName of opponentName
     * @param playerWins gained by player
     * @param playerLosses gained by player
     */
    static void setRoundOutcome(String playerName, String opponentName, int playerWins, int playerLosses) {
        def player = mapOfPlayers.get(playerName)
        def opponent = mapOfPlayers.get(opponentName)
        //if both players are in this round
        if (player && opponent) {
            //if player wins set records
            if (playerWins > playerLosses) {
                player.wonRound()
                player.addPoints(3)
                player.addIndividualWins(playerWins)
                player.addIndividualLosses(playerLosses)

                opponent.lostRound()
                opponent.addIndividualWins(playerLosses)
                opponent.addIndividualLosses(playerWins)
            }
            //else if opponent wins set these records
            else {
                opponent.wonRound()
                opponent.addPoints(3)
                opponent.addIndividualWins(playerWins)
                opponent.addIndividualLosses(playerLosses)

                player.lostRound()
                player.addIndividualWins(playerLosses)
                player.addIndividualLosses(playerWins)
            }
        }
        //else if one of these players is null set a bye
        else {
            if (player) {
                player.byeRound()
            }
            else if (opponent) {
                opponent.byeRound()
            }
        }
        save(playerName, player)
        save(opponentName, opponent)
    }

    /**
     * Sets both players info object to record the round and opponentName for the round
     * @param round number
     * @param name of player1
     * @param opponent playerName of player2
     */
    static void setRoundPairing(int round, String name, String opponent) {
        if (name != "Bye") {
            def player1 = mapOfPlayers.get(name)
            player1.addRoundPairing(round, opponent)
            save(name, player1)
        }

        if (opponent != "Bye") {
            def player2 = mapOfPlayers.get(opponent)
            player2.addRoundPairing(round, name)
            save(opponent, player2)
        }
    }

    private static void save(String name, PlayerInfo player) {
        mapOfPlayers.remove(name)
        mapOfPlayers.put(name, player)
    }
}
