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
    private int points = 0
    private int roundWins = 0
    private int roundLosses = 0
    private int roundByes = 0
    private int individualWins = 0
    private int individualLosses = 0

    private String opponent
    private ArrayList<String> possibleOpponents = new ArrayList<String>()
    private SortedMap<Integer, String> roundPairings = new TreeMap<Integer, String>()

    def PlayerInfo(String name, int seed, ArrayList<String> playerNames) {
        this.name = name
        this.seed = seed
        addPossibleOpponents(playerNames)
    }

    def String getName() {
        return name
    }

    def int getSeed() {
        return seed
    }

    def int getRank() {
        return rank
    }

    def int getPoints() {
        return points
    }

    def int getRoundWins() {
        return roundWins
    }

    def int getRoundLosses() {
        return roundLosses
    }

    def int getRoundByes() {
        return roundByes
    }

    def int getIndividualWins() {
        return individualWins
    }

    def int getIndividualLosses() {
        return individualLosses
    }

    def String getOpponent() {
        return opponent
    }

    def ArrayList<String> getPossibleOpponents() {
        return possibleOpponents
    }

    def SortedMap<Integer, String> getRoundPairings() {
        return roundPairings
    }

    def void wonRound() {
        this.roundWins += 1
        this.points += 3
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

    def void addPossibleOpponents(ArrayList<String> playerNames) {
       possibleOpponents.add("Bye")
       playerNames.each { player ->
          if(player != name)
          {
             possibleOpponents.add(player)
          }
       }
    }

   def void removePossibleOpponent(String opponent) {
      //if the removed opponent is on the list of possible opponents, remove it
      def index = possibleOpponents.indexOf(opponent)
      if(index != -1){
         possibleOpponents.remove(index)
      }
   }

   def void addPossibleOpponent(String opponent) {
      //if the player has not had a bye yet, and the opponent is not already on the list of possible opponents, add it
      if((!roundPairings.containsValue(opponent)) && (!possibleOpponents.contains(opponent))){
         possibleOpponents.add(opponent)
      }
   }

    def boolean canPlayThisPlayer(String opponentName) {
       def canPlay = false
       possibleOpponents.each { opponent ->
          if(opponentName.equals(opponent)){
             canPlay = true
          }
       }
       return canPlay
    }

    def void addRoundPairing(int round, String opponent) {
        roundPairings.put(round, opponent)
        this.opponent = opponent
    }

    def void setRoundOutcome(int round, int wins, int losses){
       def info = roundPairings.get(round)
       def outcome = wins > losses ? "Win" : "Loss"
       info = info + " / " + outcome + " / " + wins + "-" + losses
       roundPairings.remove(round)
       roundPairings.put(round, info)
    }

    def void removeRoundPairing(int round) {
        roundPairings.remove(round)
    }

    def void setRank(int rank) {
        this.rank = rank
    }

    def boolean canUseBye() {
       return possibleOpponents.contains("Bye")
    }
}
