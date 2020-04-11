package com.example.kanban.entities.membership;

import com.example.kanban.entities.boards.Board;
import com.example.kanban.entities.user.User;

import javax.persistence.*;


@Entity
public class Membership {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private MemberType member_type;

    @ManyToOne
    private User user;

    @ManyToOne
    private Board board;

    public void setMember_type(MemberType member_type) {
        this.member_type = member_type;
    }

    public Integer getUserId() {
        return this.user.getId();
    }

    public Integer getBoardId() {
        return this.board.getId();
    }

    public void setBoardId(Board boardId) {
        this.board = boardId;
    }

    public void setUserId(User userId) {
        this.user = userId;
    }

    public Integer getId(){
        return this.id;
    }
}
