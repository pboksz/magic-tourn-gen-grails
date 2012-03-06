package magic.tournament.generator

import static org.junit.Assert.*
import org.junit.*

class IntegrationTests {

   @Before
   void setUp() {
      // Setup logic here
   }

   @After
   void tearDown() {
      // Post run logic
   }

   @Test
   void test4PlayerTournament() {
      def rankedPlayers = runTournament(4, 3, 3, "Swiss")
      def failures = assertValidPairings(rankedPlayers)
      printRankings(rankedPlayers)
      println "Number of Failures = " + failures
   }

   @Test
   void test4PlayerTournament100Times() {
      def failures = 0
      for(i in 0..100){
         def rankedPlayers = runTournament(4, 3, 3, "Swiss")
         failures += assertValidPairings(rankedPlayers)
      }
      println "Number of Failures = " + failures
   }

   private runTournament(int numPlayers, int maxRound, int bestOf, String format) {
      //create new tournament
      Tournament tournament = new Tournament(numPlayers, maxRound, bestOf, format)
      RoundPairings rp = new RoundPairings(tournament)

      //reset players
      PlayerPool.dropAllPlayers()

      //add players
      ArrayList<String> playerNames = new ArrayList<String>()
      for (i in 1..numPlayers) {
         playerNames.add("player" + i)
      }
      tournament.registerPlayers(playerNames)

      //for each round
      while(tournament.round <= maxRound){
         tournament.round == 1 ? rp.setInitialRoundPairings() : rp.setRoundPairings()
         def mapOfPlayers = (SortedMap<String, PlayerInfo>) PlayerPool.mapOfPlayers.clone()
         while(mapOfPlayers.size() != 0){
            //find the players
            def player = mapOfPlayers.get(mapOfPlayers.firstKey())
            def opponent = mapOfPlayers.get(player.opponent)

            //set outcomes for both
            PlayerPool.setPlayerOutcome(player.name, opponent.name, 2, 0)
            PlayerPool.setPlayerOutcome(opponent.name, player.name, 0, 2)

            //remove them from the list
            mapOfPlayers.remove(player.name)
            mapOfPlayers.remove(opponent.name)
         }
         //increment tournament
         tournament.nextRound()
      }

      return rp.showFinalRankings()
   }

   private printRankings(ArrayList<PlayerInfo> rankedPlayers) {
      rankedPlayers.each { player ->
         println player.rank + ") " + player.name
         println "\tTotal Points: " + player.points
         println "\tRound Wins: " + player.roundWins
         println "\tRound Byes: " + player.roundByes
         println "\tRound Losses: " + player.roundLosses
         println "\tIndividual Wins: " + player.individualWins
         println "\tIndividual Losses: " + player.individualLosses
         def opponentsList = player.roundPairings
         println "\tOpponents Per Round:"
         opponentsList.each { round ->
            println "\t\t" + round.key + ") " + round.value
         }
         println ""
      }
   }

   private int assertValidPairings(ArrayList<PlayerInfo> rankedPlayers) {
      def duplicatePairs = 0
      rankedPlayers.each { playerInfo ->
         def numByes = 0
         def opponents = playerInfo.roundPairings
         opponents.each { opponent ->
            def round = opponent.key
            def otherOpponents = (SortedMap<Integer,String>) opponents.clone()
            otherOpponents.remove(round)
            def name = opponent.value
            if (name == "Bye") {
               numByes++
            }
            otherOpponents.each { other ->
               if (name == other.value) {
                  duplicatePairs++
               }
//               assert name != other.value
            }
         }
//         assert numByes <= 1;
      }
      return duplicatePairs
   }
}
