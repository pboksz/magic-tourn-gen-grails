package magic.tournament.generator

/**
 * This creates the pairs based on initial seeding or current record
 * Author: Phillip Boksz
 * Date: 2/24/12
 * Time: 2:28 PM
 */
class RoundPairings {

   /**
    * This sorts the initial players by seed for the first round
    * @return a sorted list of objects that have players names
    */

   private ArrayList<PlayerSeed> sortByInitialSeed() {
      def sorted = new ArrayList<PlayerSeed>()
      def listOfPlayers = PlayerPool.listOfPlayers
      Random seed = new Random()
      listOfPlayers.each { infoEntry ->
         def playerName = infoEntry.value.name
         sorted.add(new PlayerSeed(seed.nextInt(100), playerName))
      }
      return sorted.sort { s1, s2 ->
         s1.seed <=> s2.seed
      }
   }

   /**
    * This sorts the players by current rank for each other round
    * @return a sorted list of objects that have players names
    */
   def ArrayList<String> sortByCurrentRanking() {
      def sortedList = new ArrayList<PlayerInfo>()
      def listOfPlayers = PlayerPool.listOfPlayers
      listOfPlayers.each { infoEntry ->
         sortedList.add(infoEntry.value)
      }
      sortedList.sort { p1, p2 ->
         if (p1.roundWins == p2.roundWins) {
            if (p1.roundByes == p2.roundByes) {
               if (p1.individualWins == p2.individualWins) {
                  p1.individualLosses <=> p2.individualLosses
               }
               else {
                  p2.individualWins <=> p1.individualWins
               }
            }
            else {
               p2.roundByes <=> p1.roundByes
            }
         }
         else {
            p2.roundWins <=> p1.roundWins
         }
      }
      return sortedList
   }

   //TODO May need to mess around with how bye is given, should check if a bye was already given
   private ArrayList<PlayerPair> getRoundPairs(ArrayList sorted) {
      def listOfPairings = new ArrayList<PlayerPair>()
      def count = 0
      while (count <= (sorted.size() - 1)) {
         def p1 = sorted.get(count).name
         def p2
         if ((count + 1) >= sorted.size()) {
            p2 = "Bye"
         }
         else {
            p2 = sorted.get(count + 1).name
         }
         listOfPairings.add(new PlayerPair(p1, p2))
         count = count + 2
      }
      return listOfPairings
   }

   def ArrayList<PlayerPair> getRoundPairings() {
      if (CreateTournament.round == 1) {
         return getRoundPairs(sortByInitialSeed())
      }
      else {
         return getRoundPairs(sortByCurrentRanking())
      }
   }
}
