package it.alma.geditor.annotations.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

import it.alma.geditor.constants.SecurityConstants;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize(SecurityConstants.IS_ADMINISTRATOR_CHECK)
public @interface IsAdminPreAutorized {}