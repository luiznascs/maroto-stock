package com.javaday.maroto_stock.config.error


import com.javaday.maroto_stock.models.dtos.FieldValidationError
import com.javaday.maroto_stock.models.enums.ServiceError
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.util.*


@ControllerAdvice(annotations = [RestController::class])
class GlobalFieldExceptionHandler : ApplicationContextAware {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun validationException(exception: MethodArgumentNotValidException): FieldValidationError {
        val result = exception.bindingResult
        val mapErrors = result.fieldErrors.associate { it.field to (it.defaultMessage ?: String()) }

        return FieldValidationError(
            timestamp = Date().time,
            status = 400,
            error = HttpStatus.BAD_REQUEST.name,
            message = ServiceError.INVALID_FIELD_DATA.message,
            fieldErrors = mapErrors
        )
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) { }

}



