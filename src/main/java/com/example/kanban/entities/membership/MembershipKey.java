package com.example.kanban.entities.membership;

import com.example.kanban.entities.boards.Board;
import com.example.kanban.entities.user.User;

import java.io.Serializable;

public class MembershipKey implements Serializable {

    private User user;
    private Board board;

    public MembershipKey(){}

    public MembershipKey(User user, Board board){
        this.user = user;
        this.board = board;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()){
            return false;
        }
        MembershipKey mk = (MembershipKey) obj;
        return (mk.user.getId().equals(this.user.getId())) || (mk.board.getId().equals(this.board.getId()));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}
