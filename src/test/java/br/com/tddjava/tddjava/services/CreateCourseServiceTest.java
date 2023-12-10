package br.com.tddjava.tddjava.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.tddjava.tddjava.modules.courses.entities.Course;
import br.com.tddjava.tddjava.modules.courses.exceptions.CourseAlreadyExistsException;
import br.com.tddjava.tddjava.modules.courses.repositories.CourseInMemoryRepository;
import br.com.tddjava.tddjava.modules.courses.services.CreateCourseService;

public class CreateCourseServiceTest {

// CT001.001 - Criar novo curso
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
// CT001.002 - Não criar novo curso, caso já exista esse curso
@Test
public void should_not_be_able_to_create_a_new_course_if_exists() {
    Course course = new Course();
    course.setDescription("Not_Create_course");
    course.setName("Not_Create_course");
    course.setWorkload(120);

    CourseInMemoryRepository repository = new CourseInMemoryRepository();

    CreateCourseService createCourseService = new CreateCourseService(repository);
    assertThrows(CourseAlreadyExistsException.class, () -> {
            createCourseService.execute(course);
            createCourseService.execute(course);
        });

}

// CT002.001 - Não criar curso, caso o nome seja nulo
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
// CT002.002 - Não criar curso, caso o nome esteja vazio
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
// CT002.003 - Não criar curso, caso o nome seja maior que o limite maximo de 50 caracteres
@Test
public void should_not_be_able_to_create_a_new_course_with_maximum_name_lenght() {
    Course course = new Course();
    course.setDescription("Curso_Description_Test");
    course.setName("x".repeat(51)); // Nome vazio
    course.setWorkload(100);

    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    CreateCourseService createCourseService = new CreateCourseService(repository);

    assertThrows(IllegalArgumentException.class, () -> createCourseService.execute(course));
}
// CT002.004 - Não criar curso, caso o nome seja maior que o limite minimo de 8 caracteres
@Test
public void should_not_be_able_to_create_a_new_course_with_minimum_name_lenght() {
    Course course = new Course();
    course.setDescription("Curso_Description_Test");
    course.setName("x".repeat(7)); // Nome vazio
    course.setWorkload(100);

    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    CreateCourseService createCourseService = new CreateCourseService(repository);

    assertThrows(IllegalArgumentException.class, () -> createCourseService.execute(course));
}
// CT002.005 - Criar curso, caso o nome tenha o limite maximo de 50 caracteres
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
// CT002.006 - Criar curso, caso o nome tenha o limite minimo de 8 caracteres
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

// CT003.001 - Não criar curso, caso a descrição seja nula
@Test
public void should_not_be_able_to_create_a_new_course_with_null_description() {
    Course course = new Course();
    course.setDescription(null); // Descrição nula
    course.setName("Engeharia de Software"); 
    course.setWorkload(100);

    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    CreateCourseService createCourseService = new CreateCourseService(repository);

    assertThrows(IllegalArgumentException.class, () -> createCourseService.execute(course));
}
// CT003.002 - Não criar curso, caso a descrição seja vazia 
@Test
public void should_not_be_able_to_create_a_new_course_with_empty_description() {
    Course course = new Course();
    course.setDescription(""); // Descrição vazia
    course.setName("Engeharia de Software"); 
    course.setWorkload(100);

    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    CreateCourseService createCourseService = new CreateCourseService(repository);

    assertThrows(IllegalArgumentException.class, () -> createCourseService.execute(course));
}
// CT003.003 - Não criar curso, caso a descrição seja menor que 10 caracteres
@Test
public void should_not_be_able_to_create_a_new_course_with_less_than_10_caracters_description() {
    Course course = new Course();
    course.setDescription(("x").repeat(9)); // Descrição com menos de 10 caracteres
    course.setName("Engeharia de Software"); 
    course.setWorkload(100);

    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    CreateCourseService createCourseService = new CreateCourseService(repository);

    assertThrows(IllegalArgumentException.class, () -> createCourseService.execute(course));
}
// CT003.004 - Não criar curso, caso a descrição seja maior que 500 caracteres
@Test
public void should_not_be_able_to_create_a_new_course_with_more_500_caracters_description() {
    Course course = new Course();
    course.setDescription(("x").repeat(501)); // Descrição com mais de 500 caracteres
    course.setName("Engeharia de Software"); 
    course.setWorkload(100);

    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    CreateCourseService createCourseService = new CreateCourseService(repository);

    assertThrows(IllegalArgumentException.class, () -> createCourseService.execute(course));
}
// CT003.005 - Criar curso, caso a descrição tenha o limite maximo de 500 caracteres
@Test
public void should_be_able_to_create_a_new_course_with_maximum_description_lenght() {
    Course course = new Course();
    course.setDescription(("x").repeat(50)); // Descrição com limite maximo de 500 caracteres
    course.setName("Engeharia de Software"); 
    course.setWorkload(100);

    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    CreateCourseService createCourseService = new CreateCourseService(repository);

    Course createdCourse = createCourseService.execute(course);

    assertNotNull(createdCourse.getId());
}
// CT003.006 - Criar curso, caso a descrição tenha o limite minimo de 10 caracteres
@Test
public void should_be_able_to_create_a_new_course_with_minimum_description_lenght() {
    Course course = new Course();
    course.setDescription(("x").repeat(10)); // Descrição com limite minimo de 10 caracteres
    course.setName("Engeharia de Software"); 
    course.setWorkload(100);

    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    CreateCourseService createCourseService = new CreateCourseService(repository);

    Course createdCourse = createCourseService.execute(course);

    assertNotNull(createdCourse.getId());
}

// CT004.001 - Não criar curso, caso a carga horária seja nula
@Test
public void should_not_be_able_to_create_a_new_course_with_null_workload() {
    Course course = new Course();
    course.setDescription("Curso_Description_Test");
    course.setName("Engeharia de Software"); 
    course.setWorkload(null); // Carga horária nula

    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    CreateCourseService createCourseService = new CreateCourseService(repository);

    assertThrows(IllegalArgumentException.class, () -> createCourseService.execute(course));
}
// CT004.002 - Não criar curso, caso a carga horária seja menor que 20 horas
@Test
public void should_not_be_able_to_create_a_new_course_with_less_than_20_hours_workload() {
    Course course = new Course();
    course.setDescription("Curso_Description_Test");
    course.setName("Engeharia de Software"); 
    course.setWorkload(19); // Carga horária com menos de 20 horas

    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    CreateCourseService createCourseService = new CreateCourseService(repository);

    assertThrows(IllegalArgumentException.class, () -> createCourseService.execute(course));
}
// CT004.003 - Não criar curso, caso a carga horária seja maior que 120 horas
@Test
public void should_not_be_able_to_create_a_new_course_with_more_120_hours_workload() {
    Course course = new Course();
    course.setDescription("Curso_Description_Test");
    course.setName("Engeharia de Software"); 
    course.setWorkload(121); // Carga horária com mais de 120 horas

    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    CreateCourseService createCourseService = new CreateCourseService(repository);

    assertThrows(IllegalArgumentException.class, () -> createCourseService.execute(course));
}
// CT004.004 - Criar curso, caso a carga horária esteja no limite maximo de 120 horas
@Test
public void should_be_able_to_create_a_new_course_with_maximum_workload_hours() {
    Course course = new Course();
    course.setDescription("Curso_Description_Test");
    course.setName("Engeharia de Software"); 
    course.setWorkload(120); // Carga horária com limite maximo de 120 horas

    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    CreateCourseService createCourseService = new CreateCourseService(repository);

    Course createdCourse = createCourseService.execute(course);

    assertNotNull(createdCourse.getId());
}
// CT004.005 - Criar curso, caso a carga horária esteja no limite minimo de 20 horas
@Test
public void should_be_able_to_create_a_new_course_with_minimun_workload_hours() {
    Course course = new Course();
    course.setDescription("Curso_Description_Test");
    course.setName("Engeharia de Software"); 
    course.setWorkload(20); // Carga horária com limite minimo de 20 horas

    CourseInMemoryRepository repository = new CourseInMemoryRepository();
    CreateCourseService createCourseService = new CreateCourseService(repository);

    Course createdCourse = createCourseService.execute(course);

    assertNotNull(createdCourse.getId());
}

}