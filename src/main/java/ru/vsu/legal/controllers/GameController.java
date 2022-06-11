package ru.vsu.legal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vsu.legal.models.dto.requests.GameContinueRequest;
import ru.vsu.legal.models.dto.requests.GameCreateRequest;
import ru.vsu.legal.models.entities.GameActions;
import ru.vsu.legal.services.GameService;

import java.util.List;

@RestController
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createGame(
            @RequestBody GameCreateRequest gameCreateRequest
    ) {
        gameService.createGame(gameCreateRequest);
    }

    @PostMapping("/continue")
    @ResponseStatus(HttpStatus.OK)
    public List<GameActions> continueGame(
            @RequestBody GameContinueRequest gameContinueRequest
    ) {
        return gameService.continueGame(gameContinueRequest);
    }

    @GetMapping("/history")
    @ResponseStatus(HttpStatus.OK)
    public List<GameActions> history(
      @RequestBody String gameName
    ){
        return gameService.history(gameName);
    }
}
