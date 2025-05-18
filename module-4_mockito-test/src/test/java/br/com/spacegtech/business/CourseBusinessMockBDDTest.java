package br.com.spacegtech.business;

import br.com.spacegtech.service.CourseService;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
class CourseBusinessMockBDDTest {
     CourseService mockService;
     CourseBusiness business;
     List<String> courses;
    @BeforeEach
    void setup(){
        mockService = mock(CourseService.class);
        business = new CourseBusiness(mockService);
        courses = List.of("REST API's RESTFul do 0 à Azure com ASP.NET Core 5 e Docker",
                "Agile Desmistificado com Scrum, XP, Kanban e Trello",
                "Spotify Engineering Culture Desmistificado",
                "REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker",
                "Docker do Zero à Maestria - Contêinerização Desmistificada",
                "Docker para Amazon AWS Implante Apps Java e .NET com Travis CI",
                "Microsserviços do 0 com Spring Cloud, Spring Boot e Docker",
                "Arquitetura de Microsserviços do 0 com ASP.NET, .NET 6 e C#",
                "REST API's RESTFul do 0 à AWS com Spring Boot 3 Kotlin e Docker",
                "Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android",
                "Microsserviços do 0 com Spring Cloud, Kotlin e Docker");
    }
    @Test
    void testCoursesRelatedToSpring_When_UsingAMock(){
        given(mockService.retriveCourses("Leandro")).willReturn(courses);
        var filteredCourse = business.retriveCoursesRelatedToSpring("Leandro");
        //Then / Assert
        assertThat(filteredCourse.size(), is(4));
    }

    @Test
    @DisplayName("Delete Courses not related to Spring using mockito sould call")
    void testDeleteCoursesNotRelatedToSpring_UsingMockitoVerify_Should_CallMethod_deleteCourse(){
        given(mockService.retriveCourses("Leandro")).willReturn(courses);
        business.deleteCoursesRelatedToSpring("Leandro");
      /*  verify(mockService, times(1))
         .deleteCourse("Agile Desmistificado com Scrum, XP, Kanban e Trello");
       verify(mockService, atLeast(2))
                .deleteCourse("Agile Desmistificado com Scrum, XP, Kanban e Trello");

     */
        verify(mockService, atLeastOnce())
                .deleteCourse("Agile Desmistificado com Scrum, XP, Kanban e Trello");

        verify(mockService)
                .deleteCourse("Arquitetura de Microsserviços do 0 com ASP.NET, .NET 6 e C#");
        verify(mockService, never())
                .deleteCourse("REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker");

    }
    @Test
    @DisplayName("Delete Courses not related to Spring using mockito sould call")
    void testDeleteCoursesNotRelatedToSpring_UsingMockitoVerify_Should_CallMethod_deleteCourseV2(){
        given(mockService.retriveCourses("Leandro")).willReturn(courses);
        String agileCourse = "Agile Desmistificado com Scrum, XP, Kanban e Trello";
        String architectureCourse = "Arquitetura de Microsserviços do 0 com ASP.NET, .NET 6 e C#";
        String restSpringCourse = "REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker";

        business.deleteCoursesRelatedToSpring("Leandro");

        then(mockService).should().deleteCourse(agileCourse);
        then(mockService).should().deleteCourse(architectureCourse);
        then(mockService).should(never()).deleteCourse(restSpringCourse);

    }
    @Test
    @DisplayName("Delete Courses not related to Spring Capturing Arguments sould call")
    void testDeleteCoursesNotRelatedToSpring_CapturingArguments_Should_CallMethod_deleteCourseV2(){
        /*courses = List.of(
                "Agile Desmistificado com Scrum, XP, Kanban e Trello",
                "REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker");
         */
        given(mockService.retriveCourses("Leandro")).willReturn(courses);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

       // String agileCourse = "Agile Desmistificado com Scrum, XP, Kanban e Trello";
        business.deleteCoursesRelatedToSpring("Leandro");

        then(mockService).should(times(7)).deleteCourse(argumentCaptor.capture());

        assertThat(argumentCaptor.getAllValues().size(), is(7));

    }



}
