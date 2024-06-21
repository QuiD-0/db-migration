package com.quid.inserter.member;

import com.quid.inserter.member.gateway.repository.MemberSaveRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("member")
public class MemberSaveScheduler {

    private final MemberSaveRepository member;

    public MemberSaveScheduler(MemberSaveRepository member) {
        this.member = member;
    }

    @Scheduled(fixedDelay = 1_000)
    public void execute() {
        member.save();
    }
}
