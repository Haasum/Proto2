package LsysGUI;

import LsysRecursive.Grammatik;
import LsysRecursive.RecursiveLsys;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

import javax.swing.*;
import java.awt.*;
import java.awt.Event;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

import static LsysGUI.GUI.mainPanel;


public class Turtle extends JPanel {

    Primitives primitives;
    Grammatik grammatik;
    int growIs;
    JPanel testPanel;
    Graphics g2d;
    AffineTransform saveMatrix;
    AffineTransform newMatrix;
    AffineTransform rotateMatrix;
    AffineTransform oldMatrix;
    int testHeight;
    int startY;
    int rotateX;
    int middle;
    int height;
    int transY;
    Line branch;

    boolean startDraw;
    String drawThis = "A[+A][-A]"; //test string


    public Turtle(Grammatik grammatik, RecursiveLsys lsys) {
        middle = 300;
        this.grammatik = grammatik;
        startDraw = false;
        System.out.println(growIs);
        makeTestPanel();


    }

    private void makeTestPanel() {
        testPanel = new JPanel() {

            public void paintComponent(Graphics g) {


                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(Color.BLACK);

                oldMatrix =  AffineTransform.getTranslateInstance(50, 200);
               // g2d.setTransform(oldMatrix);

                testPanel.addPropertyChangeListener(new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        String propertyName = evt.getPropertyName();
                        switch (propertyName) {
                            case "push":
                                g2d.setTransform((AffineTransform) evt.getNewValue());
                                System.out.println("new matrix is " + newMatrix);
                                //setForeground((Color) evt.getNewValue());
                                break;
                            case "pop":
                                g2d.setTransform((AffineTransform) evt.getNewValue());
                            case "rotateLeft":
                                g2d.setTransform((AffineTransform) evt.getNewValue());
                               // g2d.rotate(Math.toRadians(10), Turtle.this.getX(), Turtle.this.getY());
                            case "rotateRight":
                                g2d.setTransform((AffineTransform) evt.getNewValue());
                               // g2d.rotate(Math.toRadians(20), Turtle.this.getX(), Turtle.this.getY());
                            default:
                        }
                    }
                });




                        //g2d.setTransform(newMatrix);
                        //g2d.getTransform(addPropertyChangeListener(newForm));
                        // g2d.getTransform(EventTarget(change));
                        //g2d.setTransform(saveMatrix);


                for (int i = 0; i < drawThis.length(); i++) {
                    char currentCheck = drawThis.charAt(i);


                    switch (currentCheck) {
                        case 'A':
                            growBranch(g2d);
                            break;
                        case '+':
                            rotateRight(g2d);
                            break;
                        case '-':
                            rotateLeft(g2d);
                            break;
                        case '[':
                            push(g2d, g2d.getTransform());
                            break;
                        case ']':
                            pop(g2d);
                            break;
                        default:
                            System.out.println("Char not in alphabet");
                            break;
                    }

                }

            }

            private void push(Graphics g2d, AffineTransform transform) {
                height ++;
                System.out.println("[");
                saveMatrix = transform;


                newMatrix = AffineTransform.getTranslateInstance(0, 0);
                newMatrix = AffineTransform.getRotateInstance(0,middle,rotateX);
                firePropertyChange("push", null, newMatrix);


            }

            private void pop(Graphics g2d) {
                height --;
                System.out.println("]");
                firePropertyChange("pop", null, saveMatrix);
            }

            private void rotateLeft(Graphics g2d) {
                rotateX = height * testHeight;
                System.out.println("-");
                newMatrix = AffineTransform.getRotateInstance(2.5,middle,rotateX);
                firePropertyChange("rotateLeft", null, newMatrix);
            }


            private void rotateRight(Graphics g2d) {
                rotateX = height * testHeight;
                newMatrix = AffineTransform.getRotateInstance(-2.5,middle,rotateX);
                firePropertyChange("rotateRight", null, newMatrix);
                System.out.println("+");
            }

            private void growBranch(Graphics g2d) {
                System.out.println("A");
                testHeight = 50;
                startY = this.getY();
                Line line = new Line(middle, startY, middle, testHeight); //prøver at lave den så den kan refereres til
                g2d.drawLine(middle, startY, middle, testHeight);

                Double Y = line.getEndY();
                System.out.println("Y is " + Y);
            }

        };

        testPanel.setSize(600, 600);
        testPanel.setVisible(true);
        testPanel.setBackground(new Color(99, 125, 150));
        testPanel.setLayout(null);
        mainPanel.add(testPanel);

        testHeight = 50;


    }



}




/**
 * TODO: NAJA!
 * klasse skal være abstractaction
 */


           /*
                SUPER PAINTCOMPONENT - DEN GAMLE - TEST
                super.paintComponent(g);


                Graphics2D g2d = (Graphics2D)g.create();
                g2d.setColor(Color.red);

                for (Line2D line : lines)
                {
                   // g2d.rotate(Math.toRadians(10), startX, startY);
                    //g2d.rotate(Math.toRadians(rotationen));

                    saveMatrix = g2d.getTransform(); //PUSH
                   // oldMatrix = AffineTransform.getTranslateInstance(0,0);

                     //skal rykkes til push metode
                    //pushIt += 60;
                    newMatrix = AffineTransform.getTranslateInstance(0,pushIt);
                    //set rotation
                    g2d.setTransform(newMatrix);

                   // g2d.translate(0,60); //ændrer nulpunkt
                    g2d.setColor(Color.black);
                    g2d.draw( line );

                    // g2d.setTransform(saveMatrix); //POP

                }


                g2d.dispose();

            }

        };

        testPanel.setSize(600, 600);
        testPanel.setVisible(true);
        testPanel.setBackground(new Color(99, 125, 150));
        testPanel.setLayout(null);
        mainPanel.add(testPanel);


    }


/* UDKOMMENTERET FOR TEST PURPOSE

    public void drawTurtle() {

        for (int i = 0; i < drawThis.length(); i++) {
            char currentCheck = drawThis.charAt(i);

            //DEN RIGTIGE:
            if (Character.isLetter(currentCheck)) {
                    branchHeight = 500;
                    System.out.println(growIs);
            }  //TODO: det skal være med denne det skal kører

            switch (currentCheck) {
                case 'A':
                    growBranch();
                    break;
                case '+':
                    rotateRight();
                    break;
                case '-':
                    rotateLeft();
                    break;
                case '[':
                    push();
                    break;
                case ']':
                    pop();
                    break;
                default:
                    System.out.println("Char not in alphabet");
                    break;
            }

        }
    }

    METODER DER STYRER HVAD DER SKER MED TRÆET:

    private void push() {
        pushIt += 60;
        repaint();
       //saveMatrix = new AffineTransform(0,60);
        System.out.println("[");
        //lines.add(new Line2D.Double(0, 0,0, 0));
    }

    private void pop() {
        //pushIt = 0;
        pop = true;
        System.out.println("]");
    }



    private void rotateLeft() {
        System.out.println("-");
    }

    private void rotateRight() {
        System.out.println("+");
    }

    private void growBranch() {
        System.out.println("A");
        branchHeight = 50; //Getbranch height
        startX = 300;

        lines.add(new Line2D.Double(startX, startY, startX, branchHeight));
    }
    UDKOMMENTERING SLUT
    */





 /*   public void setGrowIs() {
        growIs = grammatik.getGrow(c);
    } */
            //TODO: growIs skal hentes fra gramatik klassens alfabet.

