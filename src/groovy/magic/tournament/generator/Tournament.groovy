package magic.tournament.generator

/**
 * The main class that creates the tournament
 * Author: Phillip Boksz
 * Date: 2/24/12
 * Time: 10:37 AM
 */
class Tournament {
    private int prevRound = 0
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

    def getPrevRound() {
        return prevRound
    }

    def getRound() {
        return round
    }

    def getNumPlayers() {
        return numPlayers
    }

    def getMaxRound() {
        return maxRound
    }

    def getBestOf() {
        return bestOf
    }

    def getFormat() {
        return format
    }

    def nextRound() {
       this.round++
    }

    def incPrevRound() {
       this.prevRound++
    }

    def isNextRound() {
        return round > prevRound
    }
}
