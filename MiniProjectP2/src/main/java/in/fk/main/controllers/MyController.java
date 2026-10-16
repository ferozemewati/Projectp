package in.fk.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.fk.main.dto.StudentDTO;
import in.fk.main.dto.StudentResponseDTO;
import in.fk.main.response.ApiResponse;
import in.fk.main.service.StudentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class MyController {

    @Autowired
    private StudentService studentService;

    // CREATE
    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponseDTO>> save(
            @RequestBody @Valid StudentDTO dto) {

        StudentResponseDTO s = studentService.saveStudent(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("success", "Student created", s));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponseDTO>>> getStudents() {
        return ResponseEntity.ok(
                new ApiResponse<>("success", "All students",
                        studentService.getAllStudents())
        );
    }

    // ✅ FIXED (IMPORTANT)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> getStudent(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>("success", "Student found",
                        studentService.getStudentById(id))
        );
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> updateStudent(
            @PathVariable Long id,
            @RequestBody @Valid StudentDTO dto) {

        return ResponseEntity.ok(
                new ApiResponse<>("success", "Student updated",
                        studentService.updateStudent(id, dto))
        );
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStudent(
            @PathVariable Long id) {

        studentService.deleteStudent(id);

        return ResponseEntity.ok(
                new ApiResponse<>("success", "Student deleted",
                        "Deleted successfully")
        );
    }
}