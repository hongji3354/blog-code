package dev.jihun;

import dev.jihun.member.domain.Member;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CollectionMapping {

    List<Integer> integerList(List<String> stringList);

}
