package com.quid.inserter.member.gateway.repository;

import com.quid.inserter.member.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
public class MemberSaveRepository {

    private final JdbcClient jdbcClient;

    public MemberSaveRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Transactional
    public void save() {

        Member member = new Member("test", "test", "test");
        String sql = "INSERT INTO member (member_id, password, name, level, reg_date, mod_date) VALUES (?, ?, ?, ?, ?, ?)";

        log.info("===== MemberSaveRepository.save =====");
        jdbcClient.sql(sql)
            .params(
                member.memberId(),
                member.password(),
                member.name(),
                member.level().name(),
                member.regDate(),
                member.modDate()
                )
            .update();
        log.info("===== MemberSaveRepository.save end =====");
    }
}
