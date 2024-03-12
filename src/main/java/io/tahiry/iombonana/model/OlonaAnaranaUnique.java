package io.tahiry.iombonana.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import io.tahiry.iombonana.service.OlonaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the anarana value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = OlonaAnaranaUnique.OlonaAnaranaUniqueValidator.class
)
public @interface OlonaAnaranaUnique {

    String message() default "{Exists.olona.anarana}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class OlonaAnaranaUniqueValidator implements ConstraintValidator<OlonaAnaranaUnique, String> {

        private final OlonaService olonaService;
        private final HttpServletRequest request;

        public OlonaAnaranaUniqueValidator(final OlonaService olonaService,
                final HttpServletRequest request) {
            this.olonaService = olonaService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("idOlona");
            if (currentId != null && value.equalsIgnoreCase(olonaService.get(Long.parseLong(currentId)).getAnarana())) {
                // value hasn't changed
                return true;
            }
            return !olonaService.anaranaExists(value);
        }

    }

}
