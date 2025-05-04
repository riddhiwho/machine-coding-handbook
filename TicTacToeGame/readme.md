Requirements

1. The Tic-Tac-Toe game should be played on a 3x3 grid.
2. Two players take turns marking their symbols (X or O) on the grid.
3. The first player to get three of their symbols in a row (horizontally, vertically, or diagonally) wins the game.
4. If all the cells on the grid are filled and no player has won, the game ends in a draw.
5. The game should have a user interface to display the grid and allow players to make their moves.
6. The game should handle player turns and validate moves to ensure they are legal.
7. The game should detect and announce the winner or a draw at the end of the game.



Entities:

- Board: 3*3 grid
- User : symbol
- GameManager - control the entire game : board, 2 players, current player

- make a move
- check game status

- basically, the game continues until either a player wins or it results is a draw


1. core entities
- bare min. fields
- constructor
- getter & setter

2. Think about the driving class & the main class (eg: GameManager & Main in this case)
driving class - will basically call the constructor of the core entities and initialise the default values 

3. Think about the flow of the program
- The functions you will need and where they will reside 
- Start with the bare min. and build from there

- a function to start the game
- will reside in the GameManager class (called by Main class)
- make a move - Board class 
- print board - Board class
- check game status - GameManager