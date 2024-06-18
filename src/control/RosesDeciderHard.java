package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.animation.AnimationTypes;
import boardifier.view.GameStageView;
import boardifier.view.View;
import javafx.application.Platform;
import model.*;
import view.PawnLook;
import view.RosesCardLook;
import view.RosesStageView;
import view.RosesView;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.CountDownLatch;

import static model.RosesPawn.PAWN_BLUE;
import static model.RosesPawn.PAWN_RED;

public class RosesDeciderHard extends Decider {// La classe RosesDeciderHard hérite de la classe Decider
    // Déclaration des variables et des structures de données utilisées dans la classe
    Comparator<String> comparator = new Comparator<String>() {
        @Override
        public int compare(String str1, String str2) {
            return str2.compareTo(str1);
        }
    };

    private static final String[] typeOfMoves = {"M", "H"};
    private static final String[] typeOfPlay = {"block", "expend"};

    private HashMap<String, ArrayList<Integer>> CardsLeft = new HashMap<String, ArrayList<Integer>>();
    private TreeMap<String, Double> possibleMoves;
    private String move;
    private ArrayList<Point> opponentPawns;
    private Point[][] board = new Point[9][9];
    private ArrayList<Point> Pawns;
    private RosesPawn[] pawnsPot;
    private int nbrPawns = 0;
    ArrayList<Point> AllPawns = new ArrayList<Point>();
    private RosesCard[] Cards;
    private int HeroCardLeft = 0;
    private Point nextDestination;
    private RosesView view;
    // Constructeur de la classe
    public RosesDeciderHard(Model model, Controller control, View view) {
        super(model, control);
        this.view = (RosesView) view;
    }
    // La méthode decide() est la principale méthode de la classe. Elle est responsable de la prise de décision pour le jeu.
    @Override
    public ActionList decide() {
        // Le code dans cette méthode est principalement utilisé pour initialiser les variables et les structures de données nécessaires pour le jeu.
        // Il contient également la logique pour déterminer le meilleur mouvement possible pour le joueur.
        possibleMoves = new TreeMap<String, Double>(comparator);
        RosesStageModel stage = (RosesStageModel) model.getGameStage();
        Cards = new RosesCard[5];
        RosesBoard board = stage.getBoard();
        RosesPawnPot pawnPot = null;
        RosesPawn pawn = null;
        RosesPawn[] pawnsPot = null;
        Point crownPawn = new Point();
        RosesPawn crown = null;
        int nbMovements = stage.getNbMovements();
        int choice = 0;
        ActionList actions = new ActionList();
        RosesCard[] pick = stage.getPickCards();
        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < pick.length; i++) {
            if (pick[i] != null) {
                if (CardsLeft.containsKey(pick[i].getDirection())) {
                    CardsLeft.get(pick[i].getDirection()).add(pick[i].getValue());
                } else {
                    CardsLeft.put(pick[i].getDirection(), new ArrayList<Integer>());
                    CardsLeft.get(pick[i].getDirection()).add(pick[i].getValue());
                }
            }
        }
        if (model.getIdPlayer() == RosesPawn.PAWN_BLUE) {
            pawnPot = stage.getBluePot();
            pawnsPot = stage.getBluePawns();
            opponentPawns = (ArrayList<Point>) board.computeValidCells("H", 0);
            Pawns = (ArrayList<Point>) board.computeValidCells("H", 1);
            for (int i = 0; i < stage.getPlayer1MovementCards().length ; i++){
                Cards[i] = stage.getPlayer1MovementCards()[i];
            }
            HeroCardLeft = stage.getPlayer1HeroCards().length;
            nbrPawns = stage.getBluePawnsToPlay();
        } else {
            pawnPot = stage.getRedPot();
            pawnsPot = stage.getRedPawns();
            opponentPawns = (ArrayList<Point>) board.computeValidCells("H", 1);
            Pawns = (ArrayList<Point>) board.computeValidCells("H", 0);
            for (int i = 0; i < stage.getPlayer2MovementCards().length ; i++){
                Cards[i] = stage.getPlayer2MovementCards()[i];
            }
            HeroCardLeft = stage.getPlayer2HeroCards().length;
            nbrPawns = stage.getRedPawnsToPlay();
        }
        if (pawnPot.getElement(0,0) == null) {
            Platform.runLater(() -> {
                control.endGame();
                latch.countDown();
            });
            try {
                latch.await(); // Attend que la tâche soit terminée
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return actions;
        }
        System.out.println(opponentPawns);
        System.out.println(Pawns);
        for (RosesCard card : Cards) {
            System.out.println(card);
        }
        System.out.println(HeroCardLeft);
        AllPawns.addAll(opponentPawns);
        AllPawns.addAll(Pawns);
        for (int i = 0; i < board.getNbRows(); i++) {
            for (int j = 0; j < board.getNbCols(); j++) {
                if (board.getElement(i, j ) != null) {// prend la position du pion couronne car parfois il n'est pas detecté a cause du pion bleu au dessus
                    RosesPawn tempPawn = (RosesPawn) board.getElement(i, j);
                    if (tempPawn.getColor() == RosesPawn.PAWN_YELLOW) {
                        crownPawn.setLocation(i, j);
                        crown = (RosesPawn) board.getElement(i, j);
                    }
                }
                if (board.getElement(i, j, 1) != null) {
                    RosesPawn tempPawn = (RosesPawn) board.getElement(i, j, 1);
                    if (tempPawn.getColor() == RosesPawn.PAWN_YELLOW) {
                        crownPawn.setLocation(i, j);
                        crown = (RosesPawn) board.getElement(i, j, 1);
                    }

                }
            }
        }
        for (int i = 0; i < Cards.length; i++) {
            if (Cards[i] == null && !possibleMoves.containsKey("P")) {
                possibleMoves.put("P", 2.0);
                System.out.println("Test 1");

            }
            if (Cards[i] != null) {
                for (String typeOfMove : typeOfMoves) {
                    for (String typeOfPlays : typeOfPlay) {
                        System.out.println("Tested move: " + typeOfMove + " " + typeOfPlays + " " + (i + 1));

                        nextDestination = getDestination(crownPawn, i);
                        double score = evaluateMove(typeOfMove, typeOfPlays, nextDestination);
                        if (score != -1) {
                            possibleMoves.put(typeOfMove + " " + typeOfPlays + " " + (i + 1), score);
                        }
                    }
                }
            }
        }
        System.out.println("Possible moves: " + possibleMoves);
        if (possibleMoves.isEmpty()) {
            System.out.println("No possible moves.");
            System.out.println(model.getCurrentPlayerName());
            System.out.println("Test 2");
            Platform.runLater(() -> {
                control.endGame();
                latch.countDown();
            });
            try {
                latch.await(); // Attend que la tâche soit terminée
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return actions;
        }
        double maxScore = Collections.max(possibleMoves.values());
        List<String> highestScoreMoves = new ArrayList<>();
        for (Map.Entry<String, Double> entry : possibleMoves.entrySet()) {
            if (entry.getValue() == maxScore) {
                highestScoreMoves.add(entry.getKey());
            }
        }
        boolean heroMove = false;
        boolean mouvementMove = false;
        for (String move : highestScoreMoves) {
            if (move.charAt(0) == 'H') {
                heroMove = true;
            }
            if (move.charAt(0) == 'M') {
                mouvementMove = true;
            }
        }
        if (heroMove && mouvementMove) {
            highestScoreMoves.removeIf(move -> move.charAt(0) == 'H');
        }
        if (highestScoreMoves.size() > 1) {
            move = highestScoreMoves.get((int) (Math.random() * highestScoreMoves.size()));
        } else {
            move = highestScoreMoves.get(0);
        }
        String typeOfMove = move.substring(0, 1);
        if (!typeOfMove.equals("P")) {
            choice = Integer.parseInt(move.substring(move.length() - 1))-1;
            nextDestination = getDestination(crownPawn, choice);
        }
        System.out.println("The move is: " + move);

        if (typeOfMove.equals("H")) {
            int finalChoice = choice;
            Platform.runLater(() -> {
                actions.addAll(playHeroCard(stage, nextDestination.x, nextDestination.y, model.getIdPlayer(), (RosesPawn) board.getElement(nextDestination.x, nextDestination.y), finalChoice));
                latch.countDown();
            });
            try {
                latch.await(); // Attend que la tâche soit terminée
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (typeOfMove.equals("M")) {
            actions.addAll(movePawn(stage, pawnsPot, nbrPawns, nextDestination.x, nextDestination.y));
        }
        int pickCardsLength = 0;
        for (int i = 0; i < stage.getPickCards().length; i++) {
            if (stage.getPickCards()[i] != null) {
                pickCardsLength++;
            }
        }

        if (typeOfMove.equals("P")) {
            int finalPickCardsLength = pickCardsLength;
            if (model.getIdPlayer() == 0) {
                Platform.runLater(() -> {
                    actions.addAll(pickACard(stage, view.getGameStageView(), 0, finalPickCardsLength-1));
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    latch.countDown();
                });
            } else {
                Platform.runLater(() -> {
                    actions.addAll(pickACard(stage, view.getGameStageView(), 1, finalPickCardsLength-1));
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    latch.countDown();
                });

            }
            try {
                latch.await(); // Attend que la tâche soit terminée
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (!typeOfMove.equals("P") && !typeOfMove.equals("H")) {

            if (model.getIdPlayer() == 0) {
                for (int i = 0; i < stage.getDiscardCards().length - 1; i++) {
                    if (stage.getDiscardCards()[i] == null) {
                        actions.addAll(discardACard(stage, stage.getPlayer1MovementCards(), choice, i));
                        break;
                    }
                }
            } else {
                for (int i = 0; i < stage.getDiscardCards().length - 1; i++) {
                    if (stage.getDiscardCards()[i] == null) {
                        actions.addAll(discardACard(stage, stage.getPlayer2MovementCards(), choice, i));
                        break;
                    }
                }
            }
        }
        nbMovements++;
        stage.setNbMovements(nbMovements);
        boolean isNotEmpty = false;

        for (int k = 0; k < stage.getPickCards().length; k++) {
            if (stage.getPickCards()[k] != null) {
                isNotEmpty = true;
            }
        }
        if (!isNotEmpty) {
            Platform.runLater(() -> {
                moveDiscardCardsToPickPot(stage);
                latch.countDown();
            });
            try {
                latch.await(); // Attend que la tâche soit terminée
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        actions.setDoEndOfTurn(true);
        return actions;
    }
    // Le reste du code dans cette classe est principalement composé de méthodes d'aide qui sont utilisées pour effectuer diverses tâches, telles que l'évaluation des mouvements possibles, la détermination de la destination d'un mouvement, etc.
    // La méthode evaluateMove() est utilisée pour évaluer un mouvement possible. Elle prend en entrée le type de mouvement, le type de jeu et la destination du mouvement, et renvoie un score pour ce mouvement.
    private double evaluateMove(String typeOfMove, String typeOfPlay, Point destination){
        // Le code dans cette méthode est utilisé pour évaluer un mouvement possible en fonction de divers facteurs, tels que le type de mouvement, le type de jeu, la destination du mouvement, etc.
        resetBoard(board);
        double score = 0;
        if (!opponentPawns.contains(destination) && typeOfMove.equals("H")) {
            return -1;
        }
        if (typeOfMove.equals("H") && HeroCardLeft ==0){
            return -1;
        }
        if (AllPawns.contains(destination) && typeOfMove.equals("M")) {
            return -1;
        }
        if (destination.x < 0 || destination.x > 8 || destination.y < 0 || destination.y > 8) {
            return -1;
        }
        if (typeOfMove.equals("H") && typeOfPlay.equals("block")) {
            for (Point opponentPawn : opponentPawns) {
                board[opponentPawn.x][opponentPawn.y] = new Point(opponentPawn.x, opponentPawn.y);
            }
            ArrayList<Point> adjacents = adjacentsPawns(board, destination);
            if (adjacents.size() < 4) {
                score = -1;
            } else {
                score = adjacents.size();
            }
        }
        if (typeOfMove.equals("H") && typeOfPlay.equals("expend")) {
            for (Point Pawns : Pawns) {
                board[Pawns.x][Pawns.y] = new Point(Pawns.x, Pawns.y);
            }
            ArrayList<Point> adjacents = adjacentsPawns(board, destination);
            if (adjacents.size() < 4) {
                score = -1;
            } else {
                score = adjacents.size() * 1.25;
            }
        }
        if (typeOfMove.equals("M") && typeOfPlay.equals("block")) {
            for (Point opponentPawn : opponentPawns) {
                board[opponentPawn.x][opponentPawn.y] = new Point(opponentPawn.x, opponentPawn.y);
            }
            ArrayList<Point> adjacents = adjacentsPawns(board, destination);
            adjacents.remove(0);
            score = adjacents.size() * 1.5;
        }
        if (typeOfMove.equals("M") && typeOfPlay.equals("expend")) {
            for (Point Pawns : Pawns) {
                board[Pawns.x][Pawns.y] = new Point(Pawns.x, Pawns.y);
            }
            ArrayList<Point> adjacents = adjacentsPawns(board, destination);
            adjacents.remove(0);
            score = adjacents.size() * 1.754;
        }
        return score;
    }
    // La méthode getDestination() est utilisée pour déterminer la destination d'un mouvement. Elle prend en entrée la position actuelle du pion et l'index de la carte, et renvoie la destination du mouvement.
    private Point getDestination (Point crownPawn, int card){
        // Le code dans cette méthode est utilisé pour déterminer la destination d'un mouvement en fonction de la direction et de la valeur de la carte.
        Point destination = new Point();
        String direction = Cards[card].getDirection();
        int value = Cards[card].getValue();
        switch (direction) {
            case "W":
                destination.setLocation(crownPawn.x, crownPawn.y- value);
                break;
            case "N-E":
                destination.setLocation(crownPawn.x - value, crownPawn.y + value);
                break;
            case "E":
                destination.setLocation(crownPawn.x, crownPawn.y + value);
                break;
            case "S-E":
                destination.setLocation(crownPawn.x + value, crownPawn.y + value);
                break;
            case "S":
                destination.setLocation(crownPawn.x + value, crownPawn.y);
                break;
            case "S-W":
                destination.setLocation(crownPawn.x + value, crownPawn.y - value);
                break;
            case "N-W":
                destination.setLocation(crownPawn.x - value, crownPawn.y - value);
                break;
            case "N":
                destination.setLocation(crownPawn.x - value, crownPawn.y);
                break;
        }
        return destination;
    }
    // Les autres méthodes dans cette classe sont principalement des méthodes d'aide qui sont utilisées pour effectuer diverses tâches, telles que la recherche des pions adjacents, la mise à jour des cartes dans le pot de cartes, etc.


    public ArrayList<Point> adjacentsPawns(Point[][] board, Point depart){
        ArrayList<Point> adjacents = new ArrayList<Point>();
        ArrayList<Point> toDo = new ArrayList<Point>();
        toDo.add(depart);
        int startRow = depart.x;
        int startCol = depart.y;
        int endRow = board.length;
        int endCol = board[0].length;
        int[][] directions = {{0,1},{1,0},{0,-1},{-1,0}};
        while (!toDo.isEmpty()) {
            Point current = toDo.remove(0);
            ArrayList<Point> neighbors = new ArrayList<Point>();
            for (int[] direction : directions) {
                int newRow = current.x + direction[0];
                int newCol = current.y + direction[1];
                if (newRow < endRow && newRow >= 0 && newCol < endCol && newCol >= 0) {
                    if (board[newRow][newCol] != null) {
                        neighbors.add(new Point(newRow, newCol));
                    }
                }
            }
            for (Point neighbor : neighbors) {
                if (!adjacents.contains(neighbor) && !toDo.contains(neighbor)) {
                    toDo.add(neighbor);
                }
            }
            adjacents.add(current);
        }
        return adjacents;
    }


    private void moveDiscardCardsToPickPot(RosesStageModel stageModel) {
        int nb = 0;
        stageModel.unselectAll();
        RosesStageView stageView = (RosesStageView) view.getGameStageView();


        for (int i = 0; i < stageModel.getDiscardCards().length; i++) {
            if (stageModel.getDiscardCards()[i] != null) {
                RosesCard card = stageModel.getDiscardCards()[i];
                stageModel.getPickCards()[nb] = card;
                stageModel.getDiscardCards()[i] = null;

                // Create actions to animate the card movement
                ActionList actions = ActionFactory.generatePutInContainer(control, model, card, stageModel.getPickPot().getName(), 0, 0, AnimationTypes.LOOK_SIMPLE, 10);
                ActionPlayer actionPlayer = new ActionPlayer(model, control, actions);
                actionPlayer.start();
                // Update the visual representation of the card in the stage view
                RosesCardLook look = (RosesCardLook) stageView.getElementLook(card);
                if (look != null) {
                    look.update(card, look);
                }

                nb++;
            }
        }
    }

    private ActionList movePawn(RosesStageModel stageModel, RosesPawn[] pawnPot, int nbPionRest, int row, int col) {
        ActionList actions = new ActionList();
        actions.addAll(ActionFactory.generatePutInContainer(control, model, pawnPot[nbPionRest - 1], stageModel.getBoard().getName(), row, col, AnimationTypes.MOVE_LINEARPROP, 40));
        actions.addAll(ActionFactory.generatePutInContainer(control, model, stageModel.getCrownPawn(), stageModel.getBoard().getName(), row, col, AnimationTypes.MOVE_LINEARPROP, 4));// je le fais apres avec des facteurs différent pour qu'il soit au dessus de l'autre pion ce neuille
        return actions;
    }

    private ActionList discardACard(RosesStageModel stageModel, RosesCard[] movePot, int index, int i) {
        movePot[index].flip();
        System.out.println("test");
        System.out.println("is flipped : " + movePot[index].isFlipped());
        ActionList actions = new ActionList();
        actions.addAll(ActionFactory.generatePutInContainer(control, model, movePot[index], stageModel.getDiscardPot().getName(), 0, 0, AnimationTypes.LOOK_SIMPLE, 10));
        stageModel.getDiscardCards()[i] = movePot[index];
        movePot[index] = null;
        stageModel.playSound("cardpick.mp3");
        return actions;
    }

    public ActionList playHeroCard(RosesStageModel stageModel, int row, int col, int idPlayer, RosesPawn pawnToSwap, int index) {
        GameStageView stageView = view.getGameStageView();
        RosesCard[] movePot;
        ActionList actions = new ActionList();
        actions.addAll(ActionFactory.generatePutInContainer(control, model, stageModel.getCrownPawn(), stageModel.getBoard().getName(), row, col, AnimationTypes.MOVE_LINEARPROP, 5));
        if (idPlayer == 1) {
            pawnToSwap.setColor(PAWN_RED);
            stageModel.removeElement(stageModel.getPlayer2HeroCards()[stageModel.getPlayer2HeroCards().length - 1]);
            RosesCard[] tempHeroCards = stageModel.getPlayer2HeroCards();
            RosesCard[] copyOfPickPotCards = new RosesCard[tempHeroCards.length - 1];
            System.arraycopy(tempHeroCards, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
            stageModel.setPlayer2HeroCards(copyOfPickPotCards);
        } else {
            pawnToSwap.setColor(PAWN_BLUE);
            stageModel.removeElement(stageModel.getPlayer1HeroCards()[stageModel.getPlayer1HeroCards().length - 1]);
            RosesCard[] tempHeroCards = stageModel.getPlayer1HeroCards();
            RosesCard[] copyOfPickPotCards = new RosesCard[tempHeroCards.length - 1];
            System.arraycopy(tempHeroCards, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
            stageModel.setPlayer1HeroCards(copyOfPickPotCards);
        }
        PawnLook look = (PawnLook) stageView.getElementLook(pawnToSwap);
        look.updatePawn(pawnToSwap, look);
        stageView.addLook(look);
        if (idPlayer == 0) {
            movePot = stageModel.getPlayer1MovementCards();
        } else {
            movePot = stageModel.getPlayer2MovementCards();
        }
        for (int i = 0; i < stageModel.getDiscardCards().length - 1; i++) {
            if (stageModel.getDiscardCards()[i] == null) {
                actions.addAll(discardACard(stageModel, movePot, index, i));
                break;
            }
        }
        stageModel.playSound("cardpick.mp3");
        return actions;
    }

    private ActionList pickACard(RosesStageModel stageModel, GameStageView stageView, int numberOfThePlayer, int lengthOfPickPot) {
        RosesCard[] tmp;
        System.out.println("pick pot length : " + stageModel.getPickCards().length);
        ActionList actions = new ActionList();

        if (numberOfThePlayer == 0) {
            tmp = stageModel.getPlayer1MovementCards().clone();
        } else if (numberOfThePlayer == 1) {
            tmp = stageModel.getPlayer2MovementCards().clone();
        } else {
            return actions; // if the player number is invalid, exit the method (should be impossible but its good to clear this aspect)
        }
        boolean isFull = true;
            for (int j = 0; j < tmp.length; j++) {
                if (tmp[j] == null && isFull) {
                    isFull = false;
                    if (numberOfThePlayer == 0) {
                        actions.addAll(ActionFactory.generatePutInContainer(control, model, stageModel.getPickCards()[lengthOfPickPot], stageModel.getMoveBluePot().getName(), 0, j, AnimationTypes.LOOK_SIMPLE, 10));
                        stageModel.getPlayer1MovementCards()[j] = stageModel.getPickCards()[lengthOfPickPot];
                        RosesCardLook look = (RosesCardLook) stageView.getElementLook(stageModel.getPickCards()[lengthOfPickPot]);
                        RosesCard card = stageModel.getPlayer1MovementCards()[j];
                        card.flip();
                        look.update(card, look);
                    } else {
                        actions.addAll(ActionFactory.generatePutInContainer(control, model, stageModel.getPickCards()[lengthOfPickPot], stageModel.getMoveRedPot().getName(), 0, j, AnimationTypes.LOOK_SIMPLE, 10));
                        stageModel.getPlayer2MovementCards()[j] = stageModel.getPickCards()[lengthOfPickPot];
                        RosesCardLook look = (RosesCardLook) stageView.getElementLook(stageModel.getPickCards()[lengthOfPickPot]);
                        RosesCard card = stageModel.getPlayer2MovementCards()[j];
                        card.flip();
                        look.update(card, look);
                    }

                    stageModel.getPickCards()[lengthOfPickPot] = null;
                    stageModel.playSound("cardpick.mp3");
                }
            }
        return actions;
    }

    private void resetBoard(Point[][] board){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = null;
            }
        }
    }
}