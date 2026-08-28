package com.project.search_engine.services;

import java.util.*;

public class NormalizationService {
    public static Map<String,List<String>> getNormalizedStringList(Map<String,String> nameToDataMap)
    {
        Map<String,List<String>> normalizedNameToDataMap = new HashMap<>();
        for(Map.Entry<String,String> each : nameToDataMap.entrySet()) {
            normalizedNameToDataMap.put(each.getKey(),
                    Arrays.stream(each.getValue().split("[\\s.,\\-_/\\\\|!?;:'\"()\\[\\]{}<>+=*%&@#$^~`0-9]+|\\b[a-zA-Z]\\b")).toList());
        }
        return normalizedNameToDataMap;
    }
}
