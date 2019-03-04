package com.example.diffuser.Interface.Button;

public class AromaOnCommand implements Command {
    private Aroma theAroma;
    public AromaOnCommand(Aroma theAlarm) { this.theAroma = theAlarm; }
    // Command 인터페이스의 execute 메서드
    public void execute() { theAroma.start(); }
}
