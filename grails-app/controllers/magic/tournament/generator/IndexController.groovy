package magic.tournament.generator

class IndexController {

   static layout = "main"

   def index() {

   }

   def addsettings() {
      def howMany = Integer.valueOf(params.howManyPlayers)
      Tournament.setMaxRound(Integer.valueOf(params.howManyRounds))
      Tournament.setFormat(params.whichFormat)
      render(view: "addplayers", model: [howManyPlayers: howMany])
   }

   def show() {
      def roundNum = Tournament.round
      def roundPairs = Tournament.getRoundPairings()
      render(view: "show", model: [roundNum: roundNum, roundPairs: roundPairs])
   }

   def register() {
      //add players to the PlayerPool
      ArrayList<String> playerNames = new ArrayList<String>()
      params.each { player ->
         if (player.key.contains("player")) {
            def name = player.value ? player.value : player.key
            playerNames.add(name)
         }
      }
      //add the players to the tournament
      Tournament.registerPlayers(playerNames)

      //pass the round number and pairings list
      render(view: "register", model: [players: PlayerPool.listOfPlayers])
   }

   def firstround() {
      redirect(action: "show")
   }

   def nextround() {
      def players = params.player
      def opponents = params.opponent
      def playerWins = params.wins
      def playerLosses = params.losses
      def len = players.length - 1
      def missing = false

      //if any are empty redirect back
      for (i in 0..len) {
         if ((playerWins[i] == "") || (playerLosses[i] == "")) {
            //TODO add error message
            missing = true
         }
      }

      if (!missing) {
         //else add the outcome
         for (i in 0..len) {
            PlayerPool.setRoundOutcome(players[i], opponents[i], Integer.valueOf(playerWins[i]), Integer.valueOf(playerLosses[i]))
         }
         //increment round
         Tournament.nextRound()
         if (Tournament.round > Tournament.maxRound){
            redirect(action: "results")
         }
         else {
            redirect(action: "show")
         }
      }
      else
      {
         redirect(action: "show")
      }
   }

   def results() {
      RoundPairings rp = new RoundPairings()
      def results = rp.sortByCurrentRanking()
      def rank = 1
      results.each { result ->
         result.setRank(rank++)
      }
      render(view: "results", model: [results: results])
   }
}
