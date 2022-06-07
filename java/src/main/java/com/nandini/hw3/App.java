package com.nandini.hw3;
import java.io.IOException;
import java.util.Map;
import fi.iki.elonen.NanoHTTPD;

public class App extends NanoHTTPD
{
    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private Game game;
    private String gcA;
    private String gcB;

    public App() throws IOException {
        super(8080);

        this.game = new Game();
        this.gcA = "N/A";
        this.gcB = "N/A";
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning!\n");
    }
    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        if (uri.equals("/newgame")) {
            this.game = new Game();
            this.gcA = "N/A";
            this.gcB = "N/A";
        }
        else if(uri.equals("/none")) {
            if (game.getTurn() == "A") {
                gcA = "N/A";
            }
            else {
                gcB = "N/A";
            }
            game.updateTurn();
        }
        else if(uri.equals("/demeter")) {
            game.setPlayer(new Player(game.getTurn(),
                    new DemeterWorker(1), new DemeterWorker(2)));
            if (game.getTurn() == "A") {
                gcA = "Demeter";
            }
            else {
                gcB = "Demeter";
            }
            game.updateTurn();
        }
        else if(uri.equals("/minotaur")) {
            game.setPlayer(new Player(game.getTurn(),
                    new MinotaurWorker(1), new MinotaurWorker(2)));
            if (game.getTurn() == "A") {
                gcA = "Minotaur";
            }
            else {
                gcB = "Minotaur";
            }
            game.updateTurn();
        }
        else if(uri.equals("/pan")) {
            game.setPlayer(new Player(game.getTurn(),
                    new PanWorker(1), new PanWorker(2)));
            if (game.getTurn() == "A") {
                gcA = "Pan";
            }
            else {
                gcB = "Pan";
            }
            game.updateTurn();
        }
        else if(uri.equals("/apollo")) {
            game.setPlayer(new Player(game.getTurn(),
                    new ApolloWorker(1), new ApolloWorker(2)));
            if (game.getTurn() == "A") {
                gcA = "Apollo";
            }
            else {
                gcB = "Apollo";
            }
            game.updateTurn();
        }
        else if (uri.equals("/hephaestus")) {
            game.setPlayer(new Player(game.getTurn(),
                    new HephaestusWorker(1), new HephaestusWorker(2)));
            if (game.getTurn() == "A") {
                gcA = "Hephaestus";
            }
            else {
                gcB = "Hephaestus";
            }
            game.updateTurn();
        }
        else if (uri.equals("/play")) {
            game.play(Integer.parseInt(params.get("y"))*5 + Integer.parseInt(params.get("x")));
        }
        else if(uri.equals("/pass")) {
            if (game.getPlayer().getTotalBuilds() == 2) {
                game.getPlayer().resetBuilds();
                game.getPlayer().setMoved(false);
                game.updateTurn();
                System.out.println(gcA);
            }
        }
        GameState gameplay = GameState.forGame(this.game, gcA, gcB);
        return newFixedLengthResponse(gameplay.toString());
    }
}
