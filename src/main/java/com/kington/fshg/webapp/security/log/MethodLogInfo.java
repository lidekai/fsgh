/**
 * 
 */
package com.kington.fshg.webapp.security.log;

import java.lang.annotation.Documented;  
import java.lang.annotation.Inherited;  
import java.lang.annotation.Retention;  
import java.lang.annotation.Target;  
import java.lang.annotation.ElementType;  
import java.lang.annotation.RetentionPolicy; 


/**
 * @author Van Cheng
 *
 */
@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
@Inherited
public @interface MethodLogInfo {
    public String name() default "";
    
}
