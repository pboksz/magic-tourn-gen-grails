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

    def boolean canPlayThisPlayer(String opponentName) {
       def canPlay = false
       possibleOpponents.each { opponent ->
          if(opponentName.equals(opponent)){
             canPlay = true
          }
       }
       return canPlay
    }
   
    def void removePossibleOpponent(String opponent) {
        def index = possibleOpponents.indexOf(opponent)
        possibleOpponents.remove(index)
    }

    def void addRoundPairing(int round, String opponent) {
        roundPairings.put(round, opponent)
        this.opponent = opponent
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
