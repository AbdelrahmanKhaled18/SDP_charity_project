package model.DesignPatterns.proxy;

import model.Staff;

public interface IProxy {
    public void manageTaskAssignment(Staff staff);
    public void modifyTask(Staff staff);
    public void deleteTask(Staff staff);
}