package com.project.search_engine.FileHandlingHelper;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class FileHandlingHelper {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static boolean createNewFileIfDoesNotExist(@NonNull File file) throws IOException
    {
        return file.createNewFile();
    }

    public static void createEmptyJSONFile(@NonNull File file)
    {
        objectMapper.writeValue(file,new HashMap<String, Set<String>>());
        System.out.println("Empty JSON file created");
    }

    public static File getFileForGivenPath(@NonNull String path)
    {
        return new File(path);
    }

    public static Map<String,Set<String>> readFromWordToDocumentMapFile(@NonNull File file)
    {
        return objectMapper.readValue(
                file,
                new TypeReference<Map<String, Set<String>>>() {}
        );
    }

    public static void writeToWordToDocumentMpFile(@NonNull File file,Map<String,Set<String>> wordToDocumentMap)
    {
        objectMapper.writeValue(file, wordToDocumentMap);
    }
}
