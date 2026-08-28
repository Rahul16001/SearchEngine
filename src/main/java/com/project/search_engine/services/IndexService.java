package com.project.search_engine.services;
import java.util.*;

public class IndexService {

    public static Map<String,List<String>> createIndexForTheOccurrence(Map<String,List<String>> normalizedNameToDataMap)
    {
        Map<String,List<String>> wordToDocumentMap = new HashMap<>();
        for(Map.Entry<String,List<String>> entry : normalizedNameToDataMap.entrySet())
        {
            for(String word : entry.getValue())
            {
                List<String> docs = wordToDocumentMap.getOrDefault(word,new ArrayList<>());
                docs.add(entry.getKey());
                wordToDocumentMap.put(word,docs);
            }
        }
        return wordToDocumentMap;
    }

    public static void main(String[] args) {
        Map<String,List<String>> output = IndexService.createIndexForTheOccurrence(NormalizationService.getNormalizedStringList(Objects.requireNonNull(FileReadingService.readAllPDFsFromFolder("D:\\testPdfs"))));

        for(Map.Entry<String,List<String>> o : output.entrySet())
        {
            System.out.println(o.getKey()+" "+o.getValue());
        }
    }
}
