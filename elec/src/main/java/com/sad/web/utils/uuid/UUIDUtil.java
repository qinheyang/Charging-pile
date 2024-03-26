package com.sad.web.utils.uuid;


import java.util.UUID;

public class UUIDUtil
{
        public static String generateUUID(){
                return UUID.randomUUID().toString().replace("-","");
        }

}
