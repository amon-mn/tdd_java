package br.com.tddjava.tddjava.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;

import br.com.tddjava.tddjava.modules.courses.entities.Course;
import br.com.tddjava.tddjava.modules.courses.exceptions.CourseAlreadyExistsException;
import br.com.tddjava.tddjava.modules.courses.repositories.CourseInMemoryRepository;
import br.com.tddjava.tddjava.modules.courses.repositories.ICourseRepository;
import br.com.tddjava.tddjava.modules.courses.services.CreateCourseService;

public class CreateCourseServiceTest {
  // Casos de Teste de Caixa Preta
    @Test

    public void should_be_able_to_create_a_new_course() {
        // Criar um novo curso
        Course course = new Course();
        course.setDescription("Curso_Description_Test");
        course.setName("Curso_Name");
        course.setWorkload(100);

        // Criar um repositório de curso
        CourseInMemoryRepository repository = new CourseInMemoryRepository();
        // Criar um novo serviço
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

    
    // Casos de Teste de Caixa Branca
    @Test
    public void should_throw_exception_if_course_already_exists() {
        // mock para simular um curso já existente
        ICourseRepository repositoryMock = mock(ICourseRepository.class);
        when(repositoryMock.findByName("ExistingCourse")).thenReturn(new Course());

        CreateCourseService createCourseService = new CreateCourseService(repositoryMock);

        // Teste para o caminho onde o curso já existe
        assertThrows(CourseAlreadyExistsException.class, () -> {
            Course course = new Course();
            course.setName("ExistingCourse");
            createCourseService.execute(course);
        });
    }

    @Test
    public void should_create_course_if_course_does_not_exist() {
        // mock para simular um curso que não existe
        ICourseRepository repositoryMock = mock(ICourseRepository.class);
        when(repositoryMock.findByName("NonExistingCourse")).thenReturn(null);

        CreateCourseService createCourseService = new CreateCourseService(repositoryMock);

        // Teste para o caminho onde o curso não existe
        Course course = new Course();
        course.setName("NonExistingCourse");
        Course createdCourse = createCourseService.execute(course);

        assertNotNull(createdCourse.getId());
    }

    @Test
    public void should_throw_exception_for_null_or_empty_name() {
        // mock para simular um curso com nome nulo
        ICourseRepository repositoryMock = mock(ICourseRepository.class);
        CreateCourseService createCourseService = new CreateCourseService(repositoryMock);

        // Teste para o caminho onde o nome do curso é nulo
        assertThrows(IllegalArgumentException.class, () -> {
            Course course = new Course();
            course.setName(null);
            createCourseService.execute(course);
        });

        // Teste para o caminho onde o nome do curso é vazio
        assertThrows(IllegalArgumentException.class, () -> {
            Course course = new Course();
            course.setName("");
            createCourseService.execute(course);
        });
    }

    @Test
    public void should_throw_exception_for_name_length_less_than_8() {
        // mock para simular um curso com nome curto
        ICourseRepository repositoryMock = mock(ICourseRepository.class);
        CreateCourseService createCourseService = new CreateCourseService(repositoryMock);

        // Teste para o caminho onde o nome do curso tem menos de 8 caracteres
        assertThrows(IllegalArgumentException.class, () -> {
            Course course = new Course();
            course.setName("Short");
            createCourseService.execute(course);
        });
    }

    @Test
    public void should_throw_exception_for_name_length_more_than_30() {
        // mock para simular um curso com nome longo
        ICourseRepository repositoryMock = mock(ICourseRepository.class);
        CreateCourseService createCourseService = new CreateCourseService(repositoryMock);

        // Teste para o caminho onde o nome do curso tem mais de 30 caracteres
        assertThrows(IllegalArgumentException.class, () -> {
            Course course = new Course();
            course.setName("ThisIsAReallyLongCourseNameThatExceedsThirtyCharacters");
            createCourseService.execute(course);
        });
    }

    @Test
    public void should_save_and_return_new_course() {
        // mock para simular um curso que não existe
        ICourseRepository repositoryMock = mock(ICourseRepository.class);
        when(repositoryMock.findByName("NewCourse")).thenReturn(null);

        CreateCourseService createCourseService = new CreateCourseService(repositoryMock);

        // Teste para o caminho onde o curso é válido e é salvo com sucesso
        Course course = new Course();
        course.setName("NewCourse");
        Course createdCourse = createCourseService.execute(course);

        assertNotNull(createdCourse.getId());
    }

}