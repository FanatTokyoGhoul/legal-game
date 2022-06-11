package ru.vsu.legal.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.legal.exceptions.NotFoundException;
import ru.vsu.legal.models.dto.requests.GameContinueRequest;
import ru.vsu.legal.models.dto.requests.GameCreateRequest;
import ru.vsu.legal.models.entities.*;
import ru.vsu.legal.repositories.GameActionsRepository;
import ru.vsu.legal.repositories.GameRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private static final int MIN_DEMAND_ADD = -10;
    private static final int MAX_DEMAND_ADD = 10;

    private static final int MIN_COUNT_INCIDENT = 0;
    private static final int MAX_COUNT_INCIDENT = 15;
    private final Logger log = LoggerFactory.getLogger(CompanyService.class);
    private final GameRepository gameRepository;
    private final GameActionsRepository gameActionsRepository;
    private final CompanyService companyService;
    private final InsuranceService insuranceService;

    private final TreatyService treatyService;

    @Autowired
    public GameService(GameRepository gameRepository, GameActionsRepository gameActionsRepository, CompanyService companyService, InsuranceService insuranceService, TreatyService treatyService) {
        this.gameRepository = gameRepository;
        this.gameActionsRepository = gameActionsRepository;
        this.companyService = companyService;
        this.insuranceService = insuranceService;
        this.treatyService = treatyService;
    }

    public void createGame(GameCreateRequest createRequest) {
        log.info("Converting createRequest to game");
        Game gameToSave = createRequest.toGame();
        log.info("Saving game: {}", gameToSave.toString());
        Game game = gameRepository.save(gameToSave);
        companyService.createCompany(createRequest.getCompanyCreateRequest(), game.getId());
        insuranceService.createTypes(createRequest.getTypeOfInsurancesCreateRequest(), game.getId());
    }

    public List<GameActions> continueGame(GameContinueRequest gameContinueRequest) {

        Game game = getGameByName(gameContinueRequest.getName());
        GameActions lastActions = game.getGameHistory().size() > 0 ?
                game.getGameHistory().get(game.getGameHistory().size() - 1) : new GameActions(1);
        Company company = game.getCompany();
        List<GameActions> actionsList = new ArrayList<>();

        log.info("Filtered treaty bi ids: {}", gameContinueRequest.getIdsTreatyToDelete());
        List<Treaty> treatyToDelete = company.getTreaties().stream()
                .filter(treaty -> gameContinueRequest.getIdsTreatyToDelete().contains(treaty.getId())).toList();
        treatyService.deleteAll(treatyToDelete);
        Long companyId = company.getId();
        List<Treaty> savedTreaty = treatyService.createAll(gameContinueRequest.getNewTreaty(), game.getId(), companyId);


        for (int i = 0; i < gameContinueRequest.getTerm(); i++) {
            company = companyService.getCompany(company.getId());
            StringBuilder builderLog = new StringBuilder();
            log.info("Start iteration game. Month: {}", lastActions.getMonth() + i);
            GameActions nowAction = new GameActions(lastActions.getMonth() + 1 + i);

            log.info("First action");
            builderLog.append("Action tax:");
            builderLog.append("     1: Change capital. ").append(company.getCapital()).append(" to ");
            company.setCapital(calculateCapital(company.getCapital(), game.getTaxPercent()));
            builderLog.append(company.getCapital()).append(".\n");
            log.info("Check game end");
            if (company.getCapital() < 0) {
                builderLog.append("     2: Game end. Company is bankruptcy\n");
                nowAction.setLog(builderLog.toString());
                actionsList.add(nowAction);
                return actionsList;
            }

            log.info("Second action");
            builderLog.append("Action sell and payment:");
            builderLog.append("     1: Selling treaties: \n");
            for (Treaty treaty : company.getTreaties()) {
                builderLog.append("         Selling treaty! ").append(treaty);
                Float money = 0F;
                for (Insurance insurance : treaty.getInsuranceList()) {
                    TypeOfInsurance typeOfInsurance = insuranceService.getTypeOfInsurance(insurance.getTypeOfInsuranceId());
                    float demand = getDemand(typeOfInsurance.getBaseDemand(), insurance.getBaseRate(),
                            treaty.getValidity(), treaty.getMaxPayment(), treaty.getInstallmentFrequency());
                    demand += (int) getRandomIntegerBetweenRange(MIN_DEMAND_ADD, MAX_DEMAND_ADD);
                    money += demand * insurance.getBaseRate() * (treaty.getValidity() / treaty.getInstallmentFrequency());

                    int countIncidents = (int) getRandomIntegerBetweenRange(MIN_COUNT_INCIDENT, MAX_COUNT_INCIDENT);
                    for (int j = 0; j < countIncidents; j++) {
                        float paymentPercent = (float) getRandomIntegerBetweenRange(0, 1);
                        money -= treaty.getMaxPayment() * paymentPercent;
                    }
                }
                company.setCapital(company.getCapital() + money);

                builderLog.append("Company get money: ").append(money).append("\n");
            }
            companyService.updateCapital(company.getCapital(), company.getId());
            builderLog.append("Info:\n");
            builderLog.append("Company capital: ").append(company.getCapital()).append("\n");
            builderLog.append("Company treaties: ").append(company.getTreaties()).append("\n");
            nowAction.setLog(builderLog.toString());
            nowAction.setGameKey(game.getId());
            nowAction = gameActionsRepository.save(nowAction);
            actionsList.add(nowAction);
        }
        return actionsList;
    }

    public List<GameActions> history(String gameName){
        Game game = getGameByName(gameName);
        return game.getGameHistory();
    }

    private double getRandomIntegerBetweenRange(double min, double max) {
        return (Math.random() * ((max - min) + 1)) + min;
    }

    private Float getDemand(Float baseDemand, Float costInsurance, Integer validity, Float maxPayment, Float freq) {
        return baseDemand + maxPayment / (costInsurance * (validity / freq));
    }

    private Float calculateCapital(Float capital, Integer taxPercent) {
        return capital - capital * taxPercent / 100;
    }

    private Game getGameByName(String name) {
        log.info("Getting game by name:" + name);
        return gameRepository.findGameByName(name).orElseThrow(() -> new NotFoundException("Game with name: " + name + "not found"));
    }
}
