package com.flowable.rpa.work.bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.flowable.action.api.bot.BaseBotActionResult;
import com.flowable.action.api.bot.BotActionResult;
import com.flowable.action.api.bot.BotService;
import com.flowable.action.api.history.HistoricActionInstance;
import com.flowable.action.api.intents.Intent;
import com.flowable.action.api.repository.ActionDefinition;
import org.flowable.cmmn.api.CmmnRuntimeService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BuyNowBot implements BotService {

    private final CmmnRuntimeService cmmnRuntimeService;

    public BuyNowBot(CmmnRuntimeService cmmnRuntimeService) {
        this.cmmnRuntimeService = cmmnRuntimeService;
    }

    @Override
    public String getKey() {
        return "buy-now-bot";
    }

    @Override
    public String getName() {
        return "Buy now bot";
    }

    @Override
    public String getDescription() {
        return "Start Buy console process";
    }

    @Override
    public BotActionResult invokeBot(HistoricActionInstance actionInstance,
                                     ActionDefinition actionDefinition,
                                     Map<String, Object> payload) {
        String caseInstanceId = actionInstance.getScopeId();

        cmmnRuntimeService.setVariable(caseInstanceId, "buy", true);

        cmmnRuntimeService.setVariable(caseInstanceId, "consoleBuy", payload.get("consoleToBuy"));

        String planItemInstanceId = cmmnRuntimeService.createUserEventListenerInstanceQuery()
                .caseInstanceId(caseInstanceId)
                .stateUnavailable()
                .planItemDefinitionId("buy-now-event-listener")
                .singleResult()
                .getId();

        cmmnRuntimeService.triggerPlanItemInstance(planItemInstanceId);

        cmmnRuntimeService.setVariable(caseInstanceId, "buy", false);

        ObjectNode responsePayload = new ObjectMapper().createObjectNode();

        return new BaseBotActionResult(responsePayload, Intent.NOOP);
    }
}
