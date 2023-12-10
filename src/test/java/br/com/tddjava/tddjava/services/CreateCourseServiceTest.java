package br.com.tddjava.tddjava.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.tddjava.tddjava.modules.courses.entities.Course;
import br.com.tddjava.tddjava.modules.courses.exceptions.CourseAlreadyExistsException;
import br.com.tddjava.tddjava.modules.courses.repositories.CourseInMemoryRepository;
import br.com.tddjava.tddjava.modules.courses.services.CreateCourseService;

public class CreateCourseServiceTest {

@Test
public void should_be_able_to_create_a_new_course() {
    // Criar um novo curso
    // Criar table curso (Entidade)
    // ID, description, name, workload
    Course course = new Course();
    course.setDescription("Curso_Description_Test");
    course.setName("Curso_Name");
    course.setWorkload(100);

    // Criar um repositorio de curso
    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    // Criar um novo service
    CreateCourseService createCourseService = new CreateCourseService(repository);
    Course createdCourse = createCourseService.execute(course);

    assertNotNull(createdCourse.getId());

}

@Test
public void should_not_be_able_to_create_a_new_course_if_exists() {
    Course course = new Course();
    course.setDescription("Not_Create_course");
    course.setName("Not_Create_course");
    course.setWorkload(100);

    CourseInMemoryRepository repository = new CourseInMemoryRepository();

    CreateCourseService createCourseService = new CreateCourseService(repository);
    assertThrows(CourseAlreadyExistsException.class, () -> {
            createCourseService.execute(course);
            createCourseService.execute(course);
        });

}

@Test
public void should_not_be_able_to_create_a_new_course_with_null_name() {
    Course course = new Course();
    course.setDescription("Curso_Description_Test");
    course.setName(null); // Nome nulo
    course.setWorkload(100);

    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    CreateCourseService createCourseService = new CreateCourseService(repository);

    assertThrows(IllegalArgumentException.class, () -> createCourseService.execute(course));
}

@Test
public void should_not_be_able_to_create_a_new_course_with_empty_name() {
    Course course = new Course();
    course.setDescription("Curso_Description_Test");
    course.setName(""); // Nome vazio
    course.setWorkload(100);

    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    CreateCourseService createCourseService = new CreateCourseService(repository);

    assertThrows(IllegalArgumentException.class, () -> createCourseService.execute(course));
}

@Test
public void should_be_able_to_create_a_new_course_with_minimum_name_length() {
    Course course = new Course();
    course.setDescription("Curso_Description_Test");
    course.setName("CursoABC"); // Nome com o tamanho mínimo permitido
    course.setWorkload(100);

    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    CreateCourseService createCourseService = new CreateCourseService(repository);

    Course createdCourse = createCourseService.execute(course);

    assertNotNull(createdCourse.getId());
}

@Test
public void should_be_able_to_create_a_new_course_with_maximum_name_length() {
    Course course = new Course();
    course.setDescription("Curso_Description_Test");
    course.setName("Inteligência Artificial e Aprendizado De Máquina 2"); // Nome com o tamanho máximo permitido
    course.setWorkload(100);

    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    CreateCourseService createCourseService = new CreateCourseService(repository);

    Course createdCourse = createCourseService.execute(course);

    assertNotNull(createdCourse.getId());
}

}