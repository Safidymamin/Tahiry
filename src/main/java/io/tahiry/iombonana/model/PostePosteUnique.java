package io.tahiry.iombonana.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import io.tahiry.iombonana.service.PosteService;
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
 * Validate that the poste value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = PostePosteUnique.PostePosteUniqueValidator.class
)
public @interface PostePosteUnique {

    String message() default "{Exists.poste.poste}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class PostePosteUniqueValidator implements ConstraintValidator<PostePosteUnique, String> {

        private final PosteService posteService;
        private final HttpServletRequest request;

        public PostePosteUniqueValidator(final PosteService posteService,
                final HttpServletRequest request) {
            this.posteService = posteService;
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
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equalsIgnoreCase(posteService.get(Long.parseLong(currentId)).getPoste())) {
                // value hasn't changed
                return true;
            }
            return !posteService.posteExists(value);
        }

    }

}
