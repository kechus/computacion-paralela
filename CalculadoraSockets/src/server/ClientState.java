package server;

enum UserActions{
    FIRST_NUMBER,
    OPERATOR,
    SECOND_NUMBER,
}
public class ClientState {
    UserActions currentAction = UserActions.FIRST_NUMBER;
    double a = 0;
    String operator = "";
}

