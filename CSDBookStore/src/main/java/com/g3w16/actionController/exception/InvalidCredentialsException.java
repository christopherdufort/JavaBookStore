/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.actionController.exception;

/**
 *
 * @author jesuisnuageux
 */
public class InvalidCredentialsException extends Exception{
    // TODO: Should use the internationalization bundle I guess
    @Override
    public String toString(){
        return "Invalid email or adress";
    }
}
