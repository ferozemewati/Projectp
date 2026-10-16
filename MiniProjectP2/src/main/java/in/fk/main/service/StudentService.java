package in.fk.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.fk.main.Student;
import in.fk.main.dto.StudentDTO;
import in.fk.main.dto.StudentResponseDTO;
import in.fk.main.exception.ResourceNotFoundException;
import in.fk.main.repository.StudentRepository;

import org.springframework.data.domain.*;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    // =========================
    // ✅ COMMON MAPPER (NEW 🔥)
    // =========================
    private StudentResponseDTO mapToDTO(Student s) {
        return new StudentResponseDTO(
                s.getId(),
                s.getName(),
                s.getEmail()
        );
    }

    // =========================
    // ✅ CREATE
    // =========================
    public StudentResponseDTO saveStudent(StudentDTO dto) {

        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());

        Student saved = repo.save(student);

        return mapToDTO(saved); // 🔥 reused
    }

    // =========================
    // ✅ GET ALL
    // =========================
    public List<StudentResponseDTO> getAllStudents() {

        return repo.findAll()
                .stream()
                .map(this::mapToDTO) // 🔥 cleaner
                .toList();
    }

    // =========================
    // ✅ GET BY ID
    // =========================
    public StudentResponseDTO getStudentById(Long id) {

        Student s = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        return mapToDTO(s);
    }

    // =========================
    // ✅ GET BY NAME
    // =========================
    public List<StudentResponseDTO> getStudentByName(String name) {

        return repo.findByName(name)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // =========================
    // ✅ UPDATE
    // =========================
    public StudentResponseDTO updateStudent(Long id, StudentDTO dto) {

        Student existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());

        Student updated = repo.save(existing);

        return mapToDTO(updated);
    }

    // =========================
    // ✅ DELETE
    // =========================
    public void deleteStudent(Long id) {

        Student existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        repo.delete(existing); // 🔥 safer than existsById
    }

    // =========================
    // ✅ PAGINATION + SORTING
    // =========================
    public Page<StudentResponseDTO> getStudentsWithPagination(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repo.findAll(pageable)
                .map(this::mapToDTO); // 🔥 clean mapping
    }
}	