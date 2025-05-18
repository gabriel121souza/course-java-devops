package br.com.spacegtech.business;

import br.com.spacegtech.service.CourseService;
import br.com.spacegtech.service.stubs.CourseServiceStub;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseBusinessStubTest {

    @Test
    void testCoursesRelatedToSpring_When_UsingAStub(){
        //Given / Arrange
        CourseService stubService = new CourseServiceStub();
        CourseBusiness business = new CourseBusiness(stubService);
        //when / act
        var filteredCourse = business.retriveCoursesRelatedToSpring("Leandro");
        //Then / Assert
        assertEquals(4, filteredCourse.size());
    }
    @Test
    void testCoursesRelatedToSpring_When_UsingASFooBarStudent(){
        //Given / Arrange
        CourseService stubService = new CourseServiceStub();
        CourseBusiness business = new CourseBusiness(stubService);
        //when / act
        var filteredCourse = business.retriveCoursesRelatedToSpring("Foo Bar");
        //Then / Assert
        assertEquals(0, filteredCourse.size());
    }
}
