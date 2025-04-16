package web.mapper;

import domain.model.CurrentGame;
import domain.model.GameField;
import org.springframework.stereotype.Component;
import web.model.CurrentGameDto;
import web.model.GameFieldDto;
import web.model.WaitingGameDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class WebMapper {

    public CurrentGame fromDto(CurrentGameDto dto, UUID uuid) {
        return new CurrentGame(fromDto(dto.getGameField()), uuid);
    }

    public CurrentGameDto toDto(CurrentGame game) {
        CurrentGameDto dto = new CurrentGameDto(toListWaitingDto(game.getGameField()));
        dto.setActivePlayer(game.getActivePlayer());
        dto.setDraw(game.isDraw());
        dto.setWinner(game.getWinner());
        dto.setCreated(game.getCreated());
        return dto;
    }

    public List<CurrentGameDto> toDto(List<CurrentGame> games) {
        return games.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<WaitingGameDto> toListWaitingDto(List<CurrentGame> games) {
        return games.stream()
                .map(this::toWaitingDto)
                .collect(Collectors.toList());
    }

    public WaitingGameDto toWaitingDto(CurrentGame game) {
        WaitingGameDto dto = new WaitingGameDto();
        dto.setUuid(game.getUuid());
        dto.setXPlayer(game.getXPlayer() == null ? "Empty slot" : game.getXPlayer().toString());
        dto.setOPlayer(game.getOPlayer() == null ? "Empty slot" : game.getOPlayer().toString());
        dto.setCreated(game.getCreated());
        return dto;
    }

    private GameField fromDto(GameFieldDto dto) {
        GameField field = new GameField();
        for (int row = 0; row < dto.getField().length; row++) {
            for (int col = 0; col < dto.getField()[row].length; col++) {
                if (dto.getField()[row][col].equalsIgnoreCase("x")) {
                    field.setX(row, col);
                } else if (dto.getField()[row][col].equalsIgnoreCase("o")) {
                    field.setO(row, col);
                } else {
                    field.getField()[row][col] = -1;
                }
            }
        }
        return field;
    }

    private GameFieldDto toListWaitingDto(GameField field) {
        GameFieldDto dto = new GameFieldDto();
        for (int row = 0; row < dto.getField().length; row++) {
            for (int col = 0; col < dto.getField()[row].length; col++) {
                if (field.getField()[row][col] == 1) {
                    dto.setX(row, col);
                } else if (field.getField()[row][col] == 0) {
                    dto.setO(row, col);
                } else {
                    dto.getField()[row][col] = "";
                }
            }
        }
        return dto;
    }
}
