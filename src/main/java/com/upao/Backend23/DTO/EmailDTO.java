package com.upao.Backend23.DTO;

public record EmailDTO(String[] toUser,
                    String subject,
                    String message){

}