package acc.br.desafioSiteUniv;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import acc.br.model.Student;
import acc.br.repository.StudentRepository;

@SpringBootTest
class DesafioSiteUnivApplicationTests {

	@Autowired
    private StudentRepository studentRepository;

	@Test
	void contextLoads() {
	}

	@Test
    void testStudentSaveAndRetrieve() {
        Student student = new Student("Tatiana de Castro Pimentel", LocalDate.of(2000, 4, 1), "01001000", "Sao Paulo", "Engenharia da Computação");
        studentRepository.save(student);

        Student retrieved = studentRepository.findById(student.getId()).orElse(null);

        assertThat(retrieved).isNotNull();
        assertThat(retrieved.getFullName()).isEqualTo("Tatiana de Castro Pimentel");
    }

}
