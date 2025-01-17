package model.DesignPatterns.strategy;

import model.Person;

public interface ILogin {
    Person login();

    boolean validateUserInput();
}
