package com.manual.billing.exception

import com.manual.billing.dto.response.ErrorResponse
import com.manual.billing.dto.response.FieldError
import jakarta.persistence.EntityNotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    // 404 — resource does not exist
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(
        ex: ResourceNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        log.warn("Resource not found: {}", ex.message)
        return build(HttpStatus.NOT_FOUND, ex.message, request)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(
        ex: EntityNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        log.warn("Entity not found: {}", ex.message)
        return build(HttpStatus.NOT_FOUND, ex.message, request)
    }

    // 409 — request is well-formed but conflicts with current state
    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalState(
        ex: IllegalStateException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        log.warn("Business rule violation: {}", ex.message)
        return build(HttpStatus.CONFLICT, ex.message, request)
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException::class)
    fun handleOptimisticLock(
        ex: ObjectOptimisticLockingFailureException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        log.warn("Optimistic locking failure: {}", ex.message)
        return build(
            HttpStatus.CONFLICT,
            "This record was modified by another request. Please reload and try again.",
            request
        )
    }

    // 400 — bad input from the caller
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(
        ex: IllegalArgumentException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        log.warn("Invalid argument: {}", ex.message)
        return build(HttpStatus.BAD_REQUEST, ex.message, request)
    }

    // 500 — anything unexpected. Never leak internal details to the client.
    @ExceptionHandler(Exception::class)
    fun handleUnexpected(
        ex: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        log.error("Unhandled exception on {}", request.requestURI, ex)
        return build(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred. Please try again later.",
            request
        )
    }

    // Spring MVC's own exceptions (bean validation, bad JSON, etc.)
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val fieldErrors = ex.bindingResult.fieldErrors.map {
            FieldError(it.field, it.defaultMessage)
        }
        log.warn("Validation failed: {}", fieldErrors)

        val body = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = "Validation failed for one or more fields",
            path = path(request),
            fieldErrors = fieldErrors
        )
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        log.warn("Malformed request body: {}", ex.message)
        val body = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = "Malformed JSON request body",
            path = path(request)
        )
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val body = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = ex.message,
            path = path(request)
        )
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleTypeMismatch(
        ex: MethodArgumentTypeMismatchException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val message = "Parameter '${ex.name}' should be of type ${ex.requiredType?.simpleName}"
        log.warn(message)
        return build(HttpStatus.BAD_REQUEST, message, request)
    }

    // helpers
    private fun build(
        status: HttpStatus,
        message: String?,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse(
            status = status.value(),
            error = status.reasonPhrase,
            message = message,
            path = request.requestURI
        )
        return ResponseEntity(body, status)
    }

    private fun path(request: WebRequest): String =
        request.getDescription(false).removePrefix("uri=")
}