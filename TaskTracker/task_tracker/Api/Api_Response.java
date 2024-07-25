package org.crud.task_tracker.Api;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Api_Response {
    private String message;
    private String status;
}
