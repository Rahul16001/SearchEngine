package com.project.search_engine.model;

import java.util.List;

public interface DocumentReader {
    Document readSingle();
    List<Document> readMultiple();
}
