package magic.tournament.generator

/**
 * Object that holds the seed name pair
 * Author: Phillip Boksz
 * Date: 2/24/12
 * Time: 3:00 PM
 */
class PlayerSeed {
   private int seed
   private String name

   public PlayerSeed(int seed, String name) {
      this.seed = seed
      this.name = name
   }

   int getSeed() {
      return seed
   }

   String getName() {
      return name
   }
}
