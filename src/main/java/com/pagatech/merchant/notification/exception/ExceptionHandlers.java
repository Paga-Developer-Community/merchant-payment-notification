package com.pagatech.merchant.notification.exception;



import com.pagatech.merchant.notification.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;


@Slf4j
@ControllerAdvice
public class ExceptionHandlers {

    private MessageSource messageSource;

    public ExceptionHandlers(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(PaymentNotificationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response handlePaymentNotificationError(final PaymentNotificationException ex) {
        log.error(ex.getMessage());

        Response response = new Response();

        response.setStatus("SERVER_ERROR");

        return response;

    }

    @ExceptionHandler(ModelAlreadyExistException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response handleModelAlreadyExistError(final ModelAlreadyExistException ex) {
        log.error(ex.getMessage());

        Response response = new Response();

        response.setStatus("CLIENT_ERROR");

        return response;

    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response handleGeneralException(final Exception ex) {

        Response response = new Response();

        response.setStatus("SERVER_ERROR");

        return response;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response processValidationError(MethodArgumentNotValidException ex) {

        BindingResult result = ex.getBindingResult();

        FieldError error = result.getFieldError();

        return processFieldError(error);
    }

    private Response processFieldError(FieldError error) {

        Locale currentLocale = LocaleContextHolder.getLocale();

        String msg = messageSource.getMessage(error.getDefaultMessage(),
                null,
                currentLocale);

        log.info("message {} ", msg);

        Response response = new Response();

        response.setStatus("SERVER_ERROR");

        return response;
    }






}
