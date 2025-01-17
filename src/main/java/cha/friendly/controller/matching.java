package cha.friendly.controller;

import cha.friendly.domain.Advicerequest;
import cha.friendly.domain.Dto.MatchingDto;
import cha.friendly.domain.Dto.MatchingMentorDto;
import cha.friendly.domain.Dto.MentorWaitingDto;
import cha.friendly.domain.Dto.UserWaitingDto;
import cha.friendly.domain.MatchingHistory;
import cha.friendly.domain.Member;
import cha.friendly.repository.AdviceRequestCRUDRepository;
import cha.friendly.repository.MatchHistoryRepository;
import cha.friendly.repository.MemberCRUDRepository;
import cha.friendly.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class matching {
    private final AdviceRequestCRUDRepository adviceRequestCRUDRepository;
    private final MemberCRUDRepository memberCRUDRepository;
    private final MatchHistoryRepository matchHistoryRepository;

    @GetMapping("/matching/success/getList")
    public List<Advicerequest> getSuccessList(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        List<Advicerequest> success = adviceRequestCRUDRepository.findSuccess();
        return success;
    }

    @GetMapping("/matching/success/getList/login")
    public List<MatchingHistory> getSuccessListLogin(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        List<MatchingHistory> byName = matchHistoryRepository.findByUsername(loginMember.getName());
        return byName;
    }

    @GetMapping("/matching/success/getList/login/mentor")
    public List<MatchingHistory> getSuccessListLoginMentor(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        List<MatchingHistory> byName = matchHistoryRepository.findByMatchedname(loginMember.getName());
        return byName;
    }

    @GetMapping("/matching/success/login/{id}")
    public MatchingHistory getSuccessLoginByNo(@PathVariable String id,
                                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        Optional<MatchingHistory> byId = matchHistoryRepository.findById(Long.valueOf(id));
        if (byId.isPresent()) {
            MatchingHistory matchingHistoryById = byId.get();
            return matchingHistoryById;
        } else {
            System.out.println("MatchingHistory not found");
        }
        return null;
    }

    @PostMapping(value = "/matchinglist")
    public List<Member> matchinglist(@RequestBody MatchingDto matchingDto){
        Advicerequest requestval = adviceRequestCRUDRepository.findByRequest(Long.valueOf(matchingDto.getRequest_id()));
        String one, two, three;
        one= requestval.getOne();
        two = requestval.getTwo();
        three = requestval.getThree();
        System.out.println(requestval.getOnoffline());
        int remote;
        if (requestval.getOnoffline().equals("대면")){
            remote = 1;
        }
        else{
            remote = 0;
        }
        if (one.equals("별점")){
            if (two.equals("리뷰수")){
                List<Member> members = memberCRUDRepository.findByMentorListOneIsRaiting1(requestval.getDOW() , requestval.getExpStat(), remote,requestval.getMinPrice(),
                        requestval.getMaxPrice(), requestval.getCategory(), requestval.getProfessional());
                return members;
            }
            else{
                List<Member> members = memberCRUDRepository.findByMentorListOneIsRaiting2(requestval.getDOW() , requestval.getExpStat(), remote,requestval.getMinPrice(),
                        requestval.getMaxPrice(), requestval.getCategory(), requestval.getProfessional());
                return members;
            }
        }
        if (one.equals("리뷰수")){
            if (two.equals("별점")){
                List<Member> members = memberCRUDRepository.findByMentorListOneIsReview_cnt1(requestval.getDOW() , requestval.getExpStat(), remote,requestval.getMinPrice(),
                        requestval.getMaxPrice(), requestval.getCategory(), requestval.getProfessional());
                return members;
            }
            else{
                List<Member> members = memberCRUDRepository.findByMentorListOneIsReview_cnt2(requestval.getDOW() , requestval.getExpStat(), remote,requestval.getMinPrice(),
                        requestval.getMaxPrice(), requestval.getCategory(), requestval.getProfessional());
                return members;
            }
        }

        if (one.equals("매칭횟수")){
            if (two.equals("별점")){
                List<Member> members = memberCRUDRepository.findByMentorListOneIsRes_rate1(requestval.getDOW() , requestval.getExpStat(), remote,requestval.getMinPrice(),
                        requestval.getMaxPrice(), requestval.getCategory(), requestval.getProfessional());
                System.out.println(members);
                return members;
            }
            else{
                List<Member> members = memberCRUDRepository.findByMentorListOneIsRes_rate2(requestval.getDOW() , requestval.getExpStat(), remote,requestval.getMinPrice(),
                        requestval.getMaxPrice(), requestval.getCategory(), requestval.getProfessional());
                return members;
            }
        }
        return null;
    }
    @PostMapping(value = "/rematchinglist")public String rematchinglist(@RequestParam(value = "request")Long id,Model model){
        Advicerequest requestval = adviceRequestCRUDRepository.findByRequest(id);
        String re_matched = requestval.getUser_name();
        String one, two, three;
        one= requestval.getOne();
        two = requestval.getTwo();
        three = requestval.getThree();
        int remote;
        if (requestval.getOnoffline().equals("대면")){
            remote = 1;
        }
        else{
            remote = 0;
        }
        if (one.equals("별점")){
            if (two.equals("리뷰수")){
                List<Member> members = memberCRUDRepository.findByReMentorListOneIsRaiting1(requestval.getDOW() , requestval.getExpStat(), remote,requestval.getMinPrice(),
                        requestval.getMaxPrice(), requestval.getCategory(), re_matched, requestval.getProfessional());
                model.addAttribute("list", members);
                System.out.println(members);
                model.addAttribute("id", id);
                return "/adviceRequestMentorList";
            }
            else{
                List<Member> members = memberCRUDRepository.findByReMentorListOneIsRaiting2(requestval.getDOW() , requestval.getExpStat(), remote,requestval.getMinPrice(),
                        requestval.getMaxPrice(), requestval.getCategory(), re_matched, requestval.getProfessional());
                model.addAttribute("list", members);
                model.addAttribute("id", id);
                return "/adviceRequestMentorList";
            }
        }
        if (one.equals("리뷰수")){
            if (two.equals("별점")){
                List<Member> members = memberCRUDRepository.findByReMentorListOneIsReview_cnt1(requestval.getDOW() , requestval.getExpStat(), remote,requestval.getMinPrice(),
                        requestval.getMaxPrice(), requestval.getCategory(), re_matched, requestval.getProfessional());
                model.addAttribute("list", members);
                model.addAttribute("id", id);
                return "/adviceRequestMentorList";
            }
            else{
                List<Member> members = memberCRUDRepository.findByReMentorListOneIsReview_cnt2(requestval.getDOW() , requestval.getExpStat(), remote,requestval.getMinPrice(),
                        requestval.getMaxPrice(), requestval.getCategory(), re_matched, requestval.getProfessional());
                model.addAttribute("list", members);
                model.addAttribute("id", id);
                return "/adviceRequestMentorList";
            }
        }

        if (one.equals("매칭횟수")){
            if (two.equals("별점")){
                List<Member> members = memberCRUDRepository.findByReMentorListOneIsRes_rate1(requestval.getDOW() , requestval.getExpStat(), remote,requestval.getMinPrice(),
                        requestval.getMaxPrice(), requestval.getCategory(), re_matched, requestval.getProfessional());
                model.addAttribute("list", members);
                model.addAttribute("id", id);
                return "/adviceRequestMentorList";
            }
            else{
                List<Member> members = memberCRUDRepository.findByReMentorListOneIsRes_rate2(requestval.getDOW() , requestval.getExpStat(), remote,requestval.getMinPrice(),
                        requestval.getMaxPrice(), requestval.getCategory(), re_matched, requestval.getProfessional());
                model.addAttribute("list", members);
                model.addAttribute("id", id);
                return "/adviceRequestMentorList";
            }
        }
        return "/main";
    }
    //매칭요청 버튼클릭 로직(관리자(
    @PostMapping(value = "/matchingMentor")
    public String matchingMentor(@RequestBody MatchingMentorDto matchingMentorDto){
        System.out.println("matchingMentorDto = " + matchingMentorDto.getMentor_id());
        System.out.println("matchingMentorDto = " + matchingMentorDto.getRequest_id());
        Advicerequest result = adviceRequestCRUDRepository.findByRequest(Long.valueOf(matchingMentorDto.getRequest_id()));
        Member result2 = memberCRUDRepository.findByMember(Long.valueOf(matchingMentorDto.getMentor_id()));
        Long  mentor = result2.getId();
        result.setMatmentornum(mentor);
        result.setMatching("수락대기");
        result.setUser_waiting("대기");
        result.setMentor_waiting("대기");
        result.setMatmentorname(result2.getName());
        adviceRequestCRUDRepository.save(result);
        return "/";
    }

    //멘토(상담사)가 자신에게 들어온요청 리스트확인
    @PostMapping(value = "/mentorwaitinglist")
    public List<Advicerequest> mentorwaitinglist(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember){
        System.out.println("loginMember = " + loginMember.getId());
        List<Advicerequest> result = adviceRequestCRUDRepository.findByMentorWaitingList(loginMember.getId());
        System.out.println("result = " + result);
        return result;
    }
    //멘토가 요청을 수락거절 로직(멘토가 거절, 수락버튼 눌렀을떄 동작하는거)
    @PostMapping(value = "/mentorwaiting")
    public String mentorwaiting(@RequestBody MentorWaitingDto metorWaitingDto,
                                @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember){
        Advicerequest result = adviceRequestCRUDRepository.findByRequest(Long.valueOf(metorWaitingDto.getRequest_id()));
        Member member = memberCRUDRepository.findByMember(loginMember.getId());
        if (metorWaitingDto.getPopUp().equals("cancel"))
            result.setMentor_waiting("거절");
        else if (metorWaitingDto.getPopUp().equals("confirm"))
            result.setMentor_waiting("수락");

        if (result.getUser_waiting().equals("수락") && result.getMentor_waiting().equals("수락")){
            int match_cnt = member.getMatchCnt();
            int totalMatCount = member.getTotalMatchingCount();
            if (match_cnt<=3){
                result.setMatching("매칭완료");
                match_cnt = match_cnt + 1;
                totalMatCount = totalMatCount +1;
                member.setMatchCnt(match_cnt);
                member.setTotalMatchingCount(totalMatCount);
            }
            else {
                result.setMatching("매칭인원초과");
            }
        }
        if (result.getUser_waiting().equals("수락") && result.getMentor_waiting().equals("거절")){
            result.setMatching("매칭거절");
            member.setRecentMatched(result.getUser_name());
        }
        if (result.getUser_waiting().equals("거절") && result.getMentor_waiting().equals("수락")){
            result.setMatching("매칭거절");
            member.setRecentMatched(result.getUser_name());
        }
        if (result.getUser_waiting().equals("거절") && result.getMentor_waiting().equals("거절")){
            result.setMatching("매칭거절");
            member.setRecentMatched(result.getUser_name());
        }
        if (result.getUser_waiting().equals("대기") && result.getMentor_waiting().equals("거절")){
            result.setMatching("매칭거절");
            member.setRecentMatched(result.getUser_name());
        }
        if (result.getUser_waiting().equals("거절") && result.getMentor_waiting().equals("대기")){
            result.setMatching("매칭거절");
            member.setRecentMatched(result.getUser_name());
        }
        adviceRequestCRUDRepository.save(result);
        memberCRUDRepository.save(member);
        return "/main";
    }

    //유저가 자신의 신청상태 보는거
    @PostMapping(value = "/userwaitinglist1")
    public List<Advicerequest> userwaitinglist1(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        List<Advicerequest> result = adviceRequestCRUDRepository.findByUserWaitingList(loginMember.getId());
        return result;
    }

    @PostMapping(value = "/userwaitinglist2")
    public String userwaitinglist2(@RequestParam(value = "request_id")Long request_id, @RequestParam(value = "mentor_id")Long mentor_id, Model model){
        Member member = memberCRUDRepository.findByMember(mentor_id);
        Advicerequest result = adviceRequestCRUDRepository.findByUserWaitingList2(request_id);
        model.addAttribute("list", member);
        model.addAttribute("list2", result);
        return "/userWaitingList2";
    }
    //user 수락,거절
    @PostMapping(value = "/userwaiting")
    public String userwaiting(@RequestBody UserWaitingDto userWaitingDto,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        Advicerequest result = adviceRequestCRUDRepository.findByUserIDRequest(Long.valueOf(userWaitingDto.getRequest_id()));
        Member member = memberCRUDRepository.findByNameMember(userWaitingDto.getMatmentorname());
        int match_cnt = member.getMatchCnt();
        int totalMatCount = member.getTotalMatchingCount();
        if (userWaitingDto.getPopUp().equals("cancel"))
            result.setUser_waiting("거절");
        else if (userWaitingDto.getPopUp().equals("confirm"))
            result.setUser_waiting("수락");

        if (result.getUser_waiting().equals("수락") && result.getMentor_waiting().equals("수락")) {
            if (match_cnt <= 3) {
                result.setMatching("매칭완료");
                match_cnt = match_cnt + 1;
                totalMatCount = totalMatCount + 1;
                member.setMatchCnt(match_cnt);
                member.setTotalMatchingCount(totalMatCount);
            }
        }
        if (result.getUser_waiting().equals("수락") && result.getMentor_waiting().equals("거절")){
            result.setMatching("매칭거절");
            member.setRecentMatched(result.getUser_name());
        }
        if (result.getUser_waiting().equals("거절") && result.getMentor_waiting().equals("수락")){
            result.setMatching("매칭거절");
            member.setRecentMatched(result.getUser_name());
        }
        if (result.getUser_waiting().equals("거절") && result.getMentor_waiting().equals("거절")){
            result.setMatching("매칭거절");
            member.setRecentMatched(result.getUser_name());
        }
        if (result.getUser_waiting().equals("대기") && result.getMentor_waiting().equals("거절")){
            result.setMatching("매칭거절");
            member.setRecentMatched(result.getUser_name());
        }
        if (result.getUser_waiting().equals("거절") && result.getMentor_waiting().equals("대기")){
            result.setMatching("매칭거절");
            member.setRecentMatched(result.getUser_name());
        }
        adviceRequestCRUDRepository.save(result);
        memberCRUDRepository.save(member);
        MatchingHistory matchingHistory = new MatchingHistory();
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        matchingHistory.setMatchingStartDate(formattedTime);
        matchingHistory.setMatchedname(userWaitingDto.getMatmentorname());
        matchingHistory.setUsername(loginMember.getName());
        matchingHistory.setSignificant(result.getSignificant());
        matchingHistory.setCategory(result.getCategory());

        matchingHistory.setStatus("진행");
        matchHistoryRepository.save(matchingHistory);
        return "/main";
    }

    @GetMapping(value = "/ReMatchingList")public String ReMatchingList(Model model){
        List<Advicerequest> rematchinglist = adviceRequestCRUDRepository.findByReMatchingList();
        model.addAttribute("list", rematchinglist);
        return "/rematchinglist";
    }
}

