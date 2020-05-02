package com.example.kanban;

import com.example.kanban.entities.boards.Board;
import com.example.kanban.entities.membership.MembershipKey;
import com.example.kanban.entities.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MembershipKeyTest{

    private static MembershipKey membershipKey;

    @BeforeAll
    static void setup() {
        membershipKey = new MembershipKey(Mockito.mock(User.class), Mockito.mock(Board.class));
        System.out.println("HEJ");
        membershipKey.getUser().setId(2);
        membershipKey.getBoard().setId(2);
    }


    @Test
    public void testEquals() {
        MembershipKey membershipKeyForTesting = new MembershipKey(Mockito.mock(User.class), Mockito.mock(Board.class));
        membershipKeyForTesting.getUser().setId(2);
        membershipKeyForTesting.getBoard().setId(2);

        assertTrue(membershipKey.equals(membershipKeyForTesting));
    }
}
