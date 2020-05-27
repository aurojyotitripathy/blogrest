package com.nextcloudapps.blogapi.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class CommentRequest implements Serializable {
    @NotBlank
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
