package edu.ntut.project_01.homegym.repository;

import edu.ntut.project_01.homegym.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Integer> {

    boolean existsMemberByEmail(String email);
    Optional<Member> findMemberByEmail(String email);
    Optional<Member> findMemberByCode(String code);
    Optional<Member> findMemberByMemberId(Integer id);
}
