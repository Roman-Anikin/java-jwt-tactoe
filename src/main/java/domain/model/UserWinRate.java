package domain.model;

import java.util.UUID;

public class UserWinRate {

    private UUID userUuid;
    private String login;
    private double winRate;

    public UserWinRate(UUID userUuid, String login, double winRate) {
        this.userUuid = userUuid;
        this.login = login;
        this.winRate = winRate;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public double getWinRate() {
        return winRate;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }
}
