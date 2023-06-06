package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.NoticeContent;

@Component
public class NoticeContentAddValidator implements Validator {
    public boolean supports(Class<?> clazz) {
        return NoticeContent.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        System.out.println(((NoticeContent) target).getContent());
    }
}
