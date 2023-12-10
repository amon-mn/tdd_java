package br.com.tddjava.tddjava.modules.courses.services;

import org.springframework.stereotype.Service;

import br.com.tddjava.tddjava.modules.courses.entities.Course;
import br.com.tddjava.tddjava.modules.courses.exceptions.CourseAlreadyExistsException;
import br.com.tddjava.tddjava.modules.courses.repositories.ICourseRepository;

@Service
public class CreateCourseService {

    private ICourseRepository repository;

    public CreateCourseService(ICourseRepository repository) {
        this.repository = repository;
    }

    public Course execute(Course course) {
        // Validar se o curso existe pelo nome
        Course existedCourse = this.repository.findByName(course.getName());

        // Se sim - lança exceção
        if (existedCourse != null) {
            throw new CourseAlreadyExistsException("Curso já existe!");
        }

        // Validar se o nome do curso é nulo
        if (course.getName() == null) {
            throw new IllegalArgumentException("O nome do curso não pode ser nulo.");
        }

        // Validar se o nome do curso é vazio
        if (course.getName().isEmpty()) {
            throw new IllegalArgumentException("O nome do curso não pode ser vazio.");
        }

        // Validar se o nome do curso tem pelo menos 8 caracteres
        if (course.getName().length() < 8) {
            throw new IllegalArgumentException("O nome do curso deve ter pelo menos 8 caracteres.");
        }

        // Validar se o nome do curso tem no máximo 30 caracteres
        if (course.getName().length() > 50) {
            throw new IllegalArgumentException("O nome do curso não pode ter mais de 30 caracteres.");
        }

        // Se não - Salvar e retorna novo curso
        return this.repository.save(course);
    }
}
