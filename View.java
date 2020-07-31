package Root;
/**
 * View uses Swing Library and create a window
 *
 * @author Ceilvia
 * @version 0.1
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class View
{
    // instance variables - replace the example below with your own
    private static void createGUI(){
        JFrame frame = new JFrame("PixelDraw");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton upload = new JButton("Upload Image");
        frame.add(upload, BorderLayout.NORTH);
        JLabel label = new JLabel("PD");
        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);

        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser imgChooser = new JFileChooser();
                int returnVal = imgChooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION){
                    File fileToRead = imgChooser.getSelectedFile();
                    try {
                        BufferedImage uImage = ImageIO.read(fileToRead);
                        BufferedImage pixelated = ImageTools.pixelate(uImage, 5);
                        JLabel picLabel = new JLabel(new ImageIcon(pixelated));
                        frame.add(picLabel);
                    } catch (IOException ioException) {
                        System.out.println("Invalid File, please select an image");
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }


    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                createGUI();
            }
        });
    }
}


