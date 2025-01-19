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
            return commandHistory.peek();
        }
        return null;
    }


    public boolean hasCommands() {
        return !commandHistory.isEmpty();
    }
}
