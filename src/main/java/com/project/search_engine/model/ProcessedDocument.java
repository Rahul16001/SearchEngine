package com.project.search_engine.model;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProcessedDocument {
    String id;
    String fileName;
    List<String> words;
}
