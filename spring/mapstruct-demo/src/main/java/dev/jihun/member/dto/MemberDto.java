package dev.jihun.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {

    private String userNumber;

    private String userName;

    private String name;

    private String address;
}
