package domain.service;

import datasource.repository.GameRepository;
import domain.model.CurrentGame;
import domain.model.UserWinRate;
import exceptions.game.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import security.JwtAuthContextService;

import java.util.List;
import java.util.UUID;

import static domain.service.GameMode.AI;
import static domain.service.GameMode.PLAYER;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository repository;
    private final JwtAuthContextService jwtAuthContextService;

    public GameServiceImpl(JwtAuthContextService jwtAuthContextService) {
        this.jwtAuthContextService = jwtAuthContextService;
    }

    @Override
    public CurrentGame getMove(CurrentGame curGame) {
        CurrentGame prevGame = validateGame(curGame);
        UUID userUuid = jwtAuthContextService.getUserUuidByAccessToken();
        if (!userUuid.equals(prevGame.getActivePlayer()) || prevGame.isDraw() || prevGame.getWinner() != null) {
            return prevGame;
        }
        checkGameOver(curGame);
        if (!curGame.isDraw() && curGame.getWinner() == null && curGame.getOPlayer().equals(AI_UUID)) {
            generateOMove(curGame.getGameField().getField());
            checkGameOver(curGame);
        }
        if (!curGame.getOPlayer().equals(AI_UUID)) {
            curGame.setActivePlayer(userUuid.equals(curGame.getXPlayer()) ?
                    curGame.getOPlayer() : curGame.getXPlayer());
        }
        return repository.save(curGame);
    }

    @Override
    public CurrentGame validateGame(CurrentGame curGame) {
        CurrentGame prevGame = getByUuid(curGame.getUuid());
        mapGames(prevGame, curGame);
        validateField(prevGame, curGame);
        return prevGame;
    }

    @Override
    public void checkGameOver(CurrentGame game) {
        int[][] field = game.getGameField().getField();
        int res = evaluateMove(field, 0);
        game.setDraw(isMovesNotLeft(field) && res == 0);
        game.setWinner(res == WIN_POINTS ? game.getXPlayer() : res == -WIN_POINTS ? game.getOPlayer() : null);
    }

    @Override
    public UUID createGame(String mode) {
        if (!mode.equalsIgnoreCase(AI.name()) && !mode.equalsIgnoreCase(PLAYER.name())) {
            throw new InvalidGameModeException("Invalid game mode: " + mode);
        }
        CurrentGame game = new CurrentGame();
        UUID userUuid = jwtAuthContextService.getUserUuidByAccessToken();
        if (mode.equalsIgnoreCase(AI.name())) {
            game.setXPlayer(userUuid);
            game.setOPlayer(AI_UUID);
            game.setActivePlayer(game.getXPlayer());
        } else {
            game.setWaiting(true);
        }
        return repository.save(game).getUuid();
    }

    @Override
    public List<CurrentGame> getWaitingGames() {
        return repository.findByIsWaitingTrue();
    }

    @Override
    public CurrentGame joinGame(UUID gameUuid, String badge) {
        if (!badge.equalsIgnoreCase(GameBadge.X.name()) && !badge.equalsIgnoreCase(GameBadge.O.name())) {
            throw new InvalidBadgeException("Invalid badge. Choose X or O");
        }
        CurrentGame game = getByUuid(gameUuid);
        UUID userUuid = jwtAuthContextService.getUserUuidByAccessToken();
        if (badge.equalsIgnoreCase(GameBadge.X.name())) {
            if (game.getXPlayer() != null || userUuid.equals(game.getOPlayer())) {
                throw new BadgeAlreadyTakenException("Badge: " + badge.toUpperCase() + " already taken");
            }
            game.setXPlayer(userUuid);
        } else {
            if (game.getOPlayer() != null || userUuid.equals(game.getXPlayer())) {
                throw new BadgeAlreadyTakenException("Badge: " + badge.toUpperCase() + " already taken");
            }
            game.setOPlayer(userUuid);
        }
        if (game.getXPlayer() != null && game.getOPlayer() != null) {
            game.setWaiting(false);
            game.setActivePlayer(game.getXPlayer());
        }
        return repository.save(game);
    }

    @Override
    public CurrentGame getByUuid(UUID uuid) {
        return repository.findById(uuid)
                .orElseThrow(() -> new GameNotFoundException("Game with uuid: " + uuid + " not found"));
    }

    @Override
    public List<CurrentGame> getFinishedGamesByUser() {
        return repository.findByUuid(jwtAuthContextService.getUserUuidByAccessToken());
    }

    @Override
    public List<UserWinRate> getUsersWinRate(int limit) {
        return repository.findUserWinRateStatsBy(limit);
    }

    private void generateOMove(int[][] field) {
        int bestValue = Integer.MIN_VALUE, row = -1, col = -1;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == EMPTY) {
                    field[i][j] = X;
                    int moveValue = minimax(field, 0, false);
                    field[i][j] = EMPTY;
                    if (moveValue > bestValue) {
                        row = i;
                        col = j;
                        bestValue = moveValue;
                    }
                }
            }
        }
        field[row][col] = O;
    }

    private int minimax(int[][] field, int depth, Boolean isMax) {
        int score = evaluateMove(field, depth);
        if (score != 0 || isMovesNotLeft(field)) {
            return score;
        }
        int bestValue;
        if (isMax) {
            bestValue = Integer.MIN_VALUE;
            for (int row = 0; row < field.length; row++) {
                for (int col = 0; col < field[row].length; col++) {
                    if (field[row][col] == EMPTY) {
                        field[row][col] = X;
                        bestValue = Math.max(bestValue, minimax(field, depth - 1, false));
                        field[row][col] = EMPTY;
                    }
                }
            }
        } else {
            bestValue = Integer.MAX_VALUE;
            for (int row = 0; row < field.length; row++) {
                for (int col = 0; col < field[row].length; col++) {
                    if (field[row][col] == EMPTY) {
                        field[row][col] = O;
                        bestValue = Math.min(bestValue, minimax(field, depth - 1, true));
                        field[row][col] = EMPTY;
                    }
                }
            }
        }
        return bestValue;
    }

    private int evaluateMove(int[][] field, int depth) {
        for (int row = 0; row < field.length; row++) {
            if (field[row][0] == field[row][1] && field[row][1] == field[row][2]) {
                if (field[row][0] == X) {
                    return WIN_POINTS + depth;
                } else if (field[row][0] == O) {
                    return -WIN_POINTS - depth;
                }
            }
        }
        for (int col = 0; col < field.length; col++) {
            if (field[0][col] == field[1][col] && field[1][col] == field[2][col]) {
                if (field[0][col] == X) {
                    return WIN_POINTS + depth;
                } else if (field[0][col] == O) {
                    return -WIN_POINTS - depth;
                }
            }
        }
        if (field[0][0] == field[1][1] && field[1][1] == field[2][2]) {
            if (field[0][0] == X) {
                return WIN_POINTS + depth;
            } else if (field[0][0] == O) {
                return -WIN_POINTS - depth;
            }
        }
        if (field[0][2] == field[1][1] && field[1][1] == field[2][0]) {
            if (field[0][2] == X) {
                return WIN_POINTS + depth;
            } else if (field[0][2] == O) {
                return -WIN_POINTS - depth;
            }
        }
        return 0;
    }

    private boolean isMovesNotLeft(int[][] field) {
        for (int[] row : field) {
            for (int col : row) {
                if (col == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private void validateField(CurrentGame prevGame, CurrentGame curGame) {
        int[][] prevField = prevGame.getGameField().getField();
        int[][] curField = curGame.getGameField().getField();
        int dif = 0;
        for (int row = 0; row < curField.length; row++) {
            for (int col = 0; col < curField[row].length; col++) {
                if ((prevField[row][col] == X && curField[row][col] == O) ||
                        (prevField[row][col] == O && curField[row][col] == X)) {
                    throw new InvalidFieldException("Invalid game field");
                }
                if (curField[row][col] != prevField[row][col]) {
                    dif++;
                }
                if (dif > 1) {
                    throw new InvalidFieldException("Invalid game field");
                }
            }
        }
        if (dif == 0 && !prevGame.isDraw() && prevGame.getWinner() == null) {
            throw new InvalidFieldException("Need to make move");
        }
    }

    private void mapGames(CurrentGame prevGame, CurrentGame curGame) {
        curGame.getGameField().setId(prevGame.getGameField().getId());
        curGame.setActivePlayer(prevGame.getActivePlayer());
        curGame.setDraw(prevGame.isDraw());
        curGame.setWinner(prevGame.getWinner());
        curGame.setXPlayer(prevGame.getXPlayer());
        curGame.setOPlayer(prevGame.getOPlayer());
        curGame.setCreated(prevGame.getCreated());
    }
}
