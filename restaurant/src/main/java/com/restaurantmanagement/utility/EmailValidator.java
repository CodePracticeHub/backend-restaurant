package com.restaurantmanagement.utility;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }

        // Simple email format validation using regex
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);

        return pattern.matcher(email).matches();
    }
}
