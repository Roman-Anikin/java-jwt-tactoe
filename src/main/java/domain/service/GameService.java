package domain.service;

import domain.model.CurrentGame;
import domain.model.UserWinRate;

import java.util.List;
import java.util.UUID;

public interface GameService {

    int X = 1;
    int O = 0;
    int EMPTY = -1;
    int WIN_POINTS = 10;
    UUID AI_UUID = UUID.randomUUID();

    CurrentGame getMove(CurrentGame currentGame);

    CurrentGame validateGame(CurrentGame currentGame);

    void checkGameOver(CurrentGame game);

    UUID createGame(String mode);

    List<CurrentGame> getWaitingGames();

    CurrentGame joinGame(UUID gameUuid, String badge);

    CurrentGame getByUuid(UUID uuid);

    List<CurrentGame> getFinishedGamesByUser();

    List<UserWinRate> getUsersWinRate(int limit);
}
