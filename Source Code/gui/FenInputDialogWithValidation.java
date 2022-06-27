package ch06.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class FenInputDialogWithValidation extends JFrame {
   JTextField tfDisplay;  // to display the number entered
 
   /** Constructor to setup the GUI components */
   public FenInputDialogWithValidation() {
      Container cp = getContentPane();
      cp.setLayout(new FlowLayout());
 
      tfDisplay = new JTextField(10);
      tfDisplay.setEditable(false);
      cp.add(tfDisplay);
 
      JButton btn = new JButton("Input");
      cp.add(btn);
      btn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            boolean validInput = false;  // for input validation
            int numberIn;
            String inputStr = JOptionPane.showInputDialog("Enter a number [1-9]: ");
            do {
               try {
                  numberIn = Integer.parseInt(inputStr);
               } catch (NumberFormatException ex) {
                  numberIn = -1;  // input triggered NumberFormatException, set to invalid
               }
               if (numberIn < 1 || numberIn > 9) {
                  inputStr = JOptionPane.showInputDialog("Invalid numner! Enter a number [1-9]: ");
               } else {
                  JOptionPane.showMessageDialog(null, "You have entered " + numberIn);
                  validInput = true;
               }
            } while (!validInput); // repeat if input is not valid
            tfDisplay.setText(numberIn + "");
         }
      });
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(500, 100);
      setTitle("Test Input Dialog");
      setVisible(true);
   }
 
   /** The "main" entry method */
//   public static void main(String[] args) {
//      // Run the GUI code on the Event-Dispatching Thread for thread safety
//      javax.swing.SwingUtilities.invokeLater(new Runnable() {
//         public void run() {
//            new InputDialogWithValidation(); // Let the constructor do the job
//         }
//      });
//   }
}