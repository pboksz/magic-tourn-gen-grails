package magic.tournament.generator
/**
 * Holds all the info for each player
 * Author: Phillip Boksz
 * Date: 2/24/12
 * Time: 10:23 AM
 */
class PlayerInfo {
   private String name
   private int seed
   private int rank = 0
   private int roundWins = 0
   private int roundLosses = 0
   private int roundByes = 0
   private int individualWins = 0
   private int individualLosses = 0
   
   private SortedMap<Integer, String> roundPairings = new TreeMap<Integer, String>()
   private String opponent

   def PlayerInfo(String name, int seed) {
      this.name = name
      this.seed = seed
   }

   String getName() {
      return name
   }

   int getSeed() {
      return seed
   }

   int getRank() {
      return rank
   }

   int getRoundWins() {
      return roundWins
   }

   int getRoundLosses() {
      return roundLosses
   }

   int getRoundByes() {
      return roundByes
   }

   int getIndividualWins() {
      return individualWins
   }

   int getIndividualLosses() {
      return individualLosses
   }
   
   SortedMap<Integer, String> getRoundPairings() {
      return roundPairings
   }
   
   String getOpponent()
   {
      return opponent
   }

   def void wonRound() {
      this.roundWins += 1
   }

   def void lostRound() {
      this.roundLosses += 1
   }

   def void byeRound() {
      this.roundByes += 1
   }

   def void addIndividualWins(int wins) {
      this.individualWins += wins
   }

   def void addIndividualLosses(int losses) {
      this.individualLosses += losses
   }

   def void addRoundPairing(int round, String opponent)
   {
      roundPairings.put(round, opponent)
      this.opponent = opponent
   }

   def void setRank(int rank){
      this.rank = rank
   }
}
