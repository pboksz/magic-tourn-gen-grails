package magic.tournament.generator

/**
 * The main class that creates the tournament
 * Author: Phillip Boksz
 * Date: 2/24/12
 * Time: 10:37 AM
 */
class Tournament {
    private int round = 1
    private int numPlayers
    private int maxRound
    private int bestOf
    private String format

    public Tournament(int numPlayers, int maxRound, int bestOf, String format) {
        this.numPlayers = numPlayers
        this.maxRound = maxRound
        this.bestOf = bestOf
        this.format = format
    }

    def int getRound() {
        return round
    }

    def void nextRound() {
        this.round += 1
    }

    def int getNumPlayers() {
        return numPlayers
    }

    def int getMaxRound() {
        return maxRound
    }

    def int getBestOf() {
        return bestOf
    }

    def String getFormat() {
        return format
    }

    def void registerPlayers(ArrayList<String> playerNames) {
        playerNames.each { name ->
            PlayerPool.addNewPlayer(playerNames, name)
        }
    }
}
