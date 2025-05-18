package br.com.spacegtech.service;

import java.util.List;

public interface CourseService {
    public List<String> retriveCourses(String student);
    public List<String> doSomenthing(String student);
    public void deleteCourse(String student);
}
