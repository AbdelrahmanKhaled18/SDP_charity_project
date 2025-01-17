package model.DesignPatterns.command;

import java.util.Stack;

public class DonationInvoker {

    Stack<ICommand>commandHistory = new Stack<>();

    public void executeCommand(ICommand command) {
        command.execute();
        commandHistory.push(command);
    }

    public void unExecuteLastCommand() {
        if (!commandHistory.isEmpty()) {
            ICommand lastCommand = commandHistory.pop();
            lastCommand.unExecute();
        }
    }

    public ICommand getLastCommand() {
        if (!commandHistory.isEmpty()) {
            return commandHistory.peek(); // Returns the last command without removing it
        }
        return null; // Returns null if the history is empty
    }

    // Check if there are any commands in the history
    public boolean hasCommands() {
        return !commandHistory.isEmpty();
    }
}
