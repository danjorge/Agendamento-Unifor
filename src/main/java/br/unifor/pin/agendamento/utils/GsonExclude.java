/**
 * 
 */
package br.unifor.pin.agendamento.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Daniel
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD, ElementType.METHOD})
public @interface GsonExclude {

}
