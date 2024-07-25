package org.crud.task_tracker.Controller;

import org.crud.task_tracker.Api.Api_Response;
import org.crud.task_tracker.Model.Task;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/tasks_tracker")

public class TaskController {

    ArrayList<Task> tasks = new ArrayList<>();
    int idCounter = 1; //to make id an incremental value


    @GetMapping("/get")
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    @GetMapping("/get/{string}")
    public ArrayList<Task> getTaskByTitle(@PathVariable String string) {
        ArrayList<Task> foundTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getTitle().contains(string)){
                foundTasks.add(task);
            }
        }
        return foundTasks;
    }

    @PostMapping("/post")
    public Api_Response addTask(@RequestBody Task task) {
        task.setId(idCounter);
        task.setStatus("Not done");
        tasks.add(task);
        idCounter++;
        return new Api_Response("Task added successfully","200");
    }

    @PutMapping("/update/task/{index}")
    public Api_Response updateTask(@PathVariable int index, @RequestBody Task task) {
        tasks.set(index, task);
        return new Api_Response("Task updated successfully","200");
    }

    @PutMapping("/update/status/automatic/{index}")
    public Api_Response updateTaskStatus(@PathVariable int index) {

        if (tasks.get(index).getStatus().equals("Done")){
            tasks.get(index).setStatus("Not done");
        }else {
            tasks.get(index).setStatus("Done");
        }
        return new Api_Response("Task status updated successfully","200");
    }

    @DeleteMapping("/delete/{index}")
    public Api_Response deleteTask(@PathVariable int index) {
        tasks.remove(index);
        return new Api_Response("Task deleted successfully","200");
    }

}
