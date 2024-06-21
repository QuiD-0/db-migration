package com.quid.inserter.member.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberTest {

    @Test
    void test() {
        assertThrows(IllegalArgumentException.class, () -> new Member("", "test", "test"));
    }

}