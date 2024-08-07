package Listeners;

import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {

    private int x;
    private int y;
    private boolean leftPressed = false;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        leftPressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        leftPressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }
}
