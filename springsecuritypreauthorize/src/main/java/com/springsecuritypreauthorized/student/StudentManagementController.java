package com.springsecuritypreauthorized.student;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

	private List<Student> students = new ArrayList<Student>();
			
	
	public StudentManagementController() {
		students.add(new Student(1, "James Bond"));
		students.add(new Student(2, "Maria Jones"));
		students.add(new Student(3, "Anna Smith"));

	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
	public List<Student> getAllStudents() {
		return students;
	}

	
	@PostMapping
	@PreAuthorize("hasAuthority('course:write')")
	public void registerNewStudent(@RequestBody Student student) {
		students.add(student);
	}

	@DeleteMapping(path = "{studentId}")
	@PreAuthorize("hasAuthority('course:write')")
	public void deleteStudent(@PathVariable("studentId") Integer studentId) {
		students.removeIf(student -> student.getStudentId() == studentId);
	}

	@PutMapping(path = "{studentId}")
	@PreAuthorize("hasAuthority('course:write')")
	public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student updatedStudent) {

		boolean found = students.stream().anyMatch(student -> student.getStudentId() == studentId);

		if (found) {
			students.stream().forEach((student) -> {

				if (student.getStudentId() == studentId) {
					student = updatedStudent;
				}
			});
		} else {
			students.add(new Student(studentId, updatedStudent.getStudentName()));
		}

	}
}
