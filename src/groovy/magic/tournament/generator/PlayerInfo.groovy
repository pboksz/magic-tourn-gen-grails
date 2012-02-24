package magic.tournament.generator
/**
 * Holds all the info for each player
 * Author: Phillip Boksz
 * Date: 2/24/12
 * Time: 10:23 AM
 */
class PlayerInfo {
   private String name
   private int roundWins = 0
   private int roundLosses = 0
   private int roundByes = 0
   private int individualWins = 0
   private int individualLosses = 0

   def PlayerInfo(String name) {
      this.name = name
   }

   String getName() {
      return name
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
}
