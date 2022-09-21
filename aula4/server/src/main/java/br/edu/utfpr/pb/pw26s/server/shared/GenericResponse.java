package br.edu.utfpr.pb.pw26s.server.shared;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericResponse {

    private String message;

    public GenericResponse(String message) {
        this.message = message;
    }
}
