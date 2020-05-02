package com.example.kanban.entities.membership;
import static java.lang.System.*;
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
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 17;
        int result = (int) prime * board.hashCode() * user.hashCode();
        System.out.println(result);
        return result;
    }
}
