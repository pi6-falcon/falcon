package com.falcon.falcon

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator

private val validator: Validator = Validation.buildDefaultValidatorFactory().validator

data class ValidateBeans<T>(
    val input: T,
    val errorsExpected: Int
)

fun <T> T.validate(): MutableSet<ConstraintViolation<T>> = validator.validate(this)
