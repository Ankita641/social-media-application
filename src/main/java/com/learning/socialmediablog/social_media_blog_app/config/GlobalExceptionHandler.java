package com.learning.socialmediablog.social_media_blog_app.config;

import com.learning.socialmediablog.social_media_blog_app.dto.ErrorDetails;
import com.learning.socialmediablog.social_media_blog_app.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

//GlobalExceptionHandler this class we design such a way to give a control to handle(globally) exception ant where or any layer in this program
//@ControllerAdvice is similar to @Component but only difference is special type of component annotation
// what exactly do will de-couple the exception(exception logic) from the controller to this place(class GlobalExceptionHandler).
//for user some give invalid data then we need validation.

@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {

//Handle all the validation Errors.
// like it give right message (in human understandable format) for validation (not server error 500) to give valid error 400 and message ("title" : "must not be empty") that way before we override this method we need extend the ResponseEntityExceptionHandler class
   //when we use @valid that place apply this logic (in PostMapping, PutMapping)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String,String> errorDetailsMap = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errorDetailsMap.put(fieldName, message);
        });
    return new ResponseEntity<>(errorDetailsMap, HttpStatus.BAD_REQUEST);
}

    //ResourceNotFoundException
    //when we give wrong data that place apply this logic (in GetMapping)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
       //2 way using constructor and create object using builder
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .details(webRequest.getDescription(true))
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    //GenericException
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(Exception exception, WebRequest webRequest){
       ErrorDetails errorDetails = ErrorDetails.builder()
               .timestamp(LocalDateTime.now())
               .message(exception.getMessage())
               .details(webRequest.getDescription(true))
               .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
               .build();
       return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
