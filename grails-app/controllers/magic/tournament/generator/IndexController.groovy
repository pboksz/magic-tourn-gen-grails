package magic.tournament.generator

class IndexController {

    static layout = "main"

    def index() {

    }
    
    def addplayers() {
        def howMany = Integer.valueOf(params.howManyPlayers)
        render(view: "addplayers" , model: [howManyPlayers: howMany])
    }

    def seeding() {
        //add players to the PlayerPool
        ArrayList<String> playerNames = new ArrayList<String>()
        params.each { player ->
            if (player.key.contains("player")) {
                def name = player.value ? player.value : player.key
                playerNames.add(name)
            }
        }
        //activate initial seeding which adds the round pairings to PlayerPool
        Tournament.getInitialSeeding(playerNames)

        //set round number and get the list of pairs for each round
        def roundNum = Tournament.round
        def roundPairs = PlayerPool.getListOfRoundPairs()
        render(view: "show", model: [roundNum: roundNum, roundPairs: roundPairs])
    }
}
