package magic.tournament.generator

/**
 * Holds a list of players and adds records of playerWins and such
 * Author: Phillip Boksz
 * Date: 2/24/12
 * Time: 10:33 AM
 */
class PlayerPool {
    private static SortedMap<String, PlayerInfo> listOfPlayers = new TreeMap<String, PlayerInfo>()
    private static Random seed = new Random()

    static SortedMap<String, PlayerInfo> getListOfPlayers() {
        return listOfPlayers
    }

    static void addNewPlayer(String name) {
        listOfPlayers.put(name, new PlayerInfo(name, seed.nextInt(100)))
    }

   /**
    * Sets the round outcome for both the player and opponent based on player playerWins and playerLosses
    * @param name of the player
    * @param opponent name of opponent
    * @param playerWins gained by player
    * @param playerLosses gained by player
    */
   static void setRoundOutcome(String name, String opponent, int playerWins, int playerLosses)
   {
      def player1 = listOfPlayers.get(name)
      def player2 = listOfPlayers.get(opponent)
      //if playerWins are bigger than playerLosses player1 won and player2 lost
      if(playerWins > playerLosses)
      {
         player1.wonRound()
         player1.addIndividualWins(playerWins)
         player1.addIndividualLosses(playerLosses)
         
         player2.lostRound()
         player2.addIndividualWins(playerLosses)
         player2.addIndividualLosses(playerWins)
      }
      //else player2 won, player1 lost
      else {
         player2.wonRound()
         player2.addIndividualWins(playerWins)
         player2.addIndividualLosses(playerLosses)

         player1.lostRound()
         player1.addIndividualWins(playerLosses)
         player1.addIndividualLosses(playerWins)
      }
      save(name, player1)
      save(opponent, player2)
   }

    /**
     * Sets a won round to the player with individual playerWins and playerLosses as well
     * @param name the player name
     * @param individualWins how many playerWins
     * @param individualLosses how many playerLosses
     */
    static void setWonRound(String name, int individualWins, int individualLosses) {
        def player = listOfPlayers.get(name)
        player.wonRound()
        player.addIndividualWins(individualWins)
        player.addIndividualLosses(individualLosses)
        save(name, player)
    }

    /**
     * Sets a lost round to the player with individual playerWins and playerLosses as well
     * @param name the player name
     * @param individualWins how many playerWins
     * @param individualLosses how many playerLosses
     */
    static void setLostRound(String name, int individualWins, int individualLosses) {
        def player = listOfPlayers.get(name)
        player.lostRound()
        player.addIndividualWins(individualWins)
        player.addIndividualLosses(individualLosses)
        save(name, player)
    }

    /**
     * Sets a bye round to specific player
     * @param name the player name
     */
    static void setByeRound(String name) {
        def player = listOfPlayers.get(name)
        player.byeRound()
        save(name, player)
    }

   /**
    * Sets both players info object to record the round and opponent for the round
    * @param round number
    * @param name of player1
    * @param opponent name of player2
    */
   static void setRoundPairing(int round, String name, String opponent)
   {
      if(name != "Bye"){
         def player1 = listOfPlayers.get(name)
         player1.addRoundPairing(round, opponent)
         save(name, player1)
      }

      if(opponent != "Bye"){
         def player2 = listOfPlayers.get(opponent)
         player2.addRoundPairing(round, name)
         save(opponent, player2)
      }
   }

   private static void save(String name, PlayerInfo player)
   {
      listOfPlayers.remove(name)
      listOfPlayers.put(name, player)
   }
}
