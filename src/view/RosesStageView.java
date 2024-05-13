package view;

import boardifier.control.Logger;
import boardifier.model.GameStageModel;
import boardifier.model.TextElement;
import boardifier.view.ClassicBoardLook;
import boardifier.view.GameStageView;

import boardifier.view.TextLook;
import model.RosesStageModel;

/**
 * HoleStageView has to create all the looks for all game elements created by the HoleStageFactory.
 * The desired UI is the following:
 * player            ╔═╗    ┏━━━┓
 *    A   B   C      ║1║    ┃ 1 ┃
 *  ╔═══╦═══╦═══╗    ╠═╣    ┣━━━┫
 * 1║   ║   ║   ║    ║2║    ┃ 2 ┃
 *  ╠═══╬═══╬═══╣    ╠═╣    ┣━━━┫
 * 2║   ║   ║   ║    ║3║    ┃ 3 ┃
 *  ╠═══╬═══╬═══╣    ╠═╣    ┣━━━┫
 * 3║   ║   ║   ║    ║4║    ┃ 4 ┃
 *  ╚═══╩═══╩═══╝    ╚═╝    ┗━━━┛
 *
 * The UI constraints are :
 *   - the main board has double-segments border, coordinates, and cells of size 2x4
 *   - the black pot has double-segments border, will cells that resize to match what is within (or not)
 *   - the red pot has simple-segment border, and cells have a fixed size of 2x4
 *
 *   main board can be instanciated directly as a ClassicBoardLook.
 *   black pot could be instanciated directly as a TableLook, but in this demo a BlackPotLook subclass is created (in case of we want to modifiy the look in some way)
 *   for red pot, a subclass RedPotLook of GridLook is used, in order to override the method that render the borders.
 */

public class RosesStageView extends GameStageView {
    public RosesStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    @Override
    public void createLooks() {
        RosesStageModel model = (RosesStageModel)gameStageModel;

        /* Creating all the looks for all the game elements that are created by
           the HoleStageFactory.
           WARNING ! If there is a game element that has no associated look, the execution
           will fail.

           NB1: no need to put the pawn looks within the pot looks: boardifier handles that itself, provided
           pawn elements are put within their pot element (which is the case in HoleStageFactory)

           NB2: the real location of pawn looks within the pot looks is also managed directly by boardifier. This
           location takes the paddings & alignments into account.
           This is also why the initial location for the pawn elements is meaningless.
         */
        // create a TextLook for the text element
        addLook(new TextLook(model.getPlayerName()));
        addLook(new TextLook(model.getBluePawnsCounter()));
        addLook(new TextLook(model.getRedPawnsCounter()));
        addLook(new TextLook(model.getPick()));
        addLook(new TextLook(model.getDiscard()));

        // create a ClassicBoardLook (with borders and coordinates) for the main board.
        addLook(new ClassicBoardLook(2, 4, model.getBoard(), 1, 1, false));
        // create looks for both pots
        addLook(new RosesPawnPotLook(2, 2, model.getBluePot()));
        addLook(new RosesPawnPotLook(2, 2, model.getRedPot()));
        // create looks for all pawns
        for(int i=0;i<26;i++) {
            addLook(new RosesPawnLook(model.getBlackPawns()[i]));
            addLook(new RosesPawnLook(model.getRedPawns()[i]));
        }
        addLook(new RosesCardPotLook(3, 2, model.getPickPot()));
        addLook(new RosesCardPotLook(3, 2, model.getDiscardPot()));
        addLook(new RosesCardPotLook(2, 2, model.getRedHeroPot()));
        addLook(new RosesCardPotLook(2, 2, model.getBlueHeroPot()));
        addLook(new RosesCardPotLook(3, 2, model.getMoovRedPot()));
        addLook(new RosesCardPotLook(3, 2, model.getMoovBluePot()));

        addLook(new RosesPawnLook(model.getYellowPawns()[0]));



        for (int i = 0; i < 5; i++) {
            addLook(new RosesCardLook(model.getPlayer1MovementCards()[i]));
            addLook(new RosesCardLook(model.getPlayer2MovementCards()[i]));
        }

        for (int i = 0; i < model.getPickCards().length; i++) {
            addLook(new RosesCardLook(model.getPickCards()[i]));
        }
        addLook(new TextLook(model.getCardPickCounter()));







        /*
        Example using a main container (see HoleStageFactory),


        // create a look for the main container element, with flexible cell size and no borders
        // NB: no need to recreate spanning for the container look: they are deduced from the main container element
        ContainerLook mainLook = new ContainerLook(model.getMainContainer(), -1);
        // set padding to 1
        mainLook.setPadding(1);
        // center the looks within the cells
        mainLook.setVerticalAlignment(ContainerLook.ALIGN_MIDDLE);
        mainLook.setHorizontalAlignment(ContainerLook.ALIGN_CENTER);
        // add the look to the gane stage view.
        addLook(mainLook);
        */
        Logger.debug("finished creating game stage looks", this);
    }


}
