package com.example.kanban.entities.membership;
import static java.lang.System.*;
import com.example.kanban.entities.boards.Board;
import com.example.kanban.entities.user.User;

import java.io.Serializable;

public class MembershipKey implements Serializable {

    private Integer user;
    private Integer board;

    public MembershipKey(){}

    public MembershipKey(Integer user, Integer board){
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
        return (mk.user.equals(this.user)) || (mk.board.equals(this.board));
    }

    @Override
    public int hashCode() {
        final int prime = 17;
        int result = (int) prime * board.hashCode() * user.hashCode();
        return result;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getBoard() {
        return board;
    }

    public void setBoard(Integer board) {
        this.board = board;
    }
}
