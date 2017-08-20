/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estudo.agenda.api.provider;

import br.com.estudo.agenda.api.util.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author eduardo
 */
@Provider
public class BeanValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<Error> listErrors = new ArrayList<>();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            String nameProperty = getNameProperty(constraintViolation.getPropertyPath().toString());
            Error error = new Error(nameProperty, constraintViolation.getMessage());
            listErrors.add(error);
        }
        return Response.status(Response.Status.PAYMENT_REQUIRED)
                .entity(listErrors)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
    private String getNameProperty(String namePath) {
        int position = namePath.lastIndexOf(".");
        return namePath.substring(++position);
    }

}
