package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.assertj.core.api.Assertions.*;


class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("dave");

        //when
        Long memberId = memberService.join(member);

        //then
        assertThat(member.getId()).isEqualTo(memberId);
    }

    @Test
    public void 중복_회원_에러() {
        //given
        Member member = new Member();
        member.setName("dave");
        Member member2 = new Member();
        member2.setName("dave");

        //when
        memberService.join(member);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void findMembers() {
        Member member = new Member();
        member.setName("dave");
        Member member2 = new Member();
        member2.setName("leo");

        memberService.join(member);
        memberService.join(member2);

        List<Member> members = memberService.findMembers();
        assertThat(members.size()).isEqualTo(2);
        assertThat(members.get(0).getName()).isEqualTo(member.getName());
    }

    @Test
    void findOne() {
        Member member = new Member();
        member.setName("dave");

        memberService.join(member);

        Optional<Member> result = memberService.findOne(member.getId());

        result.ifPresent(value -> assertThat(value.getName()).isEqualTo(member.getName()));
    }
}