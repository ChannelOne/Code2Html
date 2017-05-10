package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Created by cdzos on 2017/5/7.
 */
public class AboutStage extends Stage {

    private Scene mainScene;
    private VBox mainPane;

    public static String AboutContent = "组员：\n" +
            "陈度中\n" +
            "曾敏洋\n" +
            "陈广鹏\n";

    public AboutStage() {
        super();

        mainPane = new VBox();
        mainPane.setAlignment(Pos.TOP_CENTER);
        mainPane.setPadding(new Insets(24, 20, 5, 20));

        Text title = new Text("源代码转换程序");
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(new Font(36));
        title.prefWidth(480);


        Text content = new Text(AboutContent);
        content.setFont(new Font(18));

        mainPane.getChildren().addAll(title, content);
        mainScene = new Scene(mainPane, 480, 420);

        // content.wrappingWidthProperty().bind(mainPane.widthProperty().divide(3));

        initModality(Modality.APPLICATION_MODAL);
        setTitle("关于");
        setScene(mainScene);
        setResizable(false);
    }

}