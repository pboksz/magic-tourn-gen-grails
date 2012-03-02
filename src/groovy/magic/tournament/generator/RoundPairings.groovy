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
   private ArrayList<PlayerInfo> sortByInitialSeed() {
      def listOfPlayers = PlayerPool.listOfPlayers
      return listOfPlayers.sort() { s1, s2 ->
         s1.seed <=> s2.seed
      }
   }

   /**
    * This sorts the players by current rank for each other round
    * @return a sorted list of PlayerInfo objects
    */
   //TODO create MTG swiss logic as well
   def ArrayList<PlayerInfo> sortByCurrentRanking() {
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

   /*TODO May need to mess around with how bye is given, should check if a bye was already given
    *TODO also need to check if they have been paired already
    *TODO definitely need a better way to pair players
    */
   def SortedMap<String, PlayerInfo> getRoundPairings() {
      def roundPairingsList = new TreeMap<String, PlayerInfo>()
      def sorted = ((Tournament.round == 1) ? sortByInitialSeed() : sortByCurrentRanking())
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
         PlayerPool.setRoundPairing(Tournament.round, p1, p2)
         roundPairingsList.put(p1, PlayerPool.mapOfPlayers.get(p1))
         count = count + 2
      }
      return roundPairingsList
   }
}
