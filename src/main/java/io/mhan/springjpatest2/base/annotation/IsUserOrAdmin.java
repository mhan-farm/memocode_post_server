package io.mhan.springjpatest2.base.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// user 또는 admin이 접근 가능
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@PreAuthorize("hasAuthority('SCOPE_role:user') or hasAuthority('SCOPE_role:admin')")
public @interface IsUserOrAdmin {}
