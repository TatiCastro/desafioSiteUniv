package acc.br.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import acc.br.Service.CepService;
import acc.br.model.Student;
import acc.br.repository.StudentRepository;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CepService viaCepClient;

    @GetMapping("/registration")
    public ModelAndView showRegistrationForm() {
        ModelAndView modelAndView = new ModelAndView("studentRegistration");

        modelAndView.addObject("student", new Student());
        return modelAndView;
    }

    @PostMapping("/registration")
    public String submitRegistration(@ModelAttribute Student student, Model model) {
        try {
            Map<String, String> address = viaCepClient.getAddressByZipCode(student.getZipCode());
            student.setCity(address.get("localidade"));
        } catch (Exception e) {
            model.addAttribute("error", "Invalid ZIP Code");
            return "studentRegistration";
        }
        model.addAttribute("student", student);
        return "reviewRegistration";
    }

    @PostMapping("/confirm")
    public String confirmRegistration(@ModelAttribute Student student) {
        studentRepository.save(student);
        return "registrationSuccess";
    }

    @PostMapping("/edit")
    public String editRegistration(@ModelAttribute Student student, Model model) {
        model.addAttribute("student", student);
        return "studentRegistration";
    }

    @PostMapping("/cancel")
    public String cancelRegistration() {
        return "registrationCancelled";
    }

    //CRUD Endpoints

    @GetMapping("/list")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return studentRepository.findById(id).map(student -> {
            student.setFullName(updatedStudent.getFullName());
            student.setBirthDate(updatedStudent.getBirthDate());
            student.setZipCode(updatedStudent.getZipCode());
            student.setCity(updatedStudent.getCity());
            student.setCourse(updatedStudent.getCourse());
            studentRepository.save(student);
            return ResponseEntity.ok(student);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}