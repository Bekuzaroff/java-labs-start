package edu.course.lab05;

import java.util.List;

public final class LoadResult {
    private final List<ProjectTask> items;
    private final List<ValidationError> errors;

    public LoadResult(List<ProjectTask> items, List<ValidationError> errors) {
        this.items = List.copyOf(items);
        this.errors = List.copyOf(errors);
    }

    public List<ProjectTask> getItems() { return items; }
    public List<ValidationError> getErrors() { return errors; }
}
