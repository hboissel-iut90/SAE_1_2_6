package view;

import boardifier.view.RootPane;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.io.FileInputStream;

public class RosesRulesPane extends RootPane {
    private final double width;
    private final double height;
    private Button back;
    public RosesRulesPane(double width, double height) {
        super();
        this.width = width;
        this.height = height;
        resetToDefault();
    }

    public double Width() {
        return width;
    }

    public double Height() {
        return height;
    }

    @Override
    protected void createDefaultGroup() {
        double size = width*0.05;
        this.setPrefSize(width, height);
        Image image = new Image("file:src/menu_assets/velours.jpg");
        BackgroundImage backgroundImg = new BackgroundImage(image, null, null, null, null);

        this.setBackground(new Background(backgroundImg));
        ScrollPane rulesMainContainer = new ScrollPane();
        VBox rulesContainer = new VBox();
        Text title1 = new Text("1°/ Matériel et mise en place\n");
        Text content1 = new Text("Le plateau est constitué d'une grille 9x9, avec placé au centre un pion nommé \"roi\" dans la suite. "
                + "Il y a 52 pions bifaces, rouge d'un côté et bleu de l'autre, que l'on pose à côté du plateau. "
                + "On peut éventuellement préparer 26 jetons côté rouge visible et 26 jetons côté bleu, car il est très peu probable qu'un joueur en pose plus de 26 au cours d'une partie. "
                + "Chaque joueur reçoit également 4 cartes \"Héros\" qu'il va pouvoir jouer lors de ses tours de jeu. Une fois jouées, elles ne sont jamais remplacées et sont mises de côté. "
                + "Il y a également 24 cartes \"Déplacement\" qui constituent la pioche. Une carte Déplacement indique en bas un nombre en chiffre romain allant de 1 à 3, ainsi qu'une direction de déplacement pour le roi. "
                + "Vu qu'il y a 8 directions possibles, cela implique qu'une combinaison nombre+direction n'existe qu'en un seul exemplaire. "
                + "Au début de la partie les 24 cartes sont mélangées et chaque joueur en reçoit 5, face visible, qu'il doit placer avec le chiffre vers lui. La direction indiquée est donc celle vue par le joueur. "
                + "Les joueurs pourront éventuellement en repiocher au cours de la partie.\n");

        Text title2 = new Text("2°/ Régles\n"
                +"2.1°/ Déroulement du jeu\n");
        Text content2 = new Text("Le premier joueur est déterminé par tirage au sort ou bien par accord. "
                + "Chacun son tour, chaque joueur choisit une action parmi les 3 possibles, expliquée ci-dessous. "
                + "Cette alternance se poursuit jusqu'à la fin de partie, dont les conditions sont données en section 2.3.\n");

        Text title3 = new Text("2.2°/ Actions de jeu\n");
        Text content3 = new Text("Il existe 3 actions possibles :\n"
                + "piocher une carte déplacement,\n"
                + "jouer une carte déplacement seule,\n"
                + "jouer une carte déplacement plus une carte héros.\n");

        Text title4 = new Text("2.2.1°/ Piocher une carte déplacement\n");
        Text content4 = new Text("Cette action n'est possible que si le joueur a moins de 5 cartes déplacement devant lui. "
                + "Il pioche la carte du dessus de la pioche et la place face visible, orientée de façon à avoir le numéro devant lui. "
                + "Si la pioche est vide au moment de piocher, il suffit de mélanger la défausse pour refaire une pioche.\n");

        Text title5 = new Text("2.2.2°/ Jouer une carte déplacement seule.\n");
        Text content5 = new Text("Jouer une carte déplacement seule consiste à déplacer le roi dans la direction indiquée, du nombre de cases indiqué. "
                + "On ne peut pas jouer une carte :\n"
                + "dans une direction autre que celle indiquée par la carte,\n"
                + "avec moins de cases que le nombre indiqué par la carte,\n"
                + "si le déplacement fait sortir le roi du plateau,\n"
                + "si le déplacement amène le roi sur un pion (du joueur courant ou de l'adversaire).\n"
                + "A noter que le roi peut \"sauter\" par dessus des pions au cours de son déplacement. "
                + "Si la carte choisie par le joueur peut être jouée, alors :\n"
                + "il la pose dans une défausse à côté du plateau,\n"
                + "il pose un pion avec sa couleur visible sur la case d'arrivée,\n"
                + "il pose le roi sur le pion qu'il vient de poser dans la case d'arrivée.\n");

        Text title6 = new Text("2.2.3°/ Jouer une carte déplacement plus une carte héros\n");
        Text content6 = new Text("Jouer un binôme de cartes déplacement+héros consiste à déplacer le roi dans la direction indiquée, du nombre de cases indiqué, pour atterrir sur une case occupée par un pion de la couleur adverse. "
                + "On ne peut pas jouer un tel binôme :\n"
                + "dans une direction autre que celle indiquée par la carte déplacement,\n"
                + "avec moins de cases que le nombre indiqué par la carte déplacement,\n"
                + "si le déplacement n'amène pas le roi sur un pion de l'adversaire. "
                + "A noter que le roi peut \"sauter\" par dessus des pions au cours de son déplacement. "
                + "Si le binôme de cartes choisi par le joueur peut être joué, alors :\n"
                + "il pose la carte déplacement dans une défausse à côté du plateau, et la carte héros dans une autre défausse. Il ne pourra jamais récupérer cette carte héros.\n"
                + "il retourne le pion de la case d'arrivée afin de changer sa couleur visible.\n"
                + "il pose le roi sur le pion qu'il vient de retourner dans la case d'arrivée.\n");

        Text title7 = new Text("2.3°/ Fin de partie\n");
        Text content7 = new Text("La partie s'arrête dans 2 cas :\n"
                + "\t -dès qu'un joueur a 5 cartes déplacement posées devant lui et il ne peut en jouer aucune.\n"
                + "\t -dès qu'un joueur pose le dernier pion disponible.\n"
                + "Pour déterminer le gagnant, il faut déterminer :\n"
                + "\t -le nombre de zones \"d'adjacence\",\n"
                + "\t -compter pour chaque zone le nombre de pions dans cette zone, et mettre au carré ce nombre pour obtenir la valeur de la zone\n"
                + "\t -faire la somme des valeurs des zones.\n"
                + "Une zone d'adjacence est constituée par un ensemble de cases dont les jetons sont de la même couleur ET qui se touchent 2 à 2 par les côtés (pas par les coins).\n"
                + "Le joueur qui a la somme la plus élevée gagne la partie.\n"
                + "En cas d'égalité des sommes, le gagnant est celui qui a le plus de pions de sa couleur sur le plateau,\n"
                + "Si ce nombre de pions est égal, la partie est nulle et il n'y a pas de gagnants.\n");

        title1.setFont(new Font(size/3));
        content1.setFont(new Font(size/4));
        title2.setFont(new Font(size/3));
        content2.setFont(new Font(size/4));
        title3.setFont(new Font(size/3));
        content3.setFont(new Font(size/4));
        title4.setFont(new Font(size/3));
        content4.setFont(new Font(size/4));
        title5.setFont(new Font(size/3));
        content5.setFont(new Font(size/4));
        title6.setFont(new Font(size/3));
        content6.setFont(new Font(size/4));
        title7.setFont(new Font(size/3));
        content7.setFont(new Font(size/4));
        Text pageTitle = new Text("Rules");
        back = new Button("Back");
        try {
            File fontFile = new File("src/menu_assets/RoyalKing-Free.ttf");
            String absolutePath = fontFile.getAbsolutePath();
            pageTitle.setFont(Font.loadFont(new FileInputStream(absolutePath), size));
            back.setFont(Font.loadFont(new FileInputStream(absolutePath), size));
        } catch (Exception e) {
            pageTitle.setFont(new Font(size));
            back.setFont(new Font(size));
        }
        pageTitle.setX(width/4);
        pageTitle.setY(height/4.5);
        pageTitle.setFill(Color.WHITE);
        rulesContainer.getChildren().addAll(title1, content1, title2, content2, title3, content3, title4, content4, title5, content5, title6, content6, title7, content7);
        rulesMainContainer.setContent(rulesContainer);
        rulesMainContainer.setPrefSize(width/2, height/2);
        rulesMainContainer.setLayoutX(width/4);
        rulesMainContainer.setLayoutY(height/4);
        back.setPrefSize(width/5, height/10);
        back.setLayoutX(width/2.5);
        back.setLayoutY(height/1.3);
        group.getChildren().clear();
        group.getChildren().addAll(pageTitle, rulesMainContainer, back);
    }
    public Button getBackButton() {
        return back;
    }
}
