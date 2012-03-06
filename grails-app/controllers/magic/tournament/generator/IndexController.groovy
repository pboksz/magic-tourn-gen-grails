package magic.tournament.generator

class IndexController {

    static layout = "main"

    private static Tournament tournament
    private RoundPairings roundPairings = new RoundPairings(tournament)

    def getTournament() {
        return tournament
    }

    def getRoundPairings() {
        return roundPairings
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

    def firstround() {
        roundPairings.setInitialRoundPairings()
        redirect(action: "show")
    }

    def show() {
        flash.title = "Round " + tournament.getRound()
        flash.message = "Please enter the wins of each player and opponent."
        def listOfPairs = new ArrayList<PlayerInfo>()
        def mapOfPlayers = (SortedMap<String, PlayerInfo>) PlayerPool.mapOfPlayers.clone()
        while (mapOfPlayers.size() != 0) {
            def player = mapOfPlayers.get(mapOfPlayers.firstKey())
            listOfPairs.add(player)
            mapOfPlayers.remove(player.name)
            mapOfPlayers.remove(player.opponent)
        }
        render(view: "show", model: [listOfPairs: listOfPairs])
    }

    def nextround() {
        def players = params.player
        def opponents = params.opponent
        def playerWins = params.wins
        def playerLosses = params.losses
        def len = players.length - 1
        def hasErrors = false

        //if there are any errors in the input set that there are errors
        def maxWins = (int) Math.ceil(tournament.bestOf / 2)
        for (i in 0..len) {
            if (playerWins[i] != "-1") {
                if ((playerWins[i] ==~ /[0-9]/) && (playerLosses[i] ==~ /[0-9]/)) {
                    def wins = Integer.valueOf(playerWins[i])
                    def losses = Integer.valueOf(playerLosses[i])
                    if ((wins == "") || (losses == "")) {
                        hasErrors = true
                    }
                    if ((wins > maxWins) || (wins < 0)) {
                        hasErrors = true
                    }
                    if ((losses > maxWins) || (losses < 0)) {
                        hasErrors = true
                    }
                    if ((wins + losses > tournament.bestOf)) {
                        hasErrors = true
                    }
                }
                else {
                    hasErrors = true
                }
            }
        }

            //if all the wins and losses are entered
            if (!hasErrors) {
                for (i in 0..len) {
                    def playerName = players[i]
                    def opponentName = opponents[i]
                    def playerWon = Integer.valueOf(playerWins[i])
                    def playerLost = Integer.valueOf(playerLosses[i])
                    //outcome for first player
                    PlayerPool.setPlayerOutcome(playerName, opponentName, playerWon, playerLost)
                    //then the outcome for the second player (flipping the names and wins/losses as they are directly inverse)
                    PlayerPool.setPlayerOutcome(opponentName, playerName, playerLost, playerWon)
                }

                //increment round
                tournament.nextRound()

                //if this is the last round, show the results
                if (tournament.getRound() > tournament.maxRound) {
                    redirect(action: "results")
                }
                //else show the next round grid
                else {
                    //set pairings for the next round
                    roundPairings.setRoundPairings()
                    redirect(action: "current")
                }
            }
            //else return an error saying that all of the wins need to be recorded
            else {
                flash.error = "The values for wins for each player has to be a number, cannot be blank, cannot be less than 0 and should sum to " + tournament.bestOf + " or less."
                redirect(action: "show")
            }
        }

        def current() {
            flash.title = "Current Standings"
            def results = roundPairings.showCurrentRankings()
            render(view: "results", model: [results: results])
        }

        def results() {
            flash.title = "Final Results"
            def results = roundPairings.showCurrentRankings()
            render(view: "results", model: [results: results])
        }

        def dropplayer() {
            PlayerPool.dropPlayer(params.dropped, params.getsbye)
            redirect(action: "show")
        }

        def newtournament() {
            PlayerPool.dropAllPlayers()
            flash.error = "The tournament has been reset."
            redirect(action: "index")
        }
    }
