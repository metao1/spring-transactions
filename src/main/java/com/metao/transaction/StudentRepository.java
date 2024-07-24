package com.metao.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Modifying
    @Query("UPDATE student s SET s.name = :name WHERE s.id = :id")
    void updateStudent(@Param("id") Long id, @Param("name") String name);

    @Modifying
    void deleteById(Long id);

}
