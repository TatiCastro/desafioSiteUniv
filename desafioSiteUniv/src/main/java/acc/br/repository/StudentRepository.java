package acc.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import acc.br.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    
}

