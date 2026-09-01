package com.project.search_engine.services.reading_service;

import com.project.search_engine.model.Document;
import com.project.search_engine.model.DocumentReader;
import lombok.Getter;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Service
public class FileReadingService implements DocumentReader {

    @Value("${search.document.path}")
    String folderPath ="D:\\testPdfs";

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
               System.out.println("[Warning: This PDF contains no extractable text or consists only of images]");
            } else {
               System.out.println(extractedText);
            }
        } catch (IOException e) {
           System.err.println("Failed to parse " + pdfFile.getName() + ": " + e.getMessage());
        }
        return new String[]{pdfFile.getName(),extractedText};
    }

    @Override
    public Document readSingle() {
//        Path path = Paths.get(folderPath);
//        Map<String,String> nameToDataMap = new HashMap<>();
//        // Verify if directory exists
//        if (!Files.isDirectory(path)) {
//            System.err.println("The specified path is not a valid directory.");
//            return null;
//        }
//        // Use try-with-resources to automatically close the file stream
//        try (Stream<Path> paths = Files.walk(path)) {
//            nameToDataMap= paths.filter(Files::isRegularFile)
//                    .filter(p -> p.toString().toLowerCase().endsWith(".pdf"))
//                    .map(FileReadingService::parsePDFText)
//                    .filter(obj -> true)
//                    .collect(Collectors.toMap(m -> m[0],m ->m[1]));
//
//        } catch (IOException e) {
//            System.err.println("Error accessing directory: " + e.getMessage());
//        }
//        return Document.builder()
//                .id("temp")
//                .title()
        return null;
    }

    @Override
    public List<Document> readMultiple() {
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
                    .collect(Collectors.toMap(m -> m[0],m ->m[1]));

        } catch (IOException e) {
            System.err.println("Error accessing directory: " + e.getMessage());
        }
        return nameToDataMap.entrySet().stream()
                .map(each ->
                {
                    return Document.builder()
                            .id("temp")
                            .title(each.getKey())
                            .content(each.getValue())
                            .build();
                })
                .toList();
    }

}
