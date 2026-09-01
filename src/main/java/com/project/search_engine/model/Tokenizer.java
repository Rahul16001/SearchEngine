package com.project.search_engine.model;

public interface Tokenizer {
    ProcessedDocument tokenize(Document document);
}
