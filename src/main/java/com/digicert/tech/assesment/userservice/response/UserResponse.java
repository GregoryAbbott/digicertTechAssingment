package com.digicert.tech.assesment.userservice.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    /* response object so that we can control what info we would like to pass back
       to the request from the db and not necessarily pass back all columns
       can possibly add a message and HttpStatus code back here as well */

    private long id;
    private String firstName;
    private String lastName;
    private String email;
}
