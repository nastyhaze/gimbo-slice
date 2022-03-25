package com.nastyHaze.gimboslice.exception;


/**
 *  Custom Exception for actions performed by users without valid role requirements.
 */
public class UserRoleException extends RuntimeException{

    UserRoleException() {
        super();
    }

    public UserRoleException(String errorMessage) {
        super(errorMessage);
    }
}
