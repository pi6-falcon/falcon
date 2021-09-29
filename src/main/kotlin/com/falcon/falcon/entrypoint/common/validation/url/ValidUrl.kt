package com.falcon.falcon.entrypoint.common.validation.url

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidUrlValidator::class])
@MustBeDocumented
annotation class ValidUrl(
    val message: String = "URL should be a valid url",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)
