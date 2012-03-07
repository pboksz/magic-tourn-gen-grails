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
      //TODO For debugging only
//      printRankings(listOfPlayers)
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
    * method the recursively tries to set the pairings
    * these actions are stored in a queue and activate when you get back here
    */
   def setRoundPairings() {
      //list of sorted players
      def sorted
      //create a queue object
      queue = new ArrayList<TempPair>()
      //if first round get other sorting
      if(tourn.round == 1)
      {
         sorted = (ArrayList<PlayerInfo>) sortByInitialSeed().clone()
      } else {
         sorted = (ArrayList<PlayerInfo>) sortByCurrentRanking().clone()
      }
      //try doing all the pairings
      def firstIndex = 1
      trySettingRoundPairings(sorted, firstIndex)
      //after all that activate the pairings in the final queue
      queue.each { pair ->
         pair.activate()
      }
   }

   /**
    * takes a sorted list and while it has players, pairs them
    * @param sorted takes a list of PlayerInfo objects sorted by rank (sometimes in reverse order)
    */
   def trySettingRoundPairings(ArrayList<PlayerInfo> sorted, int firstIndex) {
      //try setting the bye first to last player without a bye
      if(sorted.size()%2 ==1)
      {
         def lastIndex = sorted.size()-1
         trySettingLowestBye(sorted, lastIndex)
      }

      def index = 1
      def isFirstPlayer = true
      //try and pair off all the other people
      while (!sorted.isEmpty()) {
         def player = sorted.get(0)
         tryPairingNextPlayer(sorted, player, index, isFirstPlayer, firstIndex)
         isFirstPlayer = false
      }
   }

   /**
    * will set the bye to the lowest player available to get a bye
    * @param sorted list of players sorted by rank
    * @param last index of the last player
    */
   def trySettingLowestBye(ArrayList<PlayerInfo> sorted, int last)
   {
      def player = sorted.get(last)
      if(player.canUseBye())
      {
         queue.add(new TempPair(tourn.round, player.name, "Bye"))
         sorted.remove(last)
      }
      else {
         trySettingLowestBye(sorted, --last)
      }
   }

   /**
    * takes in a list, a player, and the next player to look at and tries to pair them
    * @param sorted takes a list of PlayerInfo objects sorted by rank (sometimes in reverse order)
    * @param player takes the player object you want to pair someone with
    * @param index the number of the next player to look at in the list
    */
   def tryPairingNextPlayer(ArrayList<PlayerInfo> sorted, PlayerInfo player, int index, boolean isFirstPlayer, int firstIndex) {
      //first opponent to potentially look at
      if (isFirstPlayer){
         index = firstIndex
      }
      if (index < sorted.size()) {
         def opponent = sorted.get(index)
         def opponentName = opponent.name
         //if player has not previously played this opponent
         if (player.canPlayThisPlayer(opponentName)) {
            //add this pair to the queue, as later this may get scrapped
            queue.add(new TempPair(tourn.round, player.name, opponentName))
            //remove the player and opponent paired from sorted and reverts back to trySettingRoundPairs() with two players removed
            sorted.remove(index)
            sorted.remove(0)
         }
         //else test the next player in sorted and see if thats a better match
         else {
            tryPairingNextPlayer(sorted, player, ++index, false, firstIndex)
         }
      }
      //this would mean that the player cannot be paired with anyone in the set
      else {
         //clear the queue
         queue.clear()
         sorted = (ArrayList<PlayerInfo>) sortByCurrentRanking().clone()
         trySettingRoundPairings(sorted, ++firstIndex)
         System.exit(0)
      }
   }

   /**
    * Private inner class to hold the temporary round pairings that may or may not be activated
    */
   private class TempPair {

      private int round
      private String player
      private String opponent

      public TempPair(int round, String player, String opponent) {
         this.round = round
         this.player = player
         this.opponent = opponent
      }

      def activate() {
         PlayerPool.setRoundPairing(round, player, opponent)
      }
   }
}
