package com.flowable.rpa.work.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.flowable.rpa.work.models.Nintendo;
import org.flowable.cmmn.api.CmmnRuntimeService;
import org.flowable.engine.RuntimeService;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ProcessWebDataService {
    private static final String NAME = "Value #1";
    private static final String URL = "Value #2";
    private static final String PRICE = "Value #3";
    private static final String IMAGE = "Value #4";
    private static final String EU_CURRENCY = "€";
    private static final String MEDIA_MARKT_CURRENCY = ".–";
    private static final String ARROW_UP = "&uarr;";
    private static final String ARROW_DOWN = "&darr;";

    private final RuntimeService runtimeService;

    private final CmmnRuntimeService cmmnRuntimeService;

    private final ObjectReader objectReader;


    public ProcessWebDataService(RuntimeService runtimeService,
                                 CmmnRuntimeService cmmnRuntimeService) {
        this.runtimeService = runtimeService;
        this.cmmnRuntimeService = cmmnRuntimeService;
        this.objectReader = new ObjectMapper()
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true)
                .reader();
    }

    public void toJson(String data, String id, String response) throws JsonProcessingException {
        List<Nintendo> result = new ArrayList<>();
        JsonNode jsonNode = new ObjectMapper().valueToTree(objectReader.readTree(data));
        Collections.singletonList(jsonNode.get("DataFromWebPage")).get(0)
                .forEach(nintendo -> result.add(Nintendo.NintendoBuilder.Nintendo()
                        .name(nintendo.get(NAME).asText())
                        .url(nintendo.get(URL).asText())
                        .price(nintendo.get(PRICE).asText())
                        .image(nintendo.get(IMAGE).asText())
                        .build()));
        runtimeService.setVariableLocal(id, response, new ObjectMapper().valueToTree(result));
    }

    public Double getMediaMarktPrice(String price) throws ParseException {
        if (price.isEmpty()) {
            return null;
        }
        price = price.trim();
        int currencyIndex = price.trim().indexOf(MEDIA_MARKT_CURRENCY);
        price = price.substring(0, currencyIndex);
        return NumberFormat.getInstance(Locale.FRANCE).parse(price).doubleValue();
    }

    public Double getFnacPrice(String price) throws ParseException {
        if (price.isEmpty()) {
            return null;
        }
        price = price.trim();
        int currencyIndex = price.indexOf(EU_CURRENCY);
        price = price.substring(0, currencyIndex);
        return NumberFormat.getInstance(Locale.FRANCE).parse(price).doubleValue();
    }

    public Double getCorteInglesPrice(String price) throws ParseException {
        if (price.isEmpty()) {
            return null;
        }
        price = price.trim();
        int currencyIndex = price.indexOf(EU_CURRENCY);
        price = price.substring(0, currencyIndex - 1);
        return NumberFormat.getInstance(Locale.FRANCE).parse(price).doubleValue();
    }

    public void addToSearch(ArrayNode object, String id) {
        ArrayNode currentSearch = (ArrayNode) runtimeService.getVariablesLocal(id).get("search");
        currentSearch.addAll(object);
        runtimeService.setVariableLocal(id, "search", currentSearch);
    }

    public void addToResults(ArrayNode object, String id) {
        ArrayNode currentSearch = (ArrayNode) cmmnRuntimeService.getVariables(id).get("results");
        currentSearch.add(object);
        cmmnRuntimeService.setVariable(id, "results", currentSearch);
    }

    public String parseDoubleToString(Double d) {
        if (d == null) {
            return "";
        }
        return String.format(Locale.ROOT, "%.2f", d);
    }

    public ArrayNode getPreviousSearch(String id) {
        ArrayNode results = (ArrayNode) cmmnRuntimeService.getVariables(id).get("results");
        return (ArrayNode) results.get(results.size() - 1);
    }

    public void filterStore(ArrayNode list, String id) {
        List<String> stores = new ArrayList<>();
        for (JsonNode obj : list) {
            stores.add(obj.get("store").asText());
        }
        List<String> distinctStores = stores.stream()
                .distinct()
                .collect(Collectors.toList());

        runtimeService.setVariableLocal(id, "distinctStores", distinctStores);
    }

    public String getHigher(String previous, String current) {
        double sub = Double.parseDouble(current) - Double.parseDouble(previous);
        return String.format("%s %s €", ARROW_UP, parseDoubleToString(sub));
    }

    public String getLower(String previous, String current) {
        double sub = Double.parseDouble(previous) - Double.parseDouble(current);
        return String.format("%s %s €", ARROW_DOWN, parseDoubleToString(sub));
    }
}
