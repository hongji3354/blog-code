package dev.jihun.member.mapper;

import dev.jihun.member.domain.Member;
import dev.jihun.member.dto.MemberDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface MemberMapper {

    MemberDto memberToMemberDto(Member member);

    @Mappings(@Mapping(source = "userNumber", target = "socialSecurityNumber"))
    Member memberDtoToMember(MemberDto memberDto);

    Member memberDtoPasswordToMemberPassword(String dummy, List<String> password);

}
