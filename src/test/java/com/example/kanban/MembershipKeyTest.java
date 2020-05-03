package com.example.kanban;

import com.example.kanban.entities.boards.Board;
import com.example.kanban.entities.membership.MembershipKey;
import com.example.kanban.entities.user.User;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;


class MembershipKeyTest{

    private MembershipKey membershipKey1;
    private MembershipKey membershipKey2;

    private Board board;
    private User user;

    @BeforeEach
    public void setUp() {
        User user1 = Mockito.mock(User.class);
        Board board1 = Mockito.mock(Board.class);
        Mockito.when(user1.getId()).thenReturn(2);
        Mockito.when(board1.getId()).thenReturn(2);
        this.membershipKey1 = new MembershipKey(user1, board1);

        User user2 = Mockito.mock(User.class);
        Board board2 = Mockito.mock(Board.class);
        Mockito.when(user2.getId()).thenReturn(2);
        Mockito.when(board2.getId()).thenReturn(2);
        this.membershipKey2 = new MembershipKey(user2, board2);
    }

    @Test
    void testEquals() {
        assertTrue(this.membershipKey1.equals(this.membershipKey2));
        assertTrue(this.membershipKey1.equals(this.membershipKey1));
        assertFalse(this.membershipKey1.equals(null));


        User user2 = Mockito.mock(User.class);
        Board board2 = Mockito.mock(Board.class);
        Mockito.when(user2.getId()).thenReturn(3);
        Mockito.when(board2.getId()).thenReturn(3);
        this.membershipKey2.setUser(user2);
        this.membershipKey2.setBoard(board2);
        assertFalse(this.membershipKey1.equals(this.membershipKey2));
    }

    @Test
    void testHashCode(){

        this.user = new User();
        this.user.setId(2);
        this.board = new Board();
        this.board.setId(2);

        this.membershipKey1.setUser(this.user);
        this.membershipKey1.setBoard(this.board);
        this.membershipKey2.setUser(this.user);
        this.membershipKey2.setBoard(this.board);
        assertNotSame(this.membershipKey1, this.membershipKey2);
        assertEquals(this.user.hashCode(), this.board.hashCode());
        assertEquals(this.membershipKey1.hashCode(), this.membershipKey2.hashCode());
    }
}
