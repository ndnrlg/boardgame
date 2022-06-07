# How to Run Game & Play
To start backend run `mvn install` and `mvn exec:exec` in the **java** directory under hw3-santorini-ndnrlg

To start frontend run `npm install`, `npm run compile`, and then `npm run start` in the **frontend** directory under hw3-santorini-ndnrlg.

To play the game: 
1. Select game power for each player on the player’s turn (If nothing is selected, default to “No God Power” for both)
2. Place Player A’s workers first, then B’s
3. To move, click on worker and then square
4. To build, click on square. If player has the Demeter or Hephaestus power, they must build on the first, mandatory build and can pass the second, optional build with the "Pass Build" button.
5. Invalid actions are rejected and you are able to continue your turn with a valid action
6. Game will end when a Player has won
7. Select "New Game" button to play a new game
