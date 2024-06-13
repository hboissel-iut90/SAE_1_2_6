package view;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.RosesPawn;

public class PawnLook extends ElementLook {
    private Circle circle;
    private int radius;

    public PawnLook(int radius, GameElement element) {
        super(element);

        this.radius = radius;
        render();
    }

    @Override
    public void onSelectionChange() {
        RosesPawn pawn = (RosesPawn)getElement();
        if (pawn.isSelected()) {
            circle.setStrokeWidth(4);
            circle.setStrokeMiterLimit(10);
            circle.setStrokeType(StrokeType.CENTERED);
            circle.setStroke(Color.valueOf("0x333333"));
        }
        else {
            circle.setStrokeWidth(2);
        }
    }

    @Override
    public void onFaceChange() {
    }

    protected void render() {
        RosesPawn pawn = (RosesPawn)element;
        circle = new Circle();
        Text crown = new Text(pawn.getText());
        crown.setFont(new Font(25));
        circle.setRadius(radius);
        circle.setStrokeWidth(2);
        circle.setStrokeType(StrokeType.CENTERED);
        circle.setStroke(Color.valueOf("0x333333"));
        if (pawn.getColor() == RosesPawn.PAWN_BLUE) {
            circle.setFill(Color.BLUE);
        } else if (pawn.getColor() == RosesPawn.PAWN_RED) {
            circle.setFill(Color.RED);
        } else {
            circle.setFill(Color.YELLOW);
            crown.setFill(Color.BLACK);

        }

        addShape(circle);
        Bounds btCrown = crown.getBoundsInLocal();
        crown.setX(-btCrown.getWidth()/2.1);
        crown.setY(crown.getBaselineOffset()/2.5);
        addShape(crown);
        // NB: text won't change so no need to put it as an attribute
        if (String.valueOf(pawn.getNumber()).equals("0") || String.valueOf(pawn.getNumber()).equals("null")) {
            return;
        }
        Text text = new Text(String.valueOf(pawn.getNumber()));
        text.setFont(new Font(24));
        if (pawn.getColor() == RosesPawn.PAWN_BLUE) {
            text.setFill(Color.valueOf("0xFFFFFF"));
        }
        else {
            text.setFill(Color.valueOf("0x000000"));
        }
        Bounds bt = text.getBoundsInLocal();
        text.setX(-bt.getWidth()/2);
        // since numbers are always above the baseline, relocate just using the part above baseline
        text.setY(text.getBaselineOffset()/2-4);
        addShape(text);
    }

//    public void updateColor() {
//        RosesPawn pawn = (RosesPawn)element;
//        if (pawn.getColor() == RosesPawn.PAWN_BLUE) {
//            circle.setFill(Color.BLUE);
//        } else if (pawn.getColor() == RosesPawn.PAWN_RED) {
//            circle.setFill(Color.RED);
//        } else {
//            circle.setFill(Color.YELLOW);
//        }
//    }
}
