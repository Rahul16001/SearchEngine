package com.project.search_engine.services.document_processing_service;

import com.project.search_engine.model.DocumentProcessor;
import com.project.search_engine.model.ProcessedDocument;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NormalizationService implements DocumentProcessor {

    @Override
    public ProcessedDocument processDocument(ProcessedDocument processedDocument) {
        List<String> normalizedWords = processedDocument.getWords().stream()
                .map(String::toLowerCase)
                .toList();
        processedDocument.setWords(normalizedWords);
        return processedDocument;
    }
}
