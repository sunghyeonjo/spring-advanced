package sh.springcore.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {

    private Long id;
    private String name;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
    }

}
