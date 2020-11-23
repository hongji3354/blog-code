package dev.jihun.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id
    private String socialSecurityNumber;

    private String userName;

    private List<String> password;

    private String name;

    private String address;

    @Builder

    public Member(String socialSecurityNumber, String userName, List<String> password, String name, String address) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.address = address;
    }
}
