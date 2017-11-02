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
import sudukox.Game.BoardV_02;

/**
 *
 * @author Kolbe
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private GridPane pane;
    
    private BoardV_02 test;
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
//        System.out.println("Column " + test.getColumn(i));
//        System.out.println("Row "+test.getRow(i));
//        System.out.println("Region " +test.getRegion(i));
        i++;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        i=0;
         test = new BoardV_02();
         test.setupGame();
         test.BTSearch();
    }    
    
}
