package cha.friendly.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name; //nickname . 중복 불가
    private String email; //계정 id . 중복 불가
    private String password;

    @Embedded
    private Address Address; //회원가입 시 받지 않는다. null가능

    private String phoneNumber; //1인 1번호. 중복 불가

    @Enumerated(EnumType.STRING)
    private Role role; //1개의 계정은 1개의 역할만 같는다.

    /**
     ryu코드 추가
     */
    private String category; //1개

    private Long minPrice; //선호 가격
    private Long maxPrice;

    private String time; //선호 시간

    private int matchCnt = 0; //매칭된 인원 수(최대 3)

    private String latelyuser; //최근 매칭된 사람

    private int is_blocked; //정지유무

//    @OneToMany(mappedBy = "member") //order테이블에 있는 member를 뜻한다.
//    private List<Order> orders = new ArrayList<>();
}
