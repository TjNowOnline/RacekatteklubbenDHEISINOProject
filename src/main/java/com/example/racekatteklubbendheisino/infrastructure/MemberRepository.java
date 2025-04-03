package com.example.racekatteklubbendheisino.infrastructure;

import com.example.racekatteklubbendheisino.domain.Member;

public interface MemberRepository {
    Member findByEmail(String email);
    void save(Member member);
    void delete(Member member);
    void update(Member member);
}
