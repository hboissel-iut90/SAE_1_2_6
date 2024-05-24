package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.ContainerElement;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.model.action.ActionList;
import boardifier.view.View;
import model.RosesCard;
import model.RosesPawn;
import model.RosesStageModel;
import view.RosesStageView;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static model.RosesPawn.PAWN_BLUE;
import static model.RosesPawn.PAWN_RED;

public class RosesControllerTool extends Controller {

    public BufferedReader consoleIn;
    boolean firstPlayer;
    boolean isTheFirstTime = true;

    boolean isTheEndOfTheStage = false;

    private String difficulty;
    int nbMovements = 0;

    public RosesControllerTool(Model model, View view, String difficulty) {
        super(model, view);
        firstPlayer = true;
        this.difficulty = difficulty;
    }

    /**
     * Defines what to do within the single stage of the single party
     * It is pretty straight forward to write :
     */
    public void stageLoop() {
        consoleIn = new BufferedReader(new InputStreamReader(System.in));
        update();
        while (!model.isEndStage()) {
            playTurnTool();
            endOfTurnTool();
            update();
        }
        endGame();
    }

    public void playTurnTool() {
        // get the new player
        Player p = model.getCurrentPlayer();
        if (p.getType() == Player.COMPUTER) {
            ActionPlayer play = null;
            System.out.println("COMPUTER PLAYS");
            if (difficulty.equals("EE")|| difficulty.equals("E")) {
                RosesDeciderEasy decider = new RosesDeciderEasy(model, this);
                play = new ActionPlayer(model, this, decider, null);
            } else if (difficulty.equals("HH") || difficulty.equals("H")) {
                RosesDeciderHard decider = new RosesDeciderHard(model, this);
                play = new ActionPlayer(model, this, decider, null);
            } else {
                if (model.getIdPlayer() == 0) {
                    RosesDeciderEasy decider = new RosesDeciderEasy(model, this);
                    play = new ActionPlayer(model, this, decider, null);
                } else {
                    RosesDeciderHard decider = new RosesDeciderHard(model, this);
                    play = new ActionPlayer(model, this, decider, null);
                }
            }
            play.start();
        } else {
            checkIfPlayerPlayTool();

            boolean ok = false;
            while (!ok && !isTheEndOfTheStage) {
                System.out.println("Player playing");
                System.out.print(p.getName() + " > ");
                try {
                    String line = consoleIn.readLine();
                    if (line.equals("stop")) {
                        RosesStageModel stageModel = (RosesStageModel) model.getGameStage();
                        stageModel.computePartyResult();
                        return;
                    }
                        if (line == null || line.trim().isEmpty()) {
                            System.out.println("wrong instructions");
                            continue;
                        }
                    if (line.length() == 2 || String.valueOf(line.charAt(0)).equals("P")) {
                        ok = analyseAndPlayTool(line);
                    }
                    if (!ok) {
                        System.out.println("incorrect instruction. retry !");
                        RosesStageModel stageModel = (RosesStageModel) model.getGameStage();



                    }
                } catch (IOException e) {
                }
            }
        }
    }
    
    public void endOfTurnTool() {
        model.setNextPlayer();
        // get the new player to display its name
        Player p = model.getCurrentPlayer();
        RosesStageModel stageModel = (RosesStageModel) model.getGameStage();
        stageModel.getPlayerName().setText(p.getName());
        stageModel.update();
    }

    public boolean analyseAndPlayTool(String line) {
        RosesStageModel gameStage = (RosesStageModel) model.getGameStage();
        RosesStageView viewStage = (RosesStageView) view.getGameStageView();
        nbMovements = gameStage.getNbMovements();
        int row = 0, col = 0;
        if (isTheFirstTime) {
            row = 4;
            col = 4;
        } else {
            for (int i = 0; i < gameStage.getBoard().getNbCols(); i++) {
                for (int j = 0; j < gameStage.getBoard().getNbRows(); j++) {
                    if (gameStage.getBoard().getElement(i, j, 1) != null) { // prend la position du pion couronne car parfois il n'est pas detecté a cause du pion bleu au dessus
                        row = i;
                        col = j;
                    }
                }
            }
        }
        int pawnIndex = 0;
        String direction = "";
        String cardType = "";
        int number = 0;
        cardType = String.valueOf(line.charAt(0));
        if (cardType.equals("P")) {
            if (model.getIdPlayer() == 0) {
                for (int i = 0; i < gameStage.getPlayer1MovementCards().length; i++) {
                    if (gameStage.getPlayer1MovementCards()[i] == null && gameStage.getPickCards().length > 0) {
                        gameStage.getPlayer1MovementCards()[i] = gameStage.getPickCards()[gameStage.getPickCards().length - 1];
                        gameStage.getPlayer1MovementCards()[i].flip();
                        gameStage.getMoovBluePot().addElement(gameStage.getPlayer1MovementCards()[i], i, 0);
                        setTempPickCards(gameStage);
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
                        setTempPickCards(gameStage);
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
                nbMovements = 0;
                gameStage.setNbMovements(nbMovements);
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
            return false;
        }
        if ((col < 0) || (col > 8)) {
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
            return false;
        }
        GameElement pawn = pot.getElement(pawnIndex, 0);
        GameElement crownPawn = gameStage.getCrownPawn();


        // compute valid cells for the chosen pawn
        if (cardType.equals("M")) {
            gameStage.getBoard().setValidCells(cardType, model.getIdPlayer());
        }
        if (!gameStage.getBoard().canReachCell(row, col)) {
            return false;
        }
        if (cardType.equals("M")) {
            ActionList actions = ActionFactory.generatePutInContainer(model, pawn, "RoseBoard", row, col);
            actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
            ActionPlayer play = new ActionPlayer(model, this, actions);
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
                if (pawnToSwap != null && pawnToSwap.getColor() == PAWN_BLUE) {
                    pawnToSwap.setColor(PAWN_RED);
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
        gameStage.setNbMovements(nbMovements);
        gameStage.getBoard().moveElement(crownPawn, row, col);
        return true;
    }


    public void checkIfPlayerPlayTool() {
        int row = 0, col = 0;

        RosesStageModel gameStage = (RosesStageModel) model.getGameStage();
        RosesStageView viewStage = (RosesStageView) view.getGameStageView();

        boolean tempCheck = false;

        for (int i = 0; i < gameStage.getPlayer1MovementCards().length; i++) {
            for (int p = 0; p < gameStage.getBoard().getNbCols(); p++) {
                for (int j = 0; j < gameStage.getBoard().getNbRows(); j++) {
                    if (gameStage.getBoard().getElement(p, j, 1) != null) { // prend la position du pion couronne car parfois il n'est pas detecté a cause du pion bleu au dessus
                        row = p;
                        col = j;
                    }
                }
            }

            if (model.getIdPlayer() == 0 && gameStage.getPlayer1MovementCards()[i] == null) {
                tempCheck = true; // il peut piocher donc jouer
                gameStage.setChecked(tempCheck);
                return;
            } else if (model.getIdPlayer() == 1 && gameStage.getPlayer2MovementCards()[i] == null) {
                tempCheck = true;
                gameStage.setChecked(tempCheck);
                return;
            }

            if (!tempCheck) {
                String checkDirection;
                int checkCardValue;

                if (model.getIdPlayer() == 0) {
                    checkDirection = gameStage.getPlayer1MovementCards()[i].getDirection();
                    checkCardValue = gameStage.getPlayer1MovementCards()[i].getValue();
                } else {
                    checkDirection = gameStage.getPlayer2MovementCards()[i].getDirection();
                    checkCardValue = gameStage.getPlayer2MovementCards()[i].getValue();
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

                if (row >= 0 && col >= 0 && gameStage.getBoard().canReachCell(row, col) && !gameStage.getBoard().isElementAt(row, col)) {
                    tempCheck = true;
                    gameStage.setChecked(tempCheck);
                    return;
                } else if (row >= 0 && col >= 0 && row <= 8 && col <= 8 && i > 0 && gameStage.getBoard().isElementAt(row, col)) {
                    RosesPawn tempPawn = (RosesPawn) gameStage.getBoard().getElement(row, col);
                    if (model.getIdPlayer() == 0 && i < gameStage.getPlayer1HeroCards().length && gameStage.getPlayer1HeroCards()[i - 1] != null && tempPawn.getColor() == PAWN_RED) {
                        tempCheck = true;
                        System.out.println("can play hero cards");
                        gameStage.setChecked(tempCheck);
                        return;
                    } else if (model.getIdPlayer() == 1 && i < gameStage.getPlayer2HeroCards().length && gameStage.getPlayer2HeroCards()[i - 1] != null && tempPawn.getColor() == PAWN_BLUE) {
                        tempCheck = true;
                        System.out.println("can play hero cards");
                        gameStage.setChecked(tempCheck);
                        return;
                    }
                }
            }

        }
        isTheEndOfTheStage = true;
        gameStage.computePartyResult();
    }

    public void setTempPickCards(RosesStageModel stageModel) {
        RosesCard[] tempPickCard = stageModel.getPickCards();
        RosesCard[] copyOfPickPotCards = new RosesCard[tempPickCard.length - 1];
        System.arraycopy(tempPickCard, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
        tempPickCard = copyOfPickPotCards;
        stageModel.setPickCards(tempPickCard);
    }

}