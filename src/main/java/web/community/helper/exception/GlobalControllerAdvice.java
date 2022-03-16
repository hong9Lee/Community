package web.community.helper.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import web.community.helper.enums.ResponseStatusType;
import web.community.helper.util.Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
    }


    @ExceptionHandler({
            NullPointerException.class,
            NoSuchElementException.class, // db에 데이터가 없는 경우
            EmptyResultDataAccessException.class, // deleteItem Id에 해당하는 Item이 존재하지 안흔ㄴ 경우
            HttpRequestMethodNotSupportedException.class, // Request 요청을 잘못한 경우(POST, GET)
            IndexOutOfBoundsException.class,
            IllegalStateException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity InternalServerException(Exception e, HttpServletRequest httpServletRequest) {
        log.error(e.getMessage());
        String name = e.getClass().getName();

        List<Error> errorList = new ArrayList<>();
        errorList.add(Utils.getErrMsg(name + "(" + e.getMessage() + ")", Utils.stackTractToString(e)));

        ErrorResponse errResponse = Utils.getErrResponse(errorList,
                httpServletRequest, ResponseStatusType.INTERNAL_SERVER_ERROR);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errResponse);

    }

    /**
     * @valid  유효성체크에 통과하지 못하면 MethodArgumentNotValidException 발생.
     */
    @ExceptionHandler({MethodArgumentNotValidException.class,
            MissingRequestHeaderException.class})
    public ResponseEntity BadRequestException(MethodArgumentNotValidException e, HttpServletRequest httpServletRequest) {
        log.error(e.getMessage());

        List<Error> errorList = new ArrayList<>();

        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getAllErrors().forEach(error -> {

            FieldError field = (FieldError) error;
            String fieldName = field.getField();
            String message = field.getDefaultMessage();

            errorList.add(Utils.getErrMsg(fieldName, message));
        });

        ErrorResponse errResponse = Utils.getErrResponse(errorList,
                httpServletRequest, ResponseStatusType.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResponse);
    }




}
