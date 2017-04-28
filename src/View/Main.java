package View;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        File srcPathFile = new File("file");//默认保存路径
        ArrayList<File> Code2HtmlFile = new ArrayList<>();//存要转换目标项目
        ObservableList<RightTable> dataRight = FXCollections.observableArrayList();//存选定好的项目
        ObservableList<MyFile> dataMiddle = FXCollections.observableArrayList();//存文件中的项目
//        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        primaryStage.setTitle("源代码自动转换程序");
        StackPane backpane = new StackPane();
//        backpane.setStyle("-fx-background-color:cyan");

        BorderPane topPane = new BorderPane();
//        HBox topPane = new HBox();
//        MenuBar menuBar = new MenuBar();
//        Menu menuFile = new Menu("File");
//        MenuItem menuItemAbout = new MenuItem("About");
//        MenuItem menuItemExit = new MenuItem("Exit");
        TextField currentPath = new TextField();
//        currentPath.setPrefHeight(1);
//        Label labelCurrentPath = new Label("                  当前目录:                       ");
        Button labelCurrentPath = new Button("                当前目录:                     ");
        labelCurrentPath.setStyle("-fx-background-color:null");
//        labelCurrentPath.setContentDisplay(ContentDisplay.CENTER);
        Label _null = new Label("                                                              ");
//        currentPath.setDisable(true);

//        //关闭程序
//        menuItemExit.setOnAction((ActionEvent t) -> {
//            primaryStage.close();
//        });
//
//        menuFile.getItems().addAll(menuItemAbout, menuItemExit);
//        Menu menuHelp = new Menu("Help");
//
//        menuBar.getMenus().addAll(menuFile, menuHelp);
//        topPane.getChildren().addAll(labelCurrentPath , currentPath , _null);
        topPane.setLeft(labelCurrentPath);
////        topPane.setTop(menuBar);
        topPane.setCenter(currentPath);
        topPane.setRight(_null);


        BorderPane borderPane = new BorderPane();

        TableView<MyFile> table = new TableView<>();
        TableColumn tableName = new TableColumn("文件名");
        tableName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        TableColumn tableDate = new TableColumn("最后修改日期");
        tableDate.setCellValueFactory(new PropertyValueFactory<>("lastModified"));
        TableColumn tableLength = new TableColumn("文件大小");
        tableLength.setCellValueFactory(new PropertyValueFactory<>("length"));
        TableColumn tableType = new TableColumn("类型");
        tableType.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn tableBottom = new TableColumn("选择");
        tableBottom.setCellValueFactory(new PropertyValueFactory<>("btChoose"));
        tableName.prefWidthProperty().bind(borderPane.widthProperty().divide(8.5));
        tableDate.prefWidthProperty().bind(borderPane.widthProperty().divide(8.3));
        tableLength.prefWidthProperty().bind(borderPane.widthProperty().divide(8.5));
        tableType.prefWidthProperty().bind(borderPane.widthProperty().divide(8.5));
        tableBottom.prefWidthProperty().bind(borderPane.widthProperty().divide(8));

//        tableName.setPrefWidth(195);
//        tableDate.setPrefWidth(195);
//        tableLength.setPrefWidth(150);
//        tableType.setPrefWidth(150);
//        tableBottom.setPrefWidth(91);


        table.getColumns().addAll(tableName, tableDate, tableLength, tableType, tableBottom);

        StackPane rightPane = new StackPane();
        TableView<MyFile> rightTable = new TableView<>();
        TableColumn chooseFile = new TableColumn("已选文件");
        chooseFile.setPrefWidth(195);
        chooseFile.setCellValueFactory(new PropertyValueFactory<>("choosedFile"));
        TableColumn isCancel = new TableColumn("取消");
        isCancel.setCellValueFactory(new PropertyValueFactory<>("btCancel"));
        rightTable.getColumns().addAll(chooseFile, isCancel);
        rightPane.getChildren().add(rightTable);

        GridPane bottomPane = new GridPane();
//        bottomPane.setGridLinesVisible(true);
        bottomPane.setPadding(new Insets(5, 0, 5, 250));
        bottomPane.setHgap(25);
        bottomPane.setVgap(10);
        Label filePath = new Label("保存路径 :");
        TextField showFilePath = new TextField();
        showFilePath.setText(srcPathFile.getAbsolutePath());
        showFilePath.setAlignment(Pos.BASELINE_LEFT);
        showFilePath.prefWidthProperty().bind(borderPane.widthProperty().divide(1.85));
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Button btChoosePath = new Button("  保存在…  ");
        Button btStartCode = new Button("  开始转换  ");
        btChoosePath.setOnAction(e -> {
            File file = directoryChooser.showDialog(primaryStage);
            if (file != null) {
                showFilePath.setText(file.getAbsolutePath());
                Actions.codeAction(btStartCode, Code2HtmlFile, showFilePath.getText());//设定开始转换按钮的功能
            }
        });

        Label fileKind = new Label("类型 :");
        Actions.codeAction(btStartCode, Code2HtmlFile, showFilePath.getText());//设定开始转换按钮的功能
//        btStartCode.setDefaultButton(true);
        ComboBox<String> showFileKind = new ComboBox<>();
        showFileKind.prefWidthProperty().bind(borderPane.widthProperty().divide(1.85));
        showFileKind.getItems().addAll("all", ".h", ".c", ".java");
        showFileKind.setValue("all");
        bottomPane.add(filePath, 0, 0);
        bottomPane.add(showFilePath, 1, 0);
        bottomPane.add(btChoosePath, 2, 0);
        bottomPane.add(fileKind, 0, 1);
        bottomPane.add(showFileKind, 1, 1);
        bottomPane.add(btStartCode, 2, 1);


        Button computer = new Button(new File(System.getenv("COMPUTERNAME")).getName());
        computer.setStyle("-fx-background-color:null");
        computer.setOnMouseEntered(e -> {
            computer.setStyle("-fx-background-color:lightblue");
        });
        computer.setOnMouseExited(e -> {
            computer.setStyle("-fx-background-color:null");
        });
        TreeItem<Button> rootItem = new TreeItem<>(computer, new ImageView(new Image("file:///../image/MyComputer.png")));
        computer.setOnAction(e -> {
            rootItem.setExpanded(true);
        });

        for (File file : File.listRoots()) {
            Button button = new Button(file.getPath() + "                              ", new ImageView(new Image("file:///../image/Disk2.png")));
            button.setStyle("-fx-background-color:null");
            button.setOnMouseEntered(e -> {
                button.setStyle("-fx-background-color:lightblue");
            });
            button.setOnMouseExited(e -> {
                button.setStyle("-fx-background-color:null");
            });
            TreeItem<Button> root = new TreeItem<>(button);
            rootItem.getChildren().add(root);
            //下面函数递归调用，生成右边的目录树，把右边的按钮设定功能，在中间显示项目
            Actions.clickAction(button, root, file, table, Code2HtmlFile, showFileKind, currentPath, rightTable, dataRight, dataMiddle);
        }
        rootItem.setExpanded(true);

        TreeView<Button> tree = new TreeView<>(rootItem);

//        WebView browser = new WebView();
//        WebEngine webEngine = browser.getEngine();
//        webEngine.load("http://www.baidu.com");

        borderPane.setLeft(tree);
//        borderPane.setTop(labelCurrentPath);
        borderPane.setTop(topPane);
        borderPane.setCenter(table);
        borderPane.setRight(rightTable);
        borderPane.setBottom(bottomPane);

        backpane.getChildren().add(borderPane);

        Scene scene = new Scene(backpane, 1280, 960);
        primaryStage.setScene(scene);
        scene.setFill(null);

        //窗口图标
        primaryStage.getIcons().add(new Image("file:///../image/2.jpg"));

        primaryStage.show();
    }

}
