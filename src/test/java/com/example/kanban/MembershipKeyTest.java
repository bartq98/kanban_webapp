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

    private Board board;
    private User user;

    private Board mockBoardFirst;
    private User mockUserFirst;

    private Board mockBoardSecond;
    private User mockUserSecond;

    private MembershipKey membershipKeyFirst;
    private MembershipKey membershipKeySecond;

    @BeforeEach
    public void setUp() {
        this.mockUserFirst = Mockito.mock(User.class);
        this.mockBoardFirst = Mockito.mock(Board.class);

        Mockito.when(this.mockUserFirst.getId()).thenReturn(2);
        Mockito.when(this.mockBoardFirst.getId()).thenReturn(2);

        this.membershipKeyFirst = new MembershipKey(this.mockUserFirst.getId(), this.mockBoardFirst.getId());


        this.mockUserSecond = Mockito.mock(User.class);
        this.mockBoardSecond = Mockito.mock(Board.class);

        Mockito.when(mockUserSecond.getId()).thenReturn(2);
        Mockito.when(mockBoardSecond.getId()).thenReturn(2);

        this.membershipKeySecond = new MembershipKey(mockUserSecond.getId(), mockBoardSecond.getId());
    }

    @Test
    void testEquals() {
        assertTrue(this.membershipKeyFirst.equals(this.membershipKeySecond));
        assertTrue(this.membershipKeyFirst.equals(this.membershipKeyFirst));
        assertFalse(this.membershipKeyFirst.equals(null));

        Mockito.when(mockUserSecond.getId()).thenReturn(3);
        Mockito.when(mockBoardSecond.getId()).thenReturn(3);
        this.membershipKeySecond.setUser(mockUserSecond.getId());
        this.membershipKeySecond.setBoard(mockBoardSecond.getId());

        assertFalse(this.membershipKeyFirst.equals(this.membershipKeySecond));
    }

    @Test
    void testHashCode(){
        this.user = new User();
        this.user.setId(2);
        this.board = new Board();
        this.board.setId(2);

        this.membershipKeyFirst.setUser(this.user.getId());
        this.membershipKeyFirst.setBoard(this.board.getId());
        this.membershipKeySecond.setUser(this.user.getId());
        this.membershipKeySecond.setBoard(this.board.getId());
        assertNotSame(this.membershipKeyFirst, this.membershipKeySecond);
        assertEquals(this.user.hashCode(), this.board.hashCode());
        assertEquals(this.membershipKeyFirst.hashCode(), this.membershipKeySecond.hashCode());
    }
}
