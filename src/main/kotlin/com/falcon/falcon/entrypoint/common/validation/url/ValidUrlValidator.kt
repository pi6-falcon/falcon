package com.falcon.falcon.entrypoint.common.validation.url

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass
import org.apache.commons.validator.routines.UrlValidator

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidUrlValidator::class])
@MustBeDocumented
annotation class ValidUrl(
    val message: String = "URL should be a valid url",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

class ValidUrlValidator(private val validator: UrlValidator) : ConstraintValidator<ValidUrl, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean =
        validator.isValid(value)
}
