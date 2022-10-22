package com.marcoscouto.pocsnssqs.sqs.data;

import java.util.UUID;

public class DefaultMessage {

    private final String code;
    private final String message;

    public DefaultMessage(String message) {
        this.code = UUID.randomUUID().toString();
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String toJSON() {
        return """
            {
                "code": "%s",
                "message": "%s"
            }
            """.formatted(this.code, this.message);

    }

}
