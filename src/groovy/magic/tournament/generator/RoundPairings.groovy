package magic.tournament.generator

/**
 * This creates the pairs based on initial seeding or current record
 * Author: Phillip Boksz
 * Date: 2/24/12
 * Time: 2:28 PM
 */
class RoundPairings {

   Tournament tourn
   ArrayList queue

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
      //TODO for debugging only
      printRankings()
      return listOfPlayers
   }

   /**
    * method used to print to console for debugging each round
    * @param rankedPlayers a list of current rank of players
    *
    */
   private printRankings() {
      def rankedPlayers = sortByCurrentRanking()
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
      queue = new ArrayList<>()
      //if first round get other sorting
      if (tourn.round == 1) {
         sorted = (ArrayList<PlayerInfo>) sortByInitialSeed().clone()
      } else {
         sorted = (ArrayList<PlayerInfo>) sortByCurrentRanking().clone()
      }
      //try doing all the pairings
      trySettingRoundPairings(sorted)
      //after all that activate the pairings in the final queue
      queue.each { pair ->
         PlayerPool.setRoundPairing(pair.round, pair.player, pair.opponent)
      }
   }

   /**
    * takes a sorted list tries to pair all the players together
    * @param sorted takes a list of PlayerInfo objects sorted by rank (sometimes in reverse order)
    */
   def trySettingRoundPairings(ArrayList<PlayerInfo> sorted) {
      def firstIndex = 1
      def isFirstPlayer = true
      //try and pair off all the other people
      tryPairingAllPlayers(sorted, isFirstPlayer, firstIndex)
   }

   /**
    * tries to pair each player and if it ends up failing will redo this whole process and will try pairing the top player with second in line, and so forth
    * @param sorted takes a list of PlayerInfo objects sorted by rank
    * @param isFirstPlayer a boolean that is true says to start trying to pair the first player with the player at index 'firstIndex'
    * @param firstIndex the index of the player to start trying to pair with
    */
   def tryPairingAllPlayers(ArrayList<PlayerInfo> sorted, boolean isFirstPlayer, int firstIndex) {
      //try setting the bye first to last player without a bye
      if (sorted.size() % 2 == 1) {
         def lastIndex = sorted.size() - 1
         trySettingLowestBye(sorted, lastIndex)
      }
      //has this successfully paired the first player?
      def successFullyPairedAll = true
      while((sorted.size() != 0) && successFullyPairedAll){
         def pairIndex = 1
         //if this is the first player being sorted for the first time may change the index of the player to look at first
         if(isFirstPlayer)
         {
            pairIndex = firstIndex
            isFirstPlayer = false
         }
         //pair first player starting from opponent at pairIndex
         successFullyPairedAll = tryPairingNextPlayer(sorted, pairIndex)
      }
      //this would mean that the player cannot be paired with anyone in the set
      if (!successFullyPairedAll) {
         queue.clear()
         isFirstPlayer = true
         sorted = (ArrayList<PlayerInfo>) sortByCurrentRanking().clone()
         tryPairingAllPlayers(sorted, isFirstPlayer, ++firstIndex)
      }
   }

   /**
    * will set the bye to the lowest player available to get a bye
    * @param sorted list of players sorted by rank
    * @param last index of the last player
    */
   def trySettingLowestBye(ArrayList<PlayerInfo> sorted, int last) {
      def player = sorted.get(last)
      if (player.canUseBye()) {
         queue.add([round: tourn.round, player: player.name,opponent: "Bye"])
         sorted.remove(last)
      }
      else {
         trySettingLowestBye(sorted, --last)
      }
   }

   /**
    * this is a recursive method that pairs a player with one of the players left in the list
    * @param sorted a list of players by current rank available to be paired with
    * @param pairIndex the index of the player to try and pair first
    * @return a boolean on whether this pairing was successful of not
    */
   def boolean tryPairingNextPlayer(ArrayList<PlayerInfo> sorted, int pairIndex) {
      if (pairIndex < sorted.size()) {
         //get the first player to look at
         def player = sorted.get(0)
         //if there are potential players to match
         def opponent = sorted.get(pairIndex)
         //if player has not previously played this opponent
         if (player.canPlayThisPlayer(opponent.name)) {
            //add this pair to the queue, as later this may get scrapped
            queue.add([round: tourn.round, player: player.name,opponent: opponent.name])
            //remove the player and opponent paired from sorted and reverts back to trySettingRoundPairs() with two players removed
            sorted.remove(pairIndex)
            sorted.remove(0)
            return true
         }
         //else test the next player in sorted and see if thats a better match
         else {
            return tryPairingNextPlayer(sorted, ++pairIndex)
         }
      }
      else {
         return false
      }
   }
}
