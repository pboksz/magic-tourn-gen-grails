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
   void test4PlayerTournament2Wins1Losses() {
      def rankedPlayers = runTournament(4, 3, 3, 2, 1)
      def failures = assertValidPairings(rankedPlayers)
      assert failures == 0
   }

   @Test
   void test4PlayerTournamentMultipleTimes() {
      def failures = 0
      for(i in 0..30){
         def rankedPlayers = runTournament(4, 3, 3, 2, 1)
         failures += assertValidPairings(rankedPlayers)
      }
      assert failures == 0
   }

   @Test
   void test5PlayerTournament2Wins1Losses() {
      def rankedPlayers = runTournament(5, 3, 3, 2, 1)
      def failures = assertValidPairings(rankedPlayers)
      assert failures == 0
   }

   @Test
   void test5PlayerTournamentMultipleTimes() {
      def failures = 0
      for(i in 0..30){
         def rankedPlayers = runTournament(5, 3, 3, 2, 1)
         failures += assertValidPairings(rankedPlayers)
      }
      assert failures == 0
   }

   @Test
   void test6PlayerTournament2Wins1Losses() {
      def rankedPlayers = runTournament(6, 3, 3, 2, 1)
      def failures = assertValidPairings(rankedPlayers)
      assert failures == 0
   }

   @Test
   void test6PlayerTournamentMultipleTimes() {
      def failures = 0
      for(i in 0..30){
         def rankedPlayers = runTournament(6, 3, 3, 2, 1)
         failures += assertValidPairings(rankedPlayers)
      }
      assert failures == 0
   }

   @Test
   void test7PlayerTournament2Wins1Losses() {
      def rankedPlayers = runTournament(7, 3, 3, 2, 1)
      def failures = assertValidPairings(rankedPlayers)
      assert failures == 0
   }

   @Test
   void test7PlayerTournamentMultipleTimes() {
      def failures = 0
      for(i in 0..30){
         def rankedPlayers = runTournament(7, 3, 3, 2, 1)
         failures += assertValidPairings(rankedPlayers)
      }
      assert failures == 0
   }

   @Test
   void test8PlayerTournament2Wins1Losses() {
      def rankedPlayers = runTournament(8, 3, 3, 2, 1)
      def failures = assertValidPairings(rankedPlayers)
      assert failures == 0
   }

   @Test
   void test8PlayerTournamentMultipleTimes() {
      def failures = 0
      for(i in 0..30){
         def rankedPlayers = runTournament(8, 3, 3, 2, 1)
         failures += assertValidPairings(rankedPlayers)
      }
      assert failures == 0
   }

//   @Test
//   void testDroppingSinglePlayerBeforeRound2() {
//      for(i in 0..0){
//         runTournament(4, 3, 3, 3, 2, true, 2)
//      }
//   }
//
//   @Test
//   void testDroppingSinglePlayerBeforeRound3() {
//      for(i in 0..30){
//         runTournament(4, 3, 3, 3, 2, true, 3)
//      }
//   }

   private runTournament(int numPlayers, int maxRound, int bestOf, int maxWins, int maxLosses, boolean dropTest=false, int dropRound=2, String format="Swiss") {
      //create new tournament
      Tournament tournament = new Tournament(numPlayers, maxRound, bestOf, format)
      RoundPairings rp = new RoundPairings(tournament)
      Random random = new Random()

      //reset players
      PlayerPool.dropAllPlayers()

      //add players
      ArrayList<String> playerNames = new ArrayList<String>()
      for (i in 1..numPlayers) {
         playerNames.add("player" + i)
      }
      PlayerPool.registerPlayers(playerNames)

      //for each round
      while(tournament.round <= maxRound){
         //potentially test dropping of player
         if((tournament.round == dropRound) && dropTest){
            tryDroppingPlayer(tournament.round, "player" + (random.nextInt(4)+1))
         }

         //set pairings
         rp.setRoundPairings()

         //get the map of players
         def mapOfPlayers = (SortedMap<String, PlayerInfo>) PlayerPool.mapOfPlayers.clone()

         //for each player set the outcome
         while(mapOfPlayers.size() != 0){
            //randomly generate losses based on maximum possible losses (so 0 or 1 for a best of 3 setup)
            def losses = random.nextInt(maxLosses+1)
            //find the player and set outcome
            def player = mapOfPlayers.get(mapOfPlayers.firstKey())
            PlayerPool.setPlayerOutcome(player.name, player.opponent, tournament.round, maxWins, losses)

            //if player does not have a bye set the opponent's outcome as well
            if(player.opponent != "Bye"){
               def opponent = mapOfPlayers.get(player.opponent)
               PlayerPool.setPlayerOutcome(opponent.name, opponent.opponent, tournament.round, losses, maxWins)
               mapOfPlayers.remove(opponent.name)
            }

            //remove player from the list
            mapOfPlayers.remove(player.name)
         }
         //increment tournament
         tournament.nextRound()
      }
      return rp.showCurrentRankings()
   }

   def tryDroppingPlayer(int round, String playerName) {
      def player = PlayerPool.mapOfPlayers.get(playerName)
      PlayerPool.dropPlayer(round, player.name, player.opponent)
      def modifiedList = PlayerPool.mapOfPlayers
      assert modifiedList.get(player.name) == null
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
            }
         }
         assert numByes <= 1;
      }
      return duplicatePairs
   }
}
