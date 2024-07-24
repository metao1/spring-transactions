package com.metao.transaction;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class StudentTest {

    @Autowired
    StudentService studentService;

    public static Stream<Student> provideStudentStream() {
        return Stream.of(
            new Student(),
            new Student(1L, null)
        );
    }

    private static Address provideAddress() {
        return new Address("Berliner str.", "Berlin", "12");
    }

    @Test
    @Transactional
    void saveAndGetStudent() {
        Student st1 = createStudent(1L, "Mehrdad");
        Student st2 = createStudent(2L, "Ali");

        studentService.save(st1);
        studentService.save(st2);

        assertStudent(st1);
        assertStudent(st2);
    }

    private Student createStudent(Long id, String name) {
        Student student = new Student(id, name);
        student.addAddress(provideAddress());
        return student;
    }

    private void assertStudent(Student expected) {
        Student actual = studentService.getById(expected.getId()).orElseThrow();

        assertEquals(expected.getName(), actual.getName());
        Iterator<Address> expectedIterator = expected.getAddresses().iterator();
        Iterator<Address> actualIterator = actual.getAddresses().iterator();

        while (expectedIterator.hasNext()) {
            Address expectedAddress = expectedIterator.next();
            Address actualAddress = actualIterator.next();

            assertEquals(expectedAddress.getStudent(), actualAddress.getStudent());
        }
    }

    @Test
    void updateTest() {
        var st = new Student(1L, "Mehrdad");
        st.addAddress(provideAddress());
        studentService.save(st);
        st.setName(null);
        assertThrows(RuntimeException.class, () -> studentService.updateStudent(st));
        assertThat(studentService.getById(1L))
            .hasValueSatisfying(student -> {
                assertEquals("Mehrdad", student.getName());
            });
    }

    @Test
    @Transactional
    void updateStudentAddressTest() {
        var st = new Student(1L, "Mehrdad");
        var address = provideAddress();
        st.addAddress(address);
        studentService.save(st);
        address.setStreet("NEW_STREET");
        st.updateAddress(address);
        assertThat(studentService.getById(1L).orElseThrow())
            .satisfies(student -> {
                assertEquals("NEW_STREET", student.getAddresses().stream().findFirst().orElseThrow().getStreet());
            });
    }

    @Test
    @Transactional
    void deleteStudentAddressSuccessfulTest() {
        var st = new Student(1L, "Mehrdad");
        var address = provideAddress();
        st.addAddress(address);
        studentService.save(st);
        st.getAddresses().remove(address);
        assertThat(studentService.getById(1L).orElseThrow())
            .satisfies(student -> {
                assertTrue(student.getAddresses().isEmpty());
            });
    }

    @Transactional
    @MethodSource("provideStudentStream")
    @ParameterizedTest(name = "update student with different wrong variables")
    void updateStudentRollbacksTest(Student st) {
        assertThrows(RuntimeException.class, () -> studentService.save(st));
    }
}