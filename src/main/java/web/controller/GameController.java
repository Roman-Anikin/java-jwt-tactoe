package web.controller;

import domain.model.UserWinRate;
import domain.service.GameService;
import org.springframework.web.bind.annotation.*;
import web.mapper.WebMapper;
import web.model.CurrentGameDto;
import web.model.WaitingGameDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {

    private final WebMapper mapper;
    private final GameService service;

    public GameController(WebMapper mapper, GameService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @PostMapping
    public UUID createGame(@RequestParam(name = "mode", defaultValue = "ai") String mode) {
        return service.createGame(mode);
    }

    @GetMapping
    public List<WaitingGameDto> getWaitingGames() {
        return mapper.toListWaitingDto(service.getWaitingGames());
    }

    @PatchMapping("/{uuid}")
    public WaitingGameDto joinGame(@RequestParam("badge") String badge, @PathVariable("uuid") UUID uuid) {
        return mapper.toWaitingDto(service.joinGame(uuid, badge));
    }

    @PostMapping("/{uuid}")
    public CurrentGameDto updateField(@RequestBody CurrentGameDto dto, @PathVariable("uuid") UUID uuid) {
        return mapper.toDto(service.getMove(mapper.fromDto(dto, uuid)));
    }

    @GetMapping("/{uuid}")
    public CurrentGameDto getByUuid(@PathVariable("uuid") UUID uuid) {
        return mapper.toDto(service.getByUuid(uuid));
    }

    @GetMapping("/finished")
    public List<CurrentGameDto> getFinishedGamesByUser() {
        return mapper.toDto(service.getFinishedGamesByUser());
    }

    @GetMapping("/leaders/{limit}")
    public List<UserWinRate> getUsersWinRate(@PathVariable("limit") int limit) {
        return service.getUsersWinRate(limit);
    }
}
