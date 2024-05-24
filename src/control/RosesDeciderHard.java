package control;
import java.awt.*;
import java.lang.Math;

import boardifier.control.ActionFactory;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.*;
import java.util.*;
import java.util.List;

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
    ArrayList<Point> AllPawns = new ArrayList<Point>();
    private RosesCard[] Cards;
    private int HeroCardLeft = 0;
    private Point nextDestination;
    // Constructeur de la classe
    public RosesDeciderHard(Model model, Controller control) {
        super(model, control);
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
        Point crownPawn = new Point();
        RosesPawn crown = null;
        int nbMovements = stage.getNbMovements();
        int choice = 0;
        ActionList actions = new ActionList();
        RosesCard[] pick = stage.getPickCards();
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
            opponentPawns = (ArrayList<Point>) board.computeValidCells("H", 0);
            Pawns = (ArrayList<Point>) board.computeValidCells("H", 1);
            for (int i = 0; i < stage.getPlayer1MovementCards().length ; i++){
                Cards[i] = stage.getPlayer1MovementCards()[i];
            }
            HeroCardLeft = stage.getPlayer1HeroCards().length;
        } else {
            pawnPot = stage.getRedPot();
            opponentPawns = (ArrayList<Point>) board.computeValidCells("H", 1);
            Pawns = (ArrayList<Point>) board.computeValidCells("H", 0);
            for (int i = 0; i < stage.getPlayer2MovementCards().length ; i++){
                Cards[i] = stage.getPlayer2MovementCards()[i];
            }
            HeroCardLeft = stage.getPlayer2HeroCards().length;
        }
        if (pawnPot.getElement(0,0) == null) {
            stage.computePartyResult();
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
            stage.computePartyResult();
            actions.setDoEndOfTurn(true);
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
            RosesPawn pawnToSwap = (RosesPawn) board.getElement(nextDestination.x, nextDestination.y);
            if (model.getIdPlayer() == RosesPawn.PAWN_BLUE) {
                pawnToSwap.setColor(RosesPawn.PAWN_BLUE);
            } else {
                pawnToSwap.setColor(RosesPawn.PAWN_RED);
            }
        }

        if (typeOfMove.equals("M")) {
            pawn = (RosesPawn) pawnPot.getElement(0,0);
            actions = ActionFactory.generatePutInContainer(model, pawn, "RoseBoard", nextDestination.x, nextDestination.y);
        }
        if (typeOfMove.equals("P")) {
            if (model.getIdPlayer() == 0) {
                for (int i = 0; i < stage.getPlayer1MovementCards().length; i++) {
                    if (stage.getPlayer1MovementCards()[i] == null && stage.getPickCards().length > 0) {
                        stage.getPlayer1MovementCards()[i] = stage.getPickCards()[stage.getPickCards().length - 1];
                        stage.getPlayer1MovementCards()[i].flip();
                        stage.getMoovBluePot().addElement(stage.getPlayer1MovementCards()[i], i, 0);
                        setTempPickCards(stage);
                        if (stage.getPickCards().length > 0) {
                            actions.setDoEndOfTurn(true);
                            return actions;
                        }
                    }
                }
            } else {
                for (int i = 0; i < stage.getPlayer2MovementCards().length; i++) {
                    if (stage.getPlayer2MovementCards()[i] == null && stage.getPickCards().length > 0) {
                        stage.getPlayer2MovementCards()[i] = stage.getPickCards()[stage.getPickCards().length - 1];
                        stage.getPlayer2MovementCards()[i].flip();
                        stage.getMoovRedPot().addElement(stage.getPlayer2MovementCards()[i], i, 0);
                        setTempPickCards(stage);
                        if (stage.getPickCards().length > 0) {
                            actions.setDoEndOfTurn(true);
                            return actions;
                        }
                    }
                }
            }
        }
        if (!typeOfMove.equals("P")) {

            if (model.getIdPlayer() == 0) {
                stage.removeElement(stage.getPlayer1MovementCards()[choice]);
                if (typeOfMove.equals("H")) {
                    stage.removeElement(stage.getPlayer1HeroCards()[stage.getPlayer1HeroCards().length - 1]);
                    RosesCard[] tempHeroCards = stage.getPlayer1HeroCards();
                    RosesCard[] copyOfPickPotCards = new RosesCard[tempHeroCards.length - 1];
                    System.arraycopy(tempHeroCards, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
                    tempHeroCards = copyOfPickPotCards;
                    stage.setPlayer1HeroCards(tempHeroCards);
                }
                stage.getDiscardCards()[nbMovements] = stage.getPlayer1MovementCards()[choice];
                stage.getPlayer1MovementCards()[choice].flip();
                stage.getDiscardPot().addElement(stage.getPlayer1MovementCards()[choice], 0, 0);
                stage.getPlayer1MovementCards()[choice] = null;
                stage.getBoard().moveElement(crown, nextDestination.x, nextDestination.y);
            } else {
                stage.removeElement(stage.getPlayer2MovementCards()[choice]);
                if (typeOfMove.equals("H")) {
                    stage.removeElement(stage.getPlayer2HeroCards()[stage.getPlayer2HeroCards().length - 1]);
                    RosesCard[] tempHeroCards = stage.getPlayer2HeroCards();
                    RosesCard[] copyOfPickPotCards = new RosesCard[tempHeroCards.length - 1];
                    System.arraycopy(tempHeroCards, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
                    tempHeroCards = copyOfPickPotCards;
                    stage.setPlayer2HeroCards(tempHeroCards);
                }
                stage.getDiscardCards()[nbMovements] = stage.getPlayer2MovementCards()[choice];
                stage.getPlayer2MovementCards()[choice].flip();
                stage.getDiscardPot().addElement(stage.getPlayer2MovementCards()[choice], 0, 0);
                stage.getPlayer2MovementCards()[choice] = null;
                stage.getBoard().moveElement(crown, nextDestination.x, nextDestination.y);
            }
        }
        nbMovements++;
        stage.setNbMovements(nbMovements);
        setBackCardsInCardPot(stage);
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

    public void setTempPickCards(RosesStageModel stageModel) {
        RosesCard[] tempPickCard = stageModel.getPickCards();
        RosesCard[] copyOfPickPotCards = new RosesCard[tempPickCard.length - 1];
        System.arraycopy(tempPickCard, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
        tempPickCard = copyOfPickPotCards;
        stageModel.setPickCards(tempPickCard);
    }

    public void setBackCardsInCardPot(RosesStageModel stageModel) {
        if (stageModel.getPickCards().length == 0) {
            System.out.println("The pick pot is empty.");

            // Compter le nombre de cartes non nulles dans la défausse
            int newLength = 0;
            for (int n = 0; n < stageModel.getDiscardCards().length; n++) {
                if (stageModel.getDiscardCards()[n] != null) {
                    newLength++;
                }
            }

            RosesCard[] tempPickCard = new RosesCard[newLength];
            int index = 0;
            for (int p = 0; p < stageModel.getDiscardCards().length; p++) {
                if (stageModel.getDiscardCards()[p] != null) {
                    tempPickCard[index] = stageModel.getDiscardCards()[p];
                    index++;
                }
                stageModel.getDiscardPot().removeElement(stageModel.getDiscardCards()[p]);
            }

            RosesCard[] newEmptyDiscard = new RosesCard[26];
            stageModel.setPickCards(tempPickCard);
            stageModel.setDiscardCards(newEmptyDiscard);


            int nbMovements = 0;
            stageModel.setNbMovements(nbMovements);
        }
    }

    private void resetBoard(Point[][] board){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = null;
            }
        }
    }




}