package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberRepositoryIntegrationTest {
    @Autowired
    MemberRepository repository;

    @Test
    public void save() {
        Member member = new Member();
        member.setName("Dave");

        repository.save(member);

        Optional<Member> result = repository.findById(member.getId());

        result.ifPresent(value -> {
            assertThat(value.getId()).isEqualTo(member.getId());
        });
    }

    @Test
    public void findById() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Optional<Member> result = repository.findById(member1.getId());

        result.ifPresent(member -> assertThat(member.getId()).isEqualTo(member1.getId()));
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Optional<Member> result = repository.findByName(member1.getName());

        result.ifPresent(member -> assertThat(member.getName()).isEqualTo(member1.getName()));
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
