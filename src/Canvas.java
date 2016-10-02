import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

    private Cell[][] gameField;

    Canvas(Cell[][] gameField) {
        this.gameField = gameField;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                gameField[i][j].paint(g, i, j);
            }

        }
    }
}
