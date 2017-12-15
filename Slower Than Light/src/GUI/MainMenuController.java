package GUI;

import acq.IInjectableController;
import acq.ILogFacade;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sdown
 */
public class MainMenuController implements Initializable, IInjectableController
{
    
    private ILogFacade logFacade;
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       logFacade = GUI.getInstance().getLogFacade();
    }
    
    @Override
    public void injectStage (Stage _stage)
    {
        stage = _stage;
    }

    @FXML
    private void handleNewGameBtn(MouseEvent event) throws IOException
    {
        logFacade.newGame();
        changeScene("GameGraphics.fxml");
    }

    @FXML
    private void handleLoadGameBtn(MouseEvent event) throws IOException
    {
        logFacade.loadGame();
        changeScene("GameGraphics.fxml");
    }

    @FXML
    private void handleHighscoreBtn(MouseEvent event) throws IOException
    {
        changeScene("Highscore.fxml");
    }

    @FXML
    private void handleQuitBtn(MouseEvent event) throws IOException
    {
        System.exit(0);
    }
    
    private void changeScene(String sceneName)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            IInjectableController controller = loader.getController();
            controller.injectStage(stage);

            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            System.out.println("Game: Tried to change scene to " + sceneName);
            e.printStackTrace();
        }
    }
    
}
