package magic.tournament.generator

class IndexController {

    static layout = "main"

    private static Tournament tournament

    def getTournament() {
        return tournament
    }

    def index() {
        flash.title = "Tournament Settings"
        flash.message = "Please select the tournament settings."
    }

    def addsettings() {
        //set new tournament
        def numPlayers = Integer.valueOf(params.howManyPlayers)
        def maxRounds = Integer.valueOf(params.howManyRounds)
        def bestOf = Integer.valueOf(params.bestOf)
        def format = params.whichFormat
        this.tournament = new Tournament(numPlayers, maxRounds, bestOf, format)

        flash.title = "Add Players"
        flash.message = "Please enter each player's name. If left blank the player's name will default to the format [ player# ]."
        render(view: "addplayers", model: [howManyPlayers: numPlayers])
    }

    def register() {
        flash.title = "Registered Players"
        flash.message = "Players will be paired by the order in which they are seeded."

        //if the players already exist just redirect to list of players and seedings
        if (PlayerPool.listOfPlayers) {
            def listOfPlayers = PlayerPool.listOfPlayers.sort { p1, p2 ->
                p1.seed <=> p2.seed
            }
            render(view: "register", model: [players: listOfPlayers])
        }
        //add new players to the PlayerPool
        else {
            ArrayList<String> playerNames = new ArrayList<String>()
            params.each { player ->
                if (player.key.contains("player")) {
                    def name = player.value ? player.value : player.key
                    playerNames.add(name)
                }
            }
            tournament.registerPlayers(playerNames)

            //pass the round number and pairings list
            def listOfPlayers = PlayerPool.listOfPlayers.sort { p1, p2 ->
                p1.seed <=> p2.seed
            }
            render(view: "register", model: [players: listOfPlayers])
        }
    }

    def show() {
        RoundPairings rp = new RoundPairings(tournament)
        flash.title = "Round " + tournament.getRound()
        flash.message = "Please enter the wins and losses of the player on the left."
        def roundPairs = rp.getRoundPairings()
        render(view: "show", model: [roundPairs: roundPairs])
    }

    def dropplayer() {
        PlayerPool.dropPlayer(params.dropped, params.getsbye)
        redirect(action: "show")
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
                flash.message = "Error, wins and losses cannot be blank."
                missing = true
            }
        }

        if (!missing) {
            //else add the outcome
            for (i in 0..len) {
                PlayerPool.setRoundOutcome(players[i], opponents[i], Integer.valueOf(playerWins[i]), Integer.valueOf(playerLosses[i]))
            }
            //increment round
            tournament.nextRound()
            if (tournament.getRound() > tournament.maxRound) {
                redirect(action: "results")
            }
            else {
                redirect(action: "show")
            }
        }
        else {
            flash.error = "All of the wins need to be recorded."
            redirect(action: "show")
        }
    }

    def results() {
        flash.title = "Final Results"
        RoundPairings rp = new RoundPairings()
        def results = rp.sortByCurrentRanking()
        def rank = 1
        results.each { result ->
            result.setRank(rank++)
        }
        render(view: "results", model: [results: results])
    }

    def newtournament() {
        PlayerPool.dropAllPlayers()
        flash.error = "The tournament has been reset."
        redirect(action: "index")
    }
}
