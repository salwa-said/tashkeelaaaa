package com.omanfootball.manager.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;



@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationErrors(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .distinct()
                .collect(java.util.stream.Collectors.joining("; "));
        return ResponseEntity.badRequest().body(message.isBlank() ? "Please check the submitted form." : message);
    }





    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(

            ResourceNotFoundException ex

    ){



        return new ResponseEntity<>(

                ex.getMessage(),

                HttpStatus.NOT_FOUND

        );



    }







    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(

            Exception ex

    ){



        return new ResponseEntity<>(

                ex.getMessage(),

                HttpStatus.BAD_REQUEST

        );



    }



}
