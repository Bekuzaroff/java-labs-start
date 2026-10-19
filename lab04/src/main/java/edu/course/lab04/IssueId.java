package edu.course.lab04;

import java.util.Objects;

public class IssueId {
    private final String project_code;
    private final int project_num;

    public IssueId(String project_code, int project_num) {
        this.project_code = project_code;
        this.project_num = project_num;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;

        IssueId obj2 = (IssueId) obj;
        return Objects.equals(this.project_code, obj2.project_code)
                && Objects.equals(this.project_num, obj2.project_num);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.project_code, this.project_num);
    }

    public String getProjectCode() { return this.project_code; }
    public int getProjectNum() { return this.project_num; }


}
