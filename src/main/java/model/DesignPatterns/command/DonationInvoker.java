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
        } else {
            System.out.println("No commands to undo.");
        }
    }
}
