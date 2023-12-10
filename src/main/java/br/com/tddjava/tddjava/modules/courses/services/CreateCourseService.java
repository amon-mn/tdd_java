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
        // Validar se o nome do curso tem no máximo 50 caracteres
        if (course.getName().length() > 50) {
            throw new IllegalArgumentException("O nome do curso não pode ter mais de 50 caracteres.");
        }

        // Validar se a descrição de curso é nula
        if (course.getDescription() == null) {
            throw new IllegalArgumentException("A descrição do curso não pode ser nula.");
        }
        // Validar se a descrição do curso é vazia
        if (course.getDescription().isEmpty()) {
            throw new IllegalArgumentException("A descrição do curso não pode ser vazia.");
        }
        // Validar se a descrição de curso tem pelo menos 10 caracteres
        if (course.getDescription().length() < 10) {
            throw new IllegalArgumentException("A descrição do curso deve ter pelo menos 10 caracteres.");
        }
        // Validar se a descrição de curso tem no máximo 500 caracteres
        if (course.getDescription().length() > 500) {
            throw new IllegalArgumentException("A descrição do curso não pode ter mais de 500 caracteres.");
        }

        // Valida se a carga horária do curso é nula
        if (course.getWorkload() == null){
            throw new IllegalArgumentException("A carga horária não pode ser nula.");
        }
        // Valida se a carga horária do curso tem pelo menos 20 horas
        if (course.getWorkload() < 20){
            throw new IllegalArgumentException("A carga horária deve ter pelo menos 20 horas.");
        }
        // Valida se a carga horária do curso tem no máximo 120 horas
        if (course.getWorkload() > 120){
            throw new IllegalArgumentException("A carga horária não pode ter mais que 120 horas.");
        }
        
        // Se não - Salvar e retorna novo curso
        return this.repository.save(course);
    }
}
