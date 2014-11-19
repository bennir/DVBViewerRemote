package de.bennir.dvbviewerremote.model;

public class DVBCommand {
    public int getCommand() {
        return Command;
    }

    public void setCommand(int command) {
        Command = command;
    }

    private int Command = 0;

    public DVBCommand(int command) {
        setCommand(command);
    }
}
