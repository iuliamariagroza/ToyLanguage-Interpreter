package com.example.interpreter;

import com.example.interpreter.controller.Controller;
import com.example.interpreter.exceptions.ADTException;
import com.example.interpreter.exceptions.ExpressionEvaluationException;
import com.example.interpreter.exceptions.StatementExecutionException;
import com.example.interpreter.model.expressions.*;
import com.example.interpreter.model.programState.ProgramState;
import com.example.interpreter.model.statements.*;
import com.example.interpreter.model.types.BoolType;
import com.example.interpreter.model.types.IntType;
import com.example.interpreter.model.types.RefType;
import com.example.interpreter.model.types.StringType;
import com.example.interpreter.model.ADTS.*;
import com.example.interpreter.model.values.BoolValue;
import com.example.interpreter.model.values.IntValue;
import com.example.interpreter.model.values.StringValue;
import com.example.interpreter.repository.IRepository;
import com.example.interpreter.repository.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgramsWindowController {
    private ExecutionWindowController ExecutionWindowController;
    List<IStmt> programsList;

    public void setExecutionWindowController(ExecutionWindowController exeCtrl)
    {
        this.ExecutionWindowController = exeCtrl;
    }
    @FXML
    private ListView<IStmt> programsListView;
    @FXML
    public void initialize()
    {
        programsListView.setItems(this.getAllPrograms());
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void displayProgram(ActionEvent event)
    {
        IStmt selectedStatement = programsListView.getSelectionModel().getSelectedItem();

        if(selectedStatement == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No program was selected!");
            alert.showAndWait();
        }
        else
        {
            int id = programsListView.getSelectionModel().getSelectedIndex();
            try{
                selectedStatement.typeCheck(new MyDictionary<>());

                ProgramState prg = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), selectedStatement);
                IRepository repo = new Repository(prg, "logFile" + (id+1) + ".txt");
                Controller ctrl = new Controller(repo);

                ExecutionWindowController.setController(ctrl);
            }catch(IOException | ExpressionEvaluationException | ADTException | StatementExecutionException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

        }
    }

    @FXML
    private ObservableList<IStmt> getAllPrograms()
    {
        programsList = new ArrayList<>();

        IStmt prg1 = new CompStmt(new VarDecl("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        IStmt prg2 = new CompStmt(new VarDecl("a",new IntType()),
                new CompStmt(new VarDecl("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        IStmt prg3 = new CompStmt(new VarDecl("a", new BoolType()),
                new CompStmt(new VarDecl("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(
                                        new VarExp("a"),
                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));

        IStmt prg4 = new CompStmt(
                new VarDecl("a",new IntType()),
                new CompStmt(
                        new VarDecl("b", new IntType()),
                        new CompStmt(
                                new VarDecl("bool1", new BoolType()),
                                new CompStmt(
                                        new VarDecl("bool2", new BoolType()),
                                        new CompStmt(
                                                new AssignStmt("a", new ValueExp(new IntValue(100))),
                                                new CompStmt(
                                                        new AssignStmt("b", new ValueExp(new IntValue(100))),
                                                        new CompStmt(
                                                                new AssignStmt("bool1", new ValueExp(new BoolValue(true))),
                                                                new CompStmt(
                                                                        new AssignStmt("bool2", new ValueExp(new BoolValue(false))),
                                                                        new CompStmt(
                                                                                new IfStmt(
                                                                                        new LogicExp("&&", new VarExp("bool1"), new VarExp("bool2")),
                                                                                        new AssignStmt("a", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(77)))),
                                                                                        new AssignStmt("b", new ArithExp('-', new VarExp("b"), new ValueExp(new IntValue(77))))),
                                                                                new CompStmt(
                                                                                        new PrintStmt(new VarExp("a")),
                                                                                        new PrintStmt(new VarExp("b"))))))))))));

        IStmt prg5 = new CompStmt(new VarDecl("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("C:\\Users\\Sergiu\\Desktop\\SEM3\\metode_avansate_de_programare\\A7\\A7_gui\\src\\main\\java\\com\\example\\a7_gui\\test.in"))),
                        new CompStmt(new OpenReadFile(new VarExp("varf")),
                                new CompStmt(new VarDecl("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseReadFile(new VarExp("varf"))))))))));

        IStmt prg6 = new CompStmt(new VarDecl("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));

        IStmt prg7 = new CompStmt(new VarDecl("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDecl("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+',new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));

        IStmt prg8 = new CompStmt(new VarDecl("v",new IntType()),
                new CompStmt(new VarDecl("a",new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new NewStmt("a",new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(
                                                new CompStmt(new WriteHeap("a", new ValueExp(new IntValue(30))),
                                                        new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(32))),
                                                                new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new ReadHeapExp(new VarExp("a"))))))), new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));

        IStmt prg9 = new CompStmt(new VarDecl("counter", new IntType()),
                new WhileStmt(
                        new RelationalExp("<", new VarExp("counter"), new ValueExp(new IntValue(10))),
                        new CompStmt(new ForkStmt(new ForkStmt(new CompStmt(new VarDecl("a", new RefType(new IntType())),
                                new CompStmt(new NewStmt("a", new VarExp("counter")),
                                        new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                new AssignStmt("counter", new ArithExp('+', new VarExp("counter"), new ValueExp(new IntValue(1)))))));

        //switch
        IStmt prg10 = new CompStmt(new VarDecl("a", new IntType()),
                new CompStmt(new VarDecl("b", new IntType()),
                        new CompStmt(new VarDecl("c", new IntType()),
                                new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(1))),
                                        new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(2))),
                                                new CompStmt(new AssignStmt("c", new ValueExp(new IntValue(5))),
                                                        new CompStmt(new SwitchStmt(
                                                                new ArithExp('*', new VarExp("a"), new ValueExp(new IntValue(10))),
                                                                new ArithExp('*', new VarExp("b"), new VarExp("c")),
                                                                new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))),
                                                                new ValueExp(new IntValue(10)),
                                                                new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))), new PrintStmt(new ValueExp(new IntValue(200)))),
                                                                new PrintStmt(new ValueExp(new IntValue(300)))),
                                                                new PrintStmt(new ValueExp(new IntValue(300))))))))));


        programsList.add(prg1);
        programsList.add(prg2);
        programsList.add(prg3);
        programsList.add(prg4);
        programsList.add(prg5);
        programsList.add(prg6);
        programsList.add(prg7);
        programsList.add(prg8);
        programsList.add(prg9);
        programsList.add(prg10);

        return FXCollections.observableArrayList(programsList);
    }
}