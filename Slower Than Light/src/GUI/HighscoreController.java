/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import acq.IInjectableController;
import acq.ILogFacade;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mortenskovgaard
 */
public class HighscoreController implements Initializable, IInjectableController 
{

    

    ILogFacade logFacade;
    Stage stage;
    @FXML
    private Button BackToMainMenuBtn;
    

    @FXML
    private ListView<String> nameCol;
    private ObservableList<String> nameList;
    @FXML
    private ListView<Integer> scoreCol;
        private ObservableList<Integer> scoreList;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        logFacade = GUI.getInstance().getILogFacade();
        
        Map<String, Integer> highscore = logFacade.getHighScore();
        
        
        List<String>nameStrings = new ArrayList<>(highscore.keySet());
        nameList = FXCollections.observableList(nameStrings);
        
        List<Integer>scoreIntegers = new ArrayList<>(highscore.values());
        scoreList = FXCollections.observableList(scoreIntegers);
        
        scoreCol.setItems(scoreList);
        nameCol.setItems(nameList);
    }    

    @Override
    public void injectStage(Stage _stage) 
    {
        stage = _stage;
    }

    @FXML
    private void handleBackToMainMenuBtn(MouseEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        
        IInjectableController controller = loader.getController();
        controller.injectStage(stage);
        
        stage.setScene(scene);
        stage.show();
    }
    /*
    private TableView<Map>highscoreTableView()
            {
                LinkedHashMap<String, Integer>highscoreMap = new LinkedHashMap<>();
        
                highscoreMap.putAll(GUI.getInstance().);
        
        tableViewHighscore = FXCollections.observableMap((Map<String, Integer>) highscoreMap);
        
        highscoreTw.setItems(tableViewHighscore);
            }
*/
}
