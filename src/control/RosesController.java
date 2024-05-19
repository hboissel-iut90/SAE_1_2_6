package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.view.View;
import model.RosesCard;
import model.RosesPawn;
import model.RosesStageModel;
import view.RosesStageView;
import boardifier.model.ContainerElement;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.List;

import static model.RosesPawn.PAWN_BLUE;
import static model.RosesPawn.PAWN_RED;

public class RosesController extends Controller {

    BufferedReader consoleIn;
    boolean firstPlayer;
    boolean isTheFirstTime = true;
    int lastRow = 0;
    int lastCol = 0;


    int nbMovements = 0;

    public RosesController(Model model, View view) {
        super(model, view);
        firstPlayer = true;
    }

    /**
     * Defines what to do within the single stage of the single party
     * It is pretty straight forward to write :
     */
    public void stageLoop() {
        consoleIn = new BufferedReader(new InputStreamReader(System.in));
        update();
        while (!model.isEndStage()) {
            playTurn();
            endOfTurn();
            update();
        }
        endGame();
    }

    private void playTurn() {
        // get the new player
        Player p = model.getCurrentPlayer();
        if (p.getType() == Player.COMPUTER) {
            System.out.println("COMPUTER PLAYS");
            RosesDecider decider = new RosesDecider(model, this);
            ActionPlayer play = new ActionPlayer(model, this, decider, null);
            play.start();
        } else {
            boolean ok = false;
            while (!ok) {
                System.out.print(p.getName() + " > ");
                try {
                    String line = consoleIn.readLine();
                    if (line.length() == 2 || String.valueOf(line.charAt(0)).equals("P")) {
                        ok = analyseAndPlay(line);
                    }
                    if (!ok) {
                        System.out.println("incorrect instruction. retry !");
                    }
                } catch (IOException e) {
                }
            }
        }
    }

    public void endOfTurn() {
        model.setNextPlayer();
        // get the new player to display its name
        Player p = model.getCurrentPlayer();
        RosesStageModel stageModel = (RosesStageModel) model.getGameStage();
        stageModel.getPlayerName().setText(p.getName());
        stageModel.update();
    }

    private boolean analyseAndPlay(String line) {


        int row, col;
        if (isTheFirstTime) {
            row = 4;
            col = 4;
        } else {
            row = lastRow;
            col = lastCol;
        }


        RosesStageModel gameStage = (RosesStageModel) model.getGameStage();
        RosesStageView viewStage = (RosesStageView) view.getGameStageView();
        int pawnIndex = 0;
        System.out.println("derniere colonne : " + col + ", derniere ligne : " + row);
        String direction = "";
        String cardType = "";
        int number = 0;
        cardType = String.valueOf(line.charAt(0));
        checkIfPlayerPlay(cardType);
        if (cardType.equals("P")){
            if (model.getIdPlayer() == 0){
                for (int i = 0; i < gameStage.getPlayer1MovementCards().length; i++) {
                    if (gameStage.getPlayer1MovementCards()[i] == null && gameStage.getPickCards().length > 0) {
                        gameStage.getPlayer1MovementCards()[i] = gameStage.getPickCards()[gameStage.getPickCards().length - 1];
                        gameStage.getPlayer1MovementCards()[i].flip();
                        gameStage.getMoovBluePot().addElement(gameStage.getPlayer1MovementCards()[i], i, 0);
                        RosesCard[] tempPickCard = gameStage.getPickCards();
                        RosesCard[] copyOfPickPotCards = new RosesCard[tempPickCard.length - 1];
                        System.arraycopy(tempPickCard, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
                        tempPickCard = copyOfPickPotCards;
                        gameStage.setPickCards(tempPickCard);
                        if (gameStage.getPickCards().length > 0) {
                            return true;
                        }
                    }
                }
            } else {
                for (int i = 0; i < gameStage.getPlayer2MovementCards().length; i++) {
                    if (gameStage.getPlayer2MovementCards()[i] == null && gameStage.getPickCards().length > 0) {
                        gameStage.getPlayer2MovementCards()[i] = gameStage.getPickCards()[gameStage.getPickCards().length - 1];
                        gameStage.getPlayer2MovementCards()[i].flip();
                        gameStage.getMoovRedPot().addElement(gameStage.getPlayer2MovementCards()[i], i, 0);
                        RosesCard[] tempPickCard = gameStage.getPickCards();
                        RosesCard[] copyOfPickPotCards = new RosesCard[tempPickCard.length - 1];
                        System.arraycopy(tempPickCard, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
                        tempPickCard = copyOfPickPotCards;
                        gameStage.setPickCards(tempPickCard);
                        if (gameStage.getPickCards().length > 0) {
                            return true;
                        }
                    }
                }
            }
            if (gameStage.getPickCards().length == 0) {
                System.out.println("La pioche est vide.");

                // Compter le nombre de cartes non nulles dans la défausse
                int newLength = 0;
                for (int n = 0; n < gameStage.getDiscardCards().length; n++) {
                    if (gameStage.getDiscardCards()[n] != null) {
                        newLength++;
                    }
                }

                // Transférer les cartes de la défausse à la pioche
                RosesCard[] tempPickCard = new RosesCard[newLength];
                int index = 0;
                for (int p = 0; p < gameStage.getDiscardCards().length; p++) {
                    if (gameStage.getDiscardCards()[p] != null) {
                        tempPickCard[index] = gameStage.getDiscardCards()[p];
                        index++;
                    }
                    gameStage.getDiscardPot().removeElement(gameStage.getDiscardCards()[p]);

                }
                // Réinitialiser la défausse à zéro
                RosesCard[] newEmptyDiscard = new RosesCard[26];
                gameStage.setPickCards(tempPickCard);
                gameStage.setDiscardCards(newEmptyDiscard);

                // Mettre à jour la pioche avec les nouvelles cartes
                nbMovements = 0;
                return true;
            }
            System.out.println("Invalid choice : u cant pick a card. Retry ! ");
            return false;
        }

        List<Point> ValidCells = null;
        if (cardType.equals("H")) {
            ValidCells = gameStage.getBoard().computeValidCells(cardType, model.getIdPlayer());
            gameStage.getBoard().setValidCells(ValidCells);
            System.out.println("Valid cells : " + ValidCells);
        }
        int choice = (int) (line.charAt(1) - '1');
        if (choice >= 0 && choice < gameStage.getPlayer1MovementCards().length && model.getIdPlayer() == 0 && cardType.equals("M") && gameStage.getPlayer1MovementCards()[choice] != null) {
            direction = gameStage.getPlayer1MovementCards()[choice].getDirection();
            number = gameStage.getPlayer1MovementCards()[choice].getValue();
        } else if (choice >= 0 && choice < gameStage.getPlayer2MovementCards().length && model.getIdPlayer() == 1 && cardType.equals("M") && gameStage.getPlayer2MovementCards()[choice] != null) {
            direction = gameStage.getPlayer2MovementCards()[choice].getDirection();
            number = gameStage.getPlayer2MovementCards()[choice].getValue();
        } else if (choice >= 0 && choice < gameStage.getPlayer1MovementCards().length && model.getIdPlayer() == 0 && cardType.equals("H") && gameStage.getPlayer1MovementCards()[choice] != null && !ValidCells.isEmpty() && gameStage.getPlayer1HeroCards().length > 0) {
            direction = gameStage.getPlayer1MovementCards()[choice].getDirection();
            number = gameStage.getPlayer1MovementCards()[choice].getValue();
        } else if (choice >= 0 && choice < gameStage.getPlayer2MovementCards().length && model.getIdPlayer() == 1 && cardType.equals("H") && gameStage.getPlayer2MovementCards()[choice] != null && !ValidCells.isEmpty() && gameStage.getPlayer2HeroCards().length > 0) {
            direction = gameStage.getPlayer2MovementCards()[choice].getDirection();
            number = gameStage.getPlayer2MovementCards()[choice].getValue();
        } else {
            System.out.println("Invalid choice. Retry!");
            return false;
        }

        switch (direction) {
            case "W":
                col = col - number;
                break;
            case "N-E":
                col = col + number;
                row = row - number;
                break;
            case "E":
                col = col + number;
                break;
            case "S-E":
                col = col + number;
                row = row + number;
                break;
            case "S":
                row = row + number;
                break;
            case "S-W":
                row = row + number;
                col = col - number;
                break;
            case "N":
                row = row - number;
                break;
            default:
                row = row - number;
                col = col - number;

        }
        // check coords validity
        if ((row < 0) || (row > 8)) {
            col = lastCol;
            row = lastRow;
            return false;
        }
        if ((col < 0) || (col > 8)) {
            col = lastCol;
            row = lastRow;
            return false;
        }
        // a pawn is already there
        if (gameStage.getBoard().isElementAt(row, col) && cardType.equals("M")) {
            System.out.println("Invalid move: There is already a pawn at the specified location.");
            return false;
        }
        // check if the pawn is still in its pot
        ContainerElement pot = null;
        if (model.getIdPlayer() == 0) {
            pot = gameStage.getBluePot();
        } else {
            pot = gameStage.getRedPot();
        }
        if (pot.isEmptyAt(pawnIndex, 0)) {
            col = lastCol;
            row = lastRow;
            return false;
        }
        GameElement pawn = pot.getElement(pawnIndex, 0);
        GameElement crownPawn = gameStage.getCrownPawn();
        gameStage.getBoard().moveElement(crownPawn, row, col);

        // compute valid cells for the chosen pawn
        if (cardType.equals("M")) {
            gameStage.getBoard().setValidCells(cardType, model.getIdPlayer());
        }
        if (!gameStage.getBoard().canReachCell(row, col)) {
            col = lastCol;
            row = lastRow;
            return false;
        }
        if (cardType.equals("M")) {
            ActionList actions = ActionFactory.generatePutInContainer(model, pawn, "RoseBoard", row, col);
            actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
            ActionPlayer play = new ActionPlayer(model, this, actions);
            lastRow = row;
            lastCol = col;
            isTheFirstTime = false;
            play.start();
        }
        if (cardType.equals("H")) {
            if (model.getIdPlayer() == 0 && gameStage.getPlayer1HeroCards().length > 0) {
                ActionList actions = new ActionList(true);
                ActionPlayer play = new ActionPlayer(model, this, actions);
                RosesPawn pawnToSwap = (RosesPawn) gameStage.getBoard().getElement(row, col);
                if (pawnToSwap != null && pawnToSwap.getColor() == PAWN_RED) {
                    pawnToSwap.setColor(PAWN_BLUE);
                    lastRow = row;
                    lastCol = col;
                    isTheFirstTime = false;
                    System.out.println(pawnToSwap.getColor());
                    play.start();
                } else {
                    System.out.println("Invalid move. No pawn at the specified location or the pawn is already of your color.");
                    return false;
                }
            } else if (model.getIdPlayer() == 1 && gameStage.getPlayer2HeroCards().length > 0) {
                ActionList actions = new ActionList(true);
                ActionPlayer play = new ActionPlayer(model, this, actions);
                RosesPawn pawnToSwap = (RosesPawn) gameStage.getBoard().getElement(row, col);
                if (pawnToSwap != null && pawnToSwap.getColor() == PAWN_BLUE){
                    pawnToSwap.setColor(PAWN_RED);
                    lastRow = row;
                    lastCol = col;
                    isTheFirstTime = false;
                    System.out.println(pawnToSwap.getColor());
                    play.start();
                } else {
                    System.out.println("Invalid move. No pawn at the specified location or the pawn is already of your color.");
                    return false;
                }
            } else {
                System.out.println("Invalid choice. No hero cards available. Retry!");
                return false;
            }
        }
        if (model.getIdPlayer() == 0) {
            gameStage.removeElement(gameStage.getPlayer1MovementCards()[choice]);
            if (cardType.equals("H")) {
                gameStage.removeElement(gameStage.getPlayer1HeroCards()[gameStage.getPlayer1HeroCards().length - 1]);
                RosesCard[] tempHeroCards = gameStage.getPlayer1HeroCards();
                RosesCard[] copyOfPickPotCards = new RosesCard[tempHeroCards.length - 1];
                System.arraycopy(tempHeroCards, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
                tempHeroCards = copyOfPickPotCards;
                gameStage.setPlayer1HeroCards(tempHeroCards);
            }
            gameStage.getDiscardCards()[nbMovements] = gameStage.getPlayer1MovementCards()[choice];
            gameStage.getPlayer1MovementCards()[choice].flip();
            gameStage.getDiscardPot().addElement(gameStage.getPlayer1MovementCards()[choice], 0, 0);
            gameStage.getPlayer1MovementCards()[choice] = null;
        } else {
            gameStage.removeElement(gameStage.getPlayer2MovementCards()[choice]);
            if (cardType.equals("H")) {
                gameStage.removeElement(gameStage.getPlayer2HeroCards()[gameStage.getPlayer2HeroCards().length - 1]);
                RosesCard[] tempHeroCards = gameStage.getPlayer2HeroCards();
                RosesCard[] copyOfPickPotCards = new RosesCard[tempHeroCards.length - 1];
                System.arraycopy(tempHeroCards, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
                tempHeroCards = copyOfPickPotCards;
                gameStage.setPlayer2HeroCards(tempHeroCards);
            }
            gameStage.getDiscardCards()[nbMovements] = gameStage.getPlayer2MovementCards()[choice];
            gameStage.getPlayer2MovementCards()[choice].flip();
            gameStage.getDiscardPot().addElement(gameStage.getPlayer2MovementCards()[choice], 0, 0);
            gameStage.getPlayer2MovementCards()[choice] = null;
        }
        nbMovements++;
        return true;
    }


    private void checkIfPlayerPlay(String cardType) {
        int row, col;
        if (isTheFirstTime) {
            row = 4;
            col = 4;
        } else {
            row = lastRow;
            col = lastCol;
        }

        RosesStageModel gameStage = (RosesStageModel) model.getGameStage();
        RosesStageView viewStage = (RosesStageView) view.getGameStageView();
        System.out.println("entre boucle, tempcheck : " + gameStage.getChecked());

        boolean tempCheck = false;

        for (int i = 0; i < gameStage.getPlayer1MovementCards().length; i++) {
            if (isTheFirstTime) {
                row = 4;
                col = 4;
            } else {
                row = lastRow;
                col = lastCol;
            }

            if (model.getIdPlayer() == 0 && gameStage.getPlayer2MovementCards()[i] == null) {
                tempCheck = true; // il peut piocher donc jouer
                gameStage.setChecked(tempCheck);
                return;
            } else if (model.getIdPlayer() == 1 && gameStage.getPlayer1MovementCards()[i] == null) {
                tempCheck = true;
                gameStage.setChecked(tempCheck);
                return;
            } else if (model.getIdPlayer() == 0 && gameStage.getPlayer1HeroCards() != null) {
                tempCheck = false;
            }

            if (!tempCheck) {
                String checkDirection;
                int checkCardValue;

                if (model.getIdPlayer() == 0) {
                    checkDirection = gameStage.getPlayer2MovementCards()[i].getDirection();
                    checkCardValue = gameStage.getPlayer2MovementCards()[i].getValue();
                } else {
                    checkDirection = gameStage.getPlayer1MovementCards()[i].getDirection();
                    checkCardValue = gameStage.getPlayer1MovementCards()[i].getValue();
                }

                switch (checkDirection) {
                    case "W":
                        col = col - checkCardValue;
                        break;
                    case "N-E":
                        col = col + checkCardValue;
                        row = row - checkCardValue;
                        break;
                    case "E":
                        col = col + checkCardValue;
                        break;
                    case "S-E":
                        col = col + checkCardValue;
                        row = row + checkCardValue;
                        break;
                    case "S":
                        row = row + checkCardValue;
                        break;
                    case "S-W":
                        row = row + checkCardValue;
                        col = col - checkCardValue;
                        break;
                    case "N":
                        row = row - checkCardValue;
                        break;
                    default:
                        row = row - checkCardValue;
                        col = col - checkCardValue;
                }

                if (row >= 0 && col >= 0 && gameStage.getBoard().canReachCell(row, col)) {
                    tempCheck = true;
                    gameStage.setChecked(tempCheck);
                    return;
                } else if (row >= 0 && col >= 0 && row <= 8 && col <= 8 && i > 0 && gameStage.getBoard().isElementAt(row, col) && cardType.equals("H")) {
                    if (model.getIdPlayer() == 0 && gameStage.getPlayer2HeroCards()[i - 1] != null) {
                        tempCheck = true;
                        System.out.println("can play hero cards");
                        gameStage.setChecked(tempCheck);
                        return;
                    } else if (model.getIdPlayer() == 1 && gameStage.getPlayer1HeroCards()[i - 1] != null) {
                        tempCheck = true;
                        System.out.println("can play hero cards");
                        gameStage.setChecked(tempCheck);
                        return;
                    }
                } else {
                    System.out.println("apayian");
                    tempCheck = false;
                }
            }
            gameStage.setChecked(tempCheck);
            System.out.println("tempcheck : " + gameStage.getChecked());
        }

        lastRow = row;
        lastCol = col;
    }

}
