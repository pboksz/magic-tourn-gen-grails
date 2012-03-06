package magic.tournament.generator

/**
 * This creates the pairs based on initial seeding or current record
 * Author: Phillip Boksz
 * Date: 2/24/12
 * Time: 2:28 PM
 */
class RoundPairings {

   Tournament tourn
   ArrayList<TempPair> queue

   public RoundPairings(Tournament tourn) {
      this.tourn = tourn
   }

   /**
    * This sorts the initial players by seed for the first round
    * @return a sorted list of objects that have players names
    */
   private ArrayList<PlayerInfo> sortByInitialSeed() {
      def listOfPlayers = PlayerPool.listOfPlayers
      return listOfPlayers.sort() { info1, info2 ->
         info1.seed <=> info2.seed
      }
   }

   /**
    * This sorts the players by current rank for each other round
    * @return a sorted list of PlayerInfo objects
    */
   private ArrayList<PlayerInfo> sortByCurrentRanking() {
      def listOfPlayers = PlayerPool.listOfPlayers
      return listOfPlayers.sort { info1, info2 ->
         if (info1.roundWins == info2.roundWins) {
            if (info1.roundByes == info2.roundByes) {
               if (info1.individualWins == info2.individualWins) {
                  info1.individualLosses <=> info2.individualLosses
               }
               else {
                  info2.individualWins <=> info1.individualWins
               }
            }
            else {
               info2.roundByes <=> info1.roundByes
            }
         }
         else {
            info2.roundWins <=> info1.roundWins
         }
      }
   }

   /**
    * sets each players final ranking when called on
    * @returns a list of players with final ranks in place
    */
   def ArrayList<PlayerInfo> showCurrentRankings() {
      def listOfPlayers = sortByCurrentRanking()
      def rank = 1
      listOfPlayers.each { player ->
         player.setRank(rank++)
      }
      printRankings(listOfPlayers)
      return listOfPlayers
   }

   /**
    * method used to print to console for debugging each round
    * @param rankedPlayers a list of current rank of players
    *
    */
   private printRankings(ArrayList<PlayerInfo> rankedPlayers) {
      rankedPlayers.each { player ->
         println player.rank + ") " + player.name
         println "\tTotal Points: " + player.points
         println "\tRound Wins: " + player.roundWins
         println "\tRound Byes: " + player.roundByes
         println "\tRound Losses: " + player.roundLosses
         println "\tIndividual Wins: " + player.individualWins
         println "\tIndividual Losses: " + player.individualLosses
         def opponentsList = player.roundPairings
         println "\tOpponents Each Round:"
         opponentsList.each { round ->
            println "\t\t" + round.key + ") " + round.value
         }
         println ""
      }
   }

   /**
    * sets the round pairings for the inital seed when the highest roll (0-100) get a Bye
    */
   def setInitialRoundPairings() {
      //get a list sorted by seed
      def sorted = sortByInitialSeed()
      def count = 0
      //pair up each set
      while (count <= (sorted.size() - 1)) {
         def p1 = sorted.get(count).name
         def p2
         //if there is no person to pair (odd numbers) set a bye
         if ((count + 1) >= sorted.size()) {
            p2 = "Bye"
         }
         else {
            p2 = sorted.get(count + 1).name
         }
         //set the round pairings in the PlayerPool
         PlayerPool.setRoundPairing(tourn.getRound(), p1, p2)
         count = count + 2
      }
   }

   /**
    * method the recursively tries to set the pairings
    * these actions are stored in a queue and activate when you get back here
    */
   def setRoundPairings() {
      queue = new ArrayList<TempPair>()
      def sorted = (ArrayList<PlayerInfo>) sortByCurrentRanking().clone()
      trySettingRoundPairings(sorted)
      queue.each { pair ->
         pair.activate()
      }
   }

   /**
    * takes a sorted list and while it has players, pairs them
    * @param sorted takes a list of PlayerInfo objects sorted by rank (sometimes in reverse order)
    */
   def trySettingRoundPairings(ArrayList<PlayerInfo> sorted) {
      while (!sorted.isEmpty()) {
         def player = sorted.get(0)
         tryPairingNextPlayer(sorted, player, 1)
      }
   }

   /**
    * takes in a list, a player, and the next player to look at and tries to pair them
    * @param sorted takes a list of PlayerInfo objects sorted by rank (sometimes in reverse order)
    * @param player takes the player object you want to pair someone with
    * @param count the number of the next player to look at in the list
    */
   def tryPairingNextPlayer(ArrayList<PlayerInfo> sorted, PlayerInfo player, int count){
      //first opponent to potentially look at
      def opponent = sorted.get(count)
      //opponent exists try and pair
      if(opponent){
         def opponentName = opponent.name
         //if player has not previously played this opponent
         if (player.canPlayThisPlayer(opponentName)) {
            //add this pair to the queue, as later this may get scrapped
            queue.add(new TempPair(tourn.round, player.name, opponentName))
            //remove the player and opponent paired from sorted and reverts back to trySettingRoundPairs() with two players removed
            sorted.remove(count)
            sorted.remove(0)
         }
         //else test the next player in sorted and see if thats a better match
         else {
            tryPairingNextPlayer(sorted, player, ++count)
         }
      }
      //failed all opponents, must try again, this time in reverse order
      else {
         def regSort = (ArrayList<PlayerInfo>) sortByCurrentRanking().clone()
         //sort players by rank in reverse, and see if this helps
         def reverseSort = (ArrayList<PlayerInfo>) regSort.reverse()
         //clear the queue of all items as this is going to run trySettingRoundPairings all over again
         queue.clear()
         trySettingRoundPairings(reverseSort)
      }
   }

   /**
    * Private inner class to hold the temporary round pairings that may or may not be activated
    */
   private class TempPair {
      
      private int round
      private String player
      private String opponent

      public TempPair(int round, String player, String opponent){
         this.round = round
         this.player = player
         this.opponent = opponent
      }

      def activate() {
         PlayerPool.setRoundPairing(round, player, opponent)
      }
   }
}
