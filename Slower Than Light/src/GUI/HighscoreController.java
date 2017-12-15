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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mortenskovgaard
 */
public class HighscoreController implements Initializable, IInjectableController 
{
    private ILogFacade logFacade;
    private Stage stage;
    
    @FXML
    private ListView<String> nameCol;
    private ObservableList<String> nameList;
    @FXML
    private ListView<Integer> scoreCol;
    private ObservableList<Integer> scoreList;

    
    /**
     * In this method we initialize the scene. 
     * We make a local variable of the logfacade.
     * The we use that to get our highscore method. We the put our data that we 
     * get from that
     * method and add's it to a new arraylist. We make 2 different arraylist 
     * with the names in one and the score in the other.
     * Last we a observablelist to the 2 arraylists and show them on the scene.
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        logFacade = GUI.getInstance().getLogFacade();
        
        Map<String, Integer> highscore = logFacade.getHighscore();
        
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
/**
 * In this method we handle what happens when the user presses the back button in the highscore scene.
 * We first make at new FXML loader and defines what the loader has to load when the button is pressed. 
 * @param event
 * @throws IOException 
 */
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
}
