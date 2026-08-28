package com.project.search_engine.services;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReadingService {
    public static Map<String,String> readAllPDFsFromFolder(String folderPath) {
        Path path = Paths.get(folderPath);
        Map<String,String> nameToDataMap = new HashMap<>();
        // Verify if directory exists
        if (!Files.isDirectory(path)) {
            System.err.println("The specified path is not a valid directory.");
            return null;
        }
        // Use try-with-resources to automatically close the file stream
        try (Stream<Path> paths = Files.walk(path)) {
            nameToDataMap= paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().toLowerCase().endsWith(".pdf"))
                    .map(FileReadingService::parsePDFText)
                    .filter(obj -> true)
                    .collect(Collectors.toMap(m -> m[0],m ->m[1]));

        } catch (IOException e) {
            System.err.println("Error accessing directory: " + e.getMessage());
        }
        return nameToDataMap;
    }

    private static String[] parsePDFText(Path pdfPath) {
        File pdfFile = pdfPath.toFile();
        String extractedText = null;
      //  System.out.println("\n--- Reading: " + pdfFile.getName() + " ---");
        // Use try-with-resources to automatically close the PDDocument
        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            // Instantiate PDFTextStripper to handle text extraction
            PDFTextStripper pdfStripper = new PDFTextStripper();

            // Extract and clean up text
            extractedText = pdfStripper.getText(document);

            if (extractedText.trim().isEmpty()) {
               // System.out.println("[Warning: This PDF contains no extractable text or consists only of images]");
            } else {
               // System.out.println(extractedText);
            }
        } catch (IOException e) {
           // System.err.println("Failed to parse " + pdfFile.getName() + ": " + e.getMessage());
        }
        return new String[]{pdfFile.getName(),extractedText};
    }
}
