package com.example.interpreter.repository;

import com.example.interpreter.exceptions.ADTException;
import com.example.interpreter.model.programState.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramList();
    void setProgramStates(List<ProgramState> programStates);
    void addProgram(ProgramState program);
    void logPrgStateExec(ProgramState programState) throws IOException, ADTException;
    //void emptyLogFile() throws IOException;
}