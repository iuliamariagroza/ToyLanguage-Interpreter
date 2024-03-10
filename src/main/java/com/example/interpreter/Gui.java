package com.example.interpreter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Gui extends Application {
    @Override
    public void start(Stage stage1) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Gui.class.getResource("program-switch-controller.fxml"));
        Parent programListRoot = fxmlLoader.load();
        Scene programListScene = new Scene(programListRoot);
        ProgramsWindowController prgSwitchCtrl = fxmlLoader.getController();
        stage1.setTitle("Programs window");
        stage1.setScene(programListScene);
        stage1.show();


        FXMLLoader executorLoader = new FXMLLoader();
        executorLoader.setLocation(Gui.class.getResource("executor-controller.fxml"));
        Parent executorRoot = executorLoader.load();

        Scene executorScene = new Scene(executorRoot);
        ExecutionWindowController executorCtrl = executorLoader.getController();
        prgSwitchCtrl.setExecutionWindowController(executorCtrl);
        Stage stage2 = new Stage();
        stage2.setTitle("Execution window");
        stage2.setScene(executorScene);
        stage2.show();
    }

    public static void main(String[] args) {
        launch();
    }
}