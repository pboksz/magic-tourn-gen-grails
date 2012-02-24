package magic.tournament.generator

/**
 * Object that holds the paired up players
 * Author: Phillip Boksz
 * Date: 2/24/12
 * Time: 3:06 PM
 */
class PlayerPair {
   private String player1
   private String player2

   public PlayerPair(String p1, String p2) {
      this.player1 = p1
      this.player2 = p2
   }

   String getPlayer1() {
      return player1
   }

   String getPlayer2() {
      return player2
   }
}
