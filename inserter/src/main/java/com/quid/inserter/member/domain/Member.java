package com.quid.inserter.member.domain;

import java.time.LocalDateTime;

import static com.quid.inserter.member.domain.Level.BRONZE;

public record Member(
    Long memberSeq,
    String memberId,
    String password,
    String name,
    Level level,
    LocalDateTime regDate,
    LocalDateTime modDate
) {

    public Member(String memberId, String password, String name) {
        this(null, memberId, password, name, BRONZE, LocalDateTime.now(), LocalDateTime.now());
        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException("memberId must not be null or empty");
        }
    }
}
