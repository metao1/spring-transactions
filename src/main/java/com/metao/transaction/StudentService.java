package com.metao.transaction;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public void save(Student st) {
        studentRepository.save(st);
    }

    @Transactional(readOnly = true)
    public Optional<Student> getById(Long id) {
        return studentRepository.findById(id);
    }

    public void updateStudent(Student st) {
        studentRepository.updateStudent(st.getId(), st.getName());
    }
}
