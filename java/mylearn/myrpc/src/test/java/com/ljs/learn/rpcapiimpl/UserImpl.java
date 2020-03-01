package com.ljs.learn.rpcapiimpl;

import com.ljs.learn.rpcapi.UserInterface;

public class UserImpl implements UserInterface {

    @Override
    public String createUser(String userName) {
        return "created user : " + userName;
    }
}
