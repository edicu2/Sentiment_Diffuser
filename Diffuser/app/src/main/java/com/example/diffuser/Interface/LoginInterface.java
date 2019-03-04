package com.example.diffuser.Interface;

public interface LoginInterface {
    interface View{
        void setView();
    }
    interface Presenter{
        void presenterView();
        void Login(String id, String pw);
    }
}
