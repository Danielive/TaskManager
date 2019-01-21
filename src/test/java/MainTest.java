import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

/**
 * @author Daniel Chuev
 */
public class MainTest extends ApplicationTest {

    private static Parent rootNode;
    private static Stage baseStage;
    private static Button start ;
    private static Label countTasks;

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(getClass().getResource("/fxml/mainForm.fxml"));
        stage.getIcons().add(new Image("/img/main.png"));
        stage.setTitle("Task manager");
        stage.setScene(new Scene(mainNode));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();

        rootNode = stage.getScene().getRoot();
        baseStage = stage;
    }

    @Before
    public void setUp () throws Exception {
        start = from(rootNode).lookup("#start").query();
        countTasks = from(rootNode).lookup("#countTasks").query();
    }

    @After
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void testStateUI () {
        assertEquals("Task manager", baseStage.getTitle());
        assertEquals("285.0, 115.0", baseStage.getX() + ", " + baseStage.getY());
        assertEquals("Count task: 0", countTasks.getText());
        assertEquals("Start", start.getText());
        clickOn(start);
    }
}
