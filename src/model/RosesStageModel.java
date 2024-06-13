package model;

import boardifier.model.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Stack;

/**
 * RosesStageModel defines the model for the single stage in "King of roses". Indeed,
 * there are no levels in this game: a party starts and when it's done, the game is also done.
 * <p>
 * RosesStageModel must define all that is needed to manage a party : state variables and game elements.
 * In the present case, there are only 2 state variables that represent the number of pawns to play by each player.
 * It is used to detect the end of the party.
 * For game elements, it depends on what is chosen as a final UI design. For that demo, there are 12 elements used
 * to represent the state : the main board, 2 pots, 8 pawns, and a text for current player.
 * <p>
 * WARNING ! RosesStageModel DOES NOT create itself the game elements because it would prevent the possibility to mock
 * game element classes for unit testing purposes. This is why RosesStageModel just defines the game elements and the methods
 * to set this elements.
 * The instanciation of the elements is done by the RosesStageFactory, which uses the provided setters.
 * <p>
 * RosesStageModel must also contain methods to check/modify the game state when given events occur. This is the role of
 * setupCallbacks() method that defines a callback function that must be called when a pawn is put in a container.
 * This is done by calling onPutInContainer() method, with the callback function as a parameter. After that call, boardifier
 * will be able to call the callback function automatically when a pawn is put in a container.
 * NB1: callback functions MUST BE defined with a lambda expression (i.e. an arrow function).
 * NB2:  there are other methods to defines callbacks for other events (see onXXX methods in GameStageModel)
 * In "King of Roses", everytime a pawn is put in the main board, we have to check if the party is ended and in this case, who is the winner.
 * This is the role of computePartyResult(), which is called by the callback function if there is no more pawn to play.
 */
public class RosesStageModel extends GameStageModel {

    // define stage state variables
    private int bluePawnsToPlay;
    private int redPawnsToPlay;

    // define stage game elements
    private RosesBoard board;

    private RosesPawnPot bluePot;
    private RosesPawnPot redPot;

    private RosesCardPot pickPot;
    private RosesCardPot discardPot;
    private RosesCardPot redHeroPot;
    private RosesCardPot blueHeroPot;
    private RosesCardPot moovRedPot;
    private RosesCardPot moovBluePot;

    public final static int STATE_SELECTCARD = 1; // the player must select a pawn
    public final static int STATE_SELECTDEST = 2; // the player must select a destination

    private RosesPawn[] bluePawns;
    private RosesPawn[] redPawns;
    private RosesPawn crownPawn;

    private RosesCard[] pickCards;
    private RosesCard[] discardCards = new RosesCard[26];
    private RosesCard[] player1HeroCards;
    private RosesCard[] player1MovementCards;
    private RosesCard[] player2MovementCards;
    private RosesCard[] player2HeroCards;

    private TextElement playerName;
    private TextElement pick;
    private TextElement discard;
    private TextElement bluePawnsCounter;
    private TextElement redPawnsCounter;

    private TextElement blueHeroCardsCounter;
    private TextElement redHeroCardsCounter;

    private TextElement cardPickCounter;

    private TextElement instructions1;
    private TextElement instructions2;
    private TextElement instructions3;

    private int nbMovements = 0;

    private String[] movementLists = new String[]{"N-W", "N-E", "S-E", "W", "S", "S-W", "N", "E"};
    private int[] numberList = new int[]{3, 1, 2};

    private boolean checked = true;


    // Uncomment next line if the example with a main container is used. see end of RosesStageFactory and RosesStageView
    //private ContainerElement mainContainer;

    public RosesStageModel(String name, Model model) {
        super(name, model);
        bluePawnsToPlay = 26;
        redPawnsToPlay = 26;
        state = STATE_SELECTCARD;
        setupCallbacks();
    }

    //Uncomment this 2 methods if example with a main container is used
    /*
    public ContainerElement getMainContainer() {
        return mainContainer;
    }

    public void setMainContainer(ContainerElement mainContainer) {
        this.mainContainer = mainContainer;
        addContainer(mainContainer);
    }
     */


    public RosesBoard getBoard() {
        return board;
    }

    public void setBoard(RosesBoard board) {
        this.board = board;
        addContainer(board);
    }

    public Rectangle getDiscardPotBounds() {
        return new Rectangle(discardPot.getX(), discardPot.getY(), 80, 110);
    }

    public Coord2D getBluePotLocation() {
        return bluePot.getLocation();
    }

    public Coord2D getRedPotLocation() {
        return redPot.getLocation();
    }

    public void setNbMovements(int nbMovements) {
        this.nbMovements = nbMovements;
    }

    public int getNbMovements() {
        return this.nbMovements;
    }

    public String[] getMovementsList() {
        return this.movementLists;
    }

    public TextElement getCardPickCounter() {
        return this.cardPickCounter;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean getChecked() {
        return this.checked;
    }

    public void setBlueHeroCardsCounter(TextElement blueHeroCardsCounter) {
        this.blueHeroCardsCounter = blueHeroCardsCounter;
        addElement(blueHeroCardsCounter);
    }

    public void setRedHeroCardsCounter(TextElement redHeroCardsCounter) {
        this.redHeroCardsCounter = redHeroCardsCounter;
        addElement(redHeroCardsCounter);
    }

    public TextElement getBlueHeroCardsCounter() {
        return this.blueHeroCardsCounter;
    }

    public TextElement getRedHeroCardsCounter() {
        return this.redHeroCardsCounter;
    }

    public void setCardPickCounter(TextElement cardPickCounter) {
        this.cardPickCounter = cardPickCounter;
        addElement(cardPickCounter);
    }

    public void setInstructions1(TextElement instructions1) {
        this.instructions1 = instructions1;
        addElement(instructions1);
    }

    public void setInstructions2(TextElement instructions2) {
        this.instructions2 = instructions2;
        addElement(instructions2);
    }

    public void setInstructions3(TextElement instructions3) {
        this.instructions3 = instructions3;
        addElement(instructions3);
    }

    public TextElement getInstruction1() {
        return this.instructions1;
    }

    public TextElement getInstruction2() {
        return this.instructions2;
    }

    public TextElement getInstruction3() {
        return this.instructions3;
    }

    public int[] getNumberList() {
        return this.numberList;
    }

    public RosesPawnPot getBluePot() {
        return bluePot;
    }

    public void setBluePot(RosesPawnPot bluePot) {
        this.bluePot = bluePot;
        addContainer(bluePot);
    }

    public RosesPawnPot getRedPot() {
        return redPot;
    }

    public void setRedPot(RosesPawnPot redPot) {
        this.redPot = redPot;
        addContainer(redPot);
    }

    public RosesCardPot getPickPot() {
        return pickPot;
    }

    public void setPickPot(RosesCardPot pickPot) {
        this.pickPot = pickPot;
        addContainer(pickPot);
    }

    public RosesCardPot getDiscardPot() {
        return discardPot;
    }

    public RosesCardPot getRedHeroPot() {
        return redHeroPot;
    }

    public void setRedHeroPot(RosesCardPot redHeroPot) {
        this.redHeroPot = redHeroPot;
        addContainer(redHeroPot);
    }

    public RosesCardPot getBlueHeroPot() {
        return blueHeroPot;
    }

    public void setBlueHeroPot(RosesCardPot blueHeroPot) {
        this.blueHeroPot = blueHeroPot;
        addContainer(blueHeroPot);
    }

    public RosesCardPot getMoovRedPot() {
        return moovRedPot;
    }

    public void setMoovRedPot(RosesCardPot moovRedPot) {
        this.moovRedPot = moovRedPot;
        addContainer(moovRedPot);
    }

    public RosesCardPot getMoovBluePot() {
        return moovBluePot;
    }

    public RosesCard[] getPickCards() {
        return this.pickCards;
    }

    public void setPickCards(RosesCard[] pickCards) {
        this.pickCards = pickCards;
        for (int i = 0; i < pickCards.length; i++) {
            addElement(pickCards[i]);
        }
    }

    public void setPickCards(int index, RosesCard pickCard) {
        this.pickCards[index] = pickCard;
        addElement(pickCards[index]);
    }

    public void setMoovBluePot(RosesCardPot moovBluePot) {
        this.moovBluePot = moovBluePot;
        addContainer(moovBluePot);
    }

    public void setDiscardPot(RosesCardPot discardPot) {
        this.discardPot = discardPot;
        addContainer(discardPot);
    }

    public RosesPawn[] getBluePawns() {
        return bluePawns;
    }

    public RosesPawn getCrownPawn() {
        return this.crownPawn;
    }

    public void setCrownPawn(RosesPawn crownPawn) {
        this.crownPawn = crownPawn;
        addElement(crownPawn);
    }

    public void setBluePawns(RosesPawn[] bluePawns) {
        this.bluePawns = bluePawns;
        for (int i = 0; i < bluePawns.length; i++) {
            addElement(bluePawns[i]);
        }
    }

    public RosesPawn[] getRedPawns() {
        return redPawns;
    }

    public void setRedPawns(RosesPawn[] redPawns) {
        this.redPawns = redPawns;
        for (int i = 0; i < redPawns.length; i++) {
            addElement(redPawns[i]);
        }
    }

    public RosesCard[] getPlayer1HeroCards() {
        return this.player1HeroCards;
    }


    public void setPlayer1HeroCards(RosesCard[] player1HeroCards) {
        this.player1HeroCards = player1HeroCards;
        for (int i = 0; i < player1HeroCards.length; i++) {
            addElement(player1HeroCards[i]);
        }
    }

    public RosesCard[] getPlayer2HeroCards() {
        return this.player2HeroCards;
    }

    public void setPlayer2HeroCards(RosesCard[] player2HeroCards) {
        this.player2HeroCards = player2HeroCards;
        for (int i = 0; i < player2HeroCards.length; i++) {
            addElement(player2HeroCards[i]);
        }
    }

    public void setPlayer1MovementCards(RosesCard[] player1MovementCards) {
        this.player1MovementCards = player1MovementCards;
        for (int i = 0; i < player1MovementCards.length; i++) {
            addElement(player1MovementCards[i]);
        }
    }

    public void setPlayer1MovementCards(int index, RosesCard player2MovementCard) {
        this.player2MovementCards[index] = player2MovementCard;
        addElement(player2MovementCards[index]);
    }

    public void setPlayer2MovementCards(RosesCard[] player2MovementCards) {
        this.player2MovementCards = player2MovementCards;
        for (int i = 0; i < player2MovementCards.length; i++) {
            addElement(player2MovementCards[i]);
        }
    }

    public void setPlayer2MovementCards(int index, RosesCard player2MovementCard) {
        this.player2MovementCards[index] = player2MovementCard;
        addElement(player2MovementCards[index]);
    }

    public RosesCard[] getPlayer1MovementCards() {
        return this.player1MovementCards;
    }

    public RosesCard[] getPlayer2MovementCards() {
        return this.player2MovementCards;
    }

    public TextElement getPlayerName() {
        return playerName;
    }

    public TextElement getRedPawnsCounter() {
        return this.redPawnsCounter;
    }

   public TextElement getPick() {
        return pick;
    }

    public TextElement getDiscard() {
        return discard;
    }

    public TextElement getBluePawnsCounter() {
        return this.bluePawnsCounter;
    }

    public void setPlayerName(TextElement playerName) {
        this.playerName = playerName;
        addElement(playerName);
    }

    public void setRedPawnsCounter(TextElement redPawnsCounter) {
        this.redPawnsCounter = redPawnsCounter;
        addElement(redPawnsCounter);
    }

    public void setBluePawnsCounter(TextElement bluePawnsCounter) {
        this.bluePawnsCounter = bluePawnsCounter;
        addElement(bluePawnsCounter);
    }

    public void setPick(TextElement pick) {
        this.pick = pick;
        addElement(pick);
    }

    public void setDiscard(TextElement discard) {
        this.discard = discard;
        addElement(discard);
    }

    public void setDiscardCards(RosesCard[] discardCards) {
        this.discardCards = discardCards;
        for (int i = 0; i < discardCards.length; i++)
            addElement(discardCards[i]);
    }

    public void setDiscardCards(int index, RosesCard discardCard) {
        this.discardCards[index] = discardCard;
        addElement(discardCards[index]);
    }

    public RosesCard[] getDiscardCards() {
        return this.discardCards;
    }

    public int getBluePawnsToPlay() {
        return this.bluePawnsToPlay;
    }

    public int getRedPawnsToPlay() {
        return this.redPawnsToPlay;
    }

//    public void piocherCartes(RosesCard[] joueurMovementCards, RosesCard[] pickPotCards, int nombre) {
//        for (int i = 0; i < joueurMovementCards.length && nombre > 0; i++) {
//            if (joueurMovementCards[i] == null) {
//                joueurMovementCards[i] = new RosesCard(pickPotCards[pickPotCards.length - 1]);
//                pickPotCards = Arrays.copyOf(pickPotCards, pickPotCards.length - 1);
//                nombre--;
//            }
//        }
//    }


    public void update() {
        bluePawnsCounter.setText("" + this.getBluePawnsToPlay());
        redPawnsCounter.setText("" + this.getRedPawnsToPlay());
        cardPickCounter.setText("" + this.pickCards.length);
        blueHeroCardsCounter.setText("" + this.player1HeroCards.length);
        redHeroCardsCounter.setText("" + this.player2HeroCards.length);
    }

    private void setupCallbacks() {
        onPutInContainer((element, gridDest, rowDest, colDest) -> {
            // just check when pawns are put in 3x3 board
            if (model.getGameStage() != null) {
                RosesStageModel tempModel = (RosesStageModel) model.getGameStage();
                if (!tempModel.getChecked()) {
                    computePartyResult();
                    return;
                }
            }
            if (gridDest != board) return;
            RosesPawn p = (RosesPawn) element;
            if (p.getColor() == 0) {
                bluePawnsToPlay--;
            } else if (p.getColor() == 1) {
                redPawnsToPlay--;
            }
            if ((bluePawnsToPlay == 0) && (redPawnsToPlay == 0)) {
                computePartyResult();
            }
        });
    }

    public void computePartyResult() {
        System.out.println("La partie est terminée");
        int idWinner = -1;
        int nbBlue = 0;
        int nbRed = 0;
        int blueScore = 0, redScore = 0;
        getBoard().removeElement(crownPawn);

        boolean[][] visited = new boolean[9][9];

        for (int n = 0; n < 9; n++) {
            for (int m = 0; m < 9; m++) {
                if (getBoard().isElementAt(n, m) && !visited[n][m]) {
                    RosesPawn p = (RosesPawn) (getBoard().getElement(n, m));
                    if (p.getColor() == RosesPawn.PAWN_BLUE) {
                        int size = countZoneSize(n, m, RosesPawn.PAWN_BLUE, visited);
                        blueScore += size * size;
                        nbBlue += size;
                    } else if (p.getColor() == RosesPawn.PAWN_RED) {
                        int size = countZoneSize(n, m, RosesPawn.PAWN_RED, visited);
                        redScore += size * size;
                        nbRed += size;

                    }
                }
            }
        }

        if (blueScore > redScore) {
            idWinner = 0;
        } else if (redScore > blueScore) {
            idWinner = 1;
        } else {
            if (nbBlue > nbRed) {
                idWinner = 0;
            } else if (nbRed > nbBlue) {
                idWinner = 1;
            } else {
                idWinner = -1;  // Partie nulle
            }
        }

        System.out.println(Color.BLUE + "[Player 1]" + Color.BLACK + " Blue pawns on the field : " + nbBlue);
        System.out.println(Color.RED + "[Player 2]" + Color.BLACK + " Red pawns on the field : " + nbRed);
        System.out.println(Color.BLUE + "[Player 1]" + Color.BLACK + " Blue score : " + blueScore);
        System.out.println(Color.RED + "[Player 2]" + Color.BLACK + " Red score : " + redScore);
        model.setIdWinner(idWinner);
        model.stopStage();
    }

    private int countZoneSize(int n, int m, int color, boolean[][] visited) {
        int size = 0;
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{n, m});
        visited[n][m] = true;

        while (!stack.isEmpty()) {
            int[] pos = stack.pop();
            size++;
            for (int[] dir : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
                int row = pos[0] + dir[0];
                int col = pos[1] + dir[1];
                if (row >= 0 && row < 9 && col >= 0 && col < 9 && !visited[row][col] && getBoard().getElement(row, col) != null) {
                    RosesPawn q = (RosesPawn) (getBoard().getElement(row, col));
                    if (q.getColor() == color) {
                        stack.push(new int[]{row, col});
                        visited[row][col] = true;
                    }
                }
            }
        }

        return size;
    }


    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new RosesStageFactory(this);
    }
}
