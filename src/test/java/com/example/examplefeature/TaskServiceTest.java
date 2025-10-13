package com.example.examplefeature;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
class TaskServiceTest {

    @Autowired
    TaskService taskService;

    @Test
    public void tasks_are_stored_in_the_database_with_the_current_timestamp() {
        var before = Instant.now(); // guardar o tempo antes de criar
        var due = LocalDate.of(2025, 2, 7);

        taskService.createTask("Do this", due);

        var after = Instant.now(); // guardar o tempo depois de criar

        assertThat(taskService.list(PageRequest.ofSize(1))).singleElement()
                .matches(task -> task.getDescription().equals("Do this")
                        && due.equals(task.getDueDate())
                        && !task.getCreationDate().isBefore(before)
                        && !task.getCreationDate().isAfter(after));
    }


    @Test
    public void tasks_are_validated_before_they_are_stored() {
        assertThatThrownBy(() -> taskService.createTask("X".repeat(Task.DESCRIPTION_MAX_LENGTH + 1), null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
