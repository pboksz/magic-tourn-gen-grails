package magic.tournament.generator

/**
 * The main class that creates the tournament
 * Author: Phillip Boksz
 * Date: 2/24/12
 * Time: 10:37 AM
 */
class Tournament {
    private static int round = 1

    def int getRound() {
        return round
    }

    def void nextRound() {
        Tournament.round += 1
    }

    static void getInitialSeeding(ArrayList<String> playerNames) {
        playerNames.each { name ->
            PlayerPool.createNewPlayer(name)
        }
        RoundPairings rp = new RoundPairings()
        def pairings = rp.getRoundPairings()
        PlayerPool.addRoundToListOfRoundPairs(round, pairings)
    }

    /**
     * Prints the standings to console
     */
    def printAllStandings() {
        RoundPairings rp = new RoundPairings()
        def sortedList = rp.sortByCurrentRanking()
        def place = 1;
        sortedList.each { player ->
            println place++ + ") " + player.name
            println "\tRound Wins = " + player.roundWins
            println "\tRound Byes = " + player.roundByes
            println "\tRound Losses = " + player.roundLosses
            println "\tIndividual Wins = " + player.individualWins
            println "\tIndividual Losses = " + player.individualLosses
        }
        println ""
    }

    //TODO all this is mimicking what would happen. Need website gui.
    //mimicking inputting players
    def addAllPlayers() {
        for (i in 1..7) {
            PlayerPool.createNewPlayer("Player" + i)
        }
    }

    //mimicks first round input
    def firstRound() {
        RoundPairings rp = new RoundPairings()
        def pairs = rp.getRoundPairings()
        pairs.each { pair ->
            def p1 = pair.player1
            def p2 = pair.player2
            println p1 + " " + p2
            if (p1 == "Bye") {
                PlayerPool.setByeRound(p2)
            }
            else if (p2 == "Bye") {
                PlayerPool.setByeRound(p1)
            }
            else {
                PlayerPool.setWonRound(p1, 2, 1)
                PlayerPool.setLostRound(p2, 1, 2)
            }
        }
        println ""
    }

    //mimicks second round input
    def secondRound() {
        round += 1
        RoundPairings rp = new RoundPairings()
        def pairs = rp.getRoundPairings()
        pairs.each { pair ->
            def p1 = pair.player1
            def p2 = pair.player2
            println p1 + " " + p2
            if (p1 == "Bye") {
                PlayerPool.setByeRound(p2)
            }
            else if (p2 == "Bye") {
                PlayerPool.setByeRound(p1)
            }
            else {
                PlayerPool.setWonRound(p1, 2, 0)
                PlayerPool.setLostRound(p2, 0, 2)
            }
        }
        println ""
    }

    //mimicks third round input
    def thirdRound() {
        round += 1
        RoundPairings rp = new RoundPairings()
        def pairs = rp.getRoundPairings()
        pairs.each { pair ->
            def p1 = pair.player1
            def p2 = pair.player2
            println p1 + " " + p2
            if (p1 == "Bye") {
                PlayerPool.setByeRound(p2)

            }
            else if (p2 == "Bye") {
                PlayerPool.setByeRound(p1)
            }
            else {
                PlayerPool.setWonRound(p2, 2, 0)
                PlayerPool.setLostRound(p1, 0, 2)
            }
        }
        println ""
    }

    //mimicks how this would go
    public static void main(String[] args) {
        Tournament ct = new Tournament()
        ct.addAllPlayers()
        ct.firstRound()
        ct.secondRound()
        ct.thirdRound()
        ct.printAllStandings()
    }
}
