package vn.edu.vnua.fita.student.domain.exception;

import lombok.Data;

@Data
public class TokenExpired extends RuntimeException {
    public TokenExpired(String message) {
        super(message);
    }
}
