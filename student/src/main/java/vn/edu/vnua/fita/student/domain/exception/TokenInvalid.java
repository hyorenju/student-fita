package vn.edu.vnua.fita.student.domain.exception;

import lombok.Data;

@Data
public class TokenInvalid extends RuntimeException {
    public TokenInvalid(String message) {
        super(message);
    }
}
