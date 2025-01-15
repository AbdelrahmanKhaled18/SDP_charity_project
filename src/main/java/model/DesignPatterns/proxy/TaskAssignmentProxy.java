package model.DesignPatterns.proxy;

import model.Staff;
import model.Task;

import java.util.List;

public class TaskAssignmentProxy implements IProxy{

    private Task task;
    private List<String> allowedPositions;
    public TaskAssignmentProxy(Task task, List<String> allowedPositions) {
        this.task = task;
        this.allowedPositions = allowedPositions;
    }

    @Override
    public void manageTaskAssignment(Staff staff) {
        if (!allowedPositions.contains(staff.getPosition())) {
            //Show a dialog box instead
            System.out.println("Access denied: " + staff.getPosition() + " cannot assign task." + task.getName());
        }
    }

    @Override
    public void modifyTask(Staff staff) {
        if (!allowedPositions.contains(staff.getPosition())) {
            //Show a dialog box instead
            System.out.println("Access denied: " + staff.getPosition() + " cannot modify tasks." + task.getName());
        }
    }

    @Override
    public void deleteTask(Staff staff) {
        if (!allowedPositions.contains(staff.getPosition())) {
            //Show a dialog box instead
            System.out.println("Access denied: " + staff.getPosition() + " cannot delete tasks." + task.getName());
        }
    }
}
