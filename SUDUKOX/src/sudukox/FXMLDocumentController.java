/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudukox;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import sudukox.Game.BoardV_03;
import sudukox.Game.Visual;

/**
 *
 * @author Kolbe
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private GridPane pane;
    
    private BoardV_03 test;
    private Visual testViz;
    int i;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @FXML
    private void testButton(ActionEvent event){
//        test.setValueTest(i);
        System.out.println(i);      
//        test.BTSearch();

//        System.out.println("Column " + test.getColumn(i));
//        System.out.println("Row "+test.getRow(i));
//        System.out.println("Region " +test.getRegion(i));
        i++;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        
         test = new BoardV_03();
         testViz = new Visual(pane,test);
         //test.setupGame(test.testState0());
         //test.BTSearch();
         test.setupGame(test.testState0());
         test.BTSearch();
//         test.setViz(testViz);
    }   
}
