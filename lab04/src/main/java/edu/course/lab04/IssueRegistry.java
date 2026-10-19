package edu.course.lab04;

import java.util.HashMap;

public class IssueRegistry{

    private final HashMap<IssueId, ProjectTask> storage = new HashMap<>();

    public void put(IssueId key, ProjectTask value) {
        storage.put(key, value);
    }

    public ProjectTask get(IssueId key) {
        return storage.get(key);
    }

    public ProjectTask remove(IssueId key) {
        return storage.remove(key);
    }

    public boolean containsKey(IssueId key) {
        return storage.containsKey(key);
    }

    public int size() {
        return storage.size();
    }
}
