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
    private static int maxDroppable = 1

    static SortedMap<String, PlayerInfo> getMapOfPlayers() {
        return mapOfPlayers
    }

   static ArrayList<PlayerInfo> getListOfPlayers() {
      ArrayList<PlayerInfo> listOfPlayers = new ArrayList<PlayerInfo>()
      mapOfPlayers.each { infoEntry ->
         listOfPlayers.add(infoEntry.value)
      }
      return listOfPlayers
   }

    static int getNumPlayers() {
       return mapOfPlayers.size()
    }

    static void registerPlayers(ArrayList<String> playerNames) {
       playerNames.each { name ->
          mapOfPlayers.put(name, new PlayerInfo(name, seed.nextInt(100), playerNames))
       }
       maxDroppable = numPlayers-3
    }

    static boolean dropPlayer(int round, String dropped, String getsBye) {
       if((round > 1) && (maxDroppable > 0)){
          mapOfPlayers.remove(dropped)
          maxDroppable--

          //for each player remove dropped player and add "Bye" to list of possible opponents
          def listOfPlayers = getListOfPlayers()
          def size = listOfPlayers.size()-1
          for(i in 0..size){
             def player = listOfPlayers.get(i)
             player.removePossibleOpponent(dropped)
             save(player)
          }
          return true
       }
       else {
          return false
       }
    }

    static void dropAllPlayers() {
        mapOfPlayers.clear()
    }

    /**
     * sets the outcome of the round for each player
     * @param playerName the player's playerName
     * @param opponentName the opponentName's playerName
     * @param wins the player's wins
     * @param losses the player's losses
     */
    static void setPlayerOutcome(String playerName, String opponentName, int round, int wins, int losses) {
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
                player.setRoundOutcome(round, wins, losses)
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
        mapOfPlayers.remove(player.name)
        mapOfPlayers.put(player.name, player)
    }
}
