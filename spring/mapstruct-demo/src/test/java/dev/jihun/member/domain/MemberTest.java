package dev.jihun.member.domain;

import dev.jihun.member.dto.MemberDto;
import dev.jihun.member.mapper.MemberMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberMapper memberMapper;

    @Test
    void memberToMemberDtoTest(){
        Member member = Member.builder().socialSecurityNumber("123456-8901234")
                .userName("userName")
                .password("password")
                .name("홍길동")
                .address("서울특별시")
                .build();

        Member savedMember = memberRepository.save(member);

        MemberDto memberDto = memberMapper.memberToMemberDto(savedMember);
        assertThat(memberDto.getUserName(),is("userName"));
        assertThat(memberDto.getName(), is("홍길동"));
        assertThat(memberDto.getAddress(), is("서울특별시"));

    }

    @Test
    void memberDtoToMember(){
        MemberDto memberDto = new MemberDto();
        memberDto.setUserNumber("123456-7890123");
        memberDto.setUserName("userName");
        memberDto.setName("홍길동");
        memberDto.setAddress("서울특별시");

        Member member = memberMapper.memberDtoToMember(memberDto);

        assertThat(member.getSocialSecurityNumber(), is("123456-7890123"));
        assertThat(member.getUserName(), is("userName"));
        assertThat(member.getName(), is("홍길동"));
        assertThat(member.getAddress(), is("서울특별시"));
    }
}
