package com.project.search_engine.services.document_processing_service;
import com.project.search_engine.FileHandlingHelper.FileHandlingHelper;
import com.project.search_engine.model.Document;
import com.project.search_engine.model.ProcessedDocument;
import com.project.search_engine.services.reading_service.FileReadingService;
import com.project.search_engine.services.tokenizer_service.TokenizerService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class IndexService {

    @Value("${index.json.path}")
    private String indexPath;

    Map<String,Set<String>> wordToDocumentMap = new HashMap<>();

    @Autowired
    FileReadingService fileReadingService;

    @Autowired
    NormalizationService normalizationService;

    @Autowired
    TokenizerService tokenizerService;

    File file;

    @PostConstruct
    private void readDatafromJson() throws IOException
    {
        file = FileHandlingHelper.getFileForGivenPath(indexPath);
        if(FileHandlingHelper.createNewFileIfDoesNotExist(file)) {
            FileHandlingHelper.createEmptyJSONFile(file);
        }
        wordToDocumentMap = FileHandlingHelper.readFromWordToDocumentMapFile(file);
        System.out.println(indexPath+" "+"Post Construct method is called");
    }

    public Map<String,Set<String>> createIndexForTheOccurrence(List<ProcessedDocument> processedDocuments)
    {
        for(ProcessedDocument processedDocument : processedDocuments)
        {
            for(String word : processedDocument.getWords())
            {
                Set<String> documentNames = wordToDocumentMap.getOrDefault(word,new HashSet<>());
                documentNames.add(processedDocument.getFileName());
                wordToDocumentMap.put(word,documentNames);
            }
        }
        FileHandlingHelper.writeToWordToDocumentMpFile(file,wordToDocumentMap);
        return wordToDocumentMap;
    }



    public Map<String,Set<String>> createIndex()
    {
        List<Document> documents = fileReadingService.readMultiple();
        List<ProcessedDocument> processedDocuments = documents.stream()
                .map(each -> tokenizerService.tokenize(each))
                .map(each -> normalizationService.processDocument(each))
                .toList();

        return createIndexForTheOccurrence(processedDocuments);
    }


}
