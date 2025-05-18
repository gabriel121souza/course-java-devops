package br.com.spacegtech.business;

import br.com.spacegtech.service.CourseService;

import java.util.ArrayList;
import java.util.List;

//SUT - System (Method) Under Test
public class CourseBusiness {
    //CourseService is a Dependency
    private CourseService service;

    public CourseBusiness(CourseService service) {
        this.service = service;
    }

    public List<String> retriveCoursesRelatedToSpring(String student){
        var filteredCourses = new ArrayList<String>();
        if("Foo Bar".equals(student)){
            return filteredCourses;
        }
        var allCourses = service.retriveCourses(student);
        for(String course : allCourses){
            if(course.contains("Spring")){
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }
    public void deleteCoursesRelatedToSpring(String student){
        var allCourses = service.retriveCourses(student);
        for(String course : allCourses){
            if(!course.contains("Spring")){
                service.deleteCourse(course);
            }
        }
    }
}
