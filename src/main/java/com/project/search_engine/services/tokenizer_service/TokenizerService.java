package com.project.search_engine.services.tokenizer_service;

import com.project.search_engine.model.Document;
import com.project.search_engine.model.ProcessedDocument;
import com.project.search_engine.model.Tokenizer;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description This class is responsible for ingesting the document and tokenize the class on the basis of stop words
 * @Return Processed Document
 */
@Service
public class TokenizerService implements Tokenizer {

    @Override
    public ProcessedDocument tokenize(Document document) {
        Set<String> words = Arrays.stream(document.getContent().split("[\\s*,./\\\\|!?;:'\"()\\[\\]{}<>+=%&@#$^~`0-9]+|\\b[a-zA-Z]\\b"))
                .collect(Collectors.toSet());

        return ProcessedDocument.builder()
                .id("temp")
                .fileName(document.getTitle())
                .words(words.stream().toList())
                .build();
    }
}
