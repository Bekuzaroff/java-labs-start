package edu.course.lab05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskFileLoaderTest {

    private final TaskFileLoader loader = new TaskFileLoader();

    @Test
    void prepPart_readAndParseNumbers(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("numbers.txt");
        Files.write(file, List.of("10", "bad", "20"), StandardCharsets.UTF_8);

        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        assertEquals(List.of("10", "bad", "20"), lines);

        List<Integer> parsed = new ArrayList<>();
        assertThrows(NumberFormatException.class, () -> {
            for (String line : lines) {
                parsed.add(Integer.parseInt(line));
            }
        });
        assertEquals(List.of(10), parsed);
    }

    @Test
    void load_validFile(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("valid.txt");
        Files.write(file, List.of(
                "id;title;category;estimatedHours;status",
                "T-101;Prepare release;backend;8;READY",
                "T-102;Fix login bug;frontend;4;TODO"
        ), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertTrue(result.getErrors().isEmpty());
        assertEquals(2, result.getItems().size());
        assertEquals("T-101", result.getItems().get(0).getId());
        assertEquals(TaskStatus.READY, result.getItems().get(0).getStatus());
    }

    @Test
    void load_unicode(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("unicode.txt");
        Files.write(file, List.of(
                "id;title;category;estimatedHours;status",
                "T-201;Настроить базу данных;бэкенд;12;LOADING"
        ), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertTrue(result.getErrors().isEmpty());
        assertEquals("Настроить базу данных", result.getItems().get(0).getTitle());
        assertEquals("бэкенд", result.getItems().get(0).getCategory());
    }

    @Test
    void load_emptyField(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("empty.txt");
        Files.write(file, List.of(
                "id;title;category;estimatedHours;status",
                "T-301;;backend;5;TODO"
        ), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertTrue(result.getItems().isEmpty());
        assertEquals(1, result.getErrors().size());
        assertEquals(2, result.getErrors().get(0).getLineNumber());
        assertEquals("title", result.getErrors().get(0).getField());
    }

    @Test
    void load_invalidNumber(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("bad_num.txt");
        Files.write(file, List.of(
                "id;title;category;estimatedHours;status",
                "T-401;Write docs;docs;eight;TODO",
                "T-402;Refactor;core;-5;TODO"
        ), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertTrue(result.getItems().isEmpty());
        assertEquals(2, result.getErrors().size());
        assertEquals("estimatedHours", result.getErrors().get(0).getField());
        assertEquals("estimatedHours", result.getErrors().get(1).getField());
    }

    @Test
    void load_unknownStatus(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("bad_status.txt");
        Files.write(file, List.of(
                "id;title;category;estimatedHours;status",
                "T-501;Deploy;devops;3;ARCHIVED"
        ), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertTrue(result.getItems().isEmpty());
        assertEquals(1, result.getErrors().size());
        assertEquals("status", result.getErrors().get(0).getField());
    }

    @Test
    void load_wrongColumns(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("wrong_cols.txt");
        Files.write(file, List.of(
                "id;title;category;estimatedHours;status",
                "T-601;Only four;columns;5"
        ), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertTrue(result.getItems().isEmpty());
        assertEquals(1, result.getErrors().size());
        assertEquals("columns", result.getErrors().get(0).getField());
    }

    @Test
    void load_mixedRows(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("mixed.txt");
        Files.write(file, List.of(
                "id;title;category;estimatedHours;status",
                "T-701;Valid 1;backend;6;COMPLETED",
                "T-702;Broken;backend;abc;TODO",
                "T-703;Valid 2;qa;2;READY",
                ";No id;qa;1;TODO"
        ), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertEquals(2, result.getItems().size());
        assertEquals(2, result.getErrors().size());
        assertEquals(3, result.getErrors().get(0).getLineNumber());
        assertEquals(5, result.getErrors().get(1).getLineNumber());
    }

    @Test
    void load_missingFile(@TempDir Path tempDir) {
        Path missing = tempDir.resolve("missing.txt");

        DataLoadingException ex = assertThrows(DataLoadingException.class, () -> loader.load(missing));
        assertInstanceOf(IOException.class, ex.getCause());
    }
}
