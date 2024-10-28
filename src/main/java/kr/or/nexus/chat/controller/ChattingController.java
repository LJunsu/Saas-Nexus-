package kr.or.nexus.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import kr.or.nexus.chat.model.service.ChattingService;
import kr.or.nexus.commons.paging.SimpleCondition;
import kr.or.nexus.vo.ChattingRoomMemberVO;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.GroupMemberVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectRoleVO;
import kr.or.nexus.vo.def.ChattingRoomDefaultVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("group/{groupId}/chat")
public class ChattingController {

    @Autowired
    private ChattingService service;
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createChattingRoom(@RequestBody Map<String, Object> chattingRoom,
                                                                  Authentication authentication) {
        String roomName = (String) chattingRoom.get("roomName");
        @SuppressWarnings("unchecked")
        List<String> members = (List<String>) chattingRoom.get("members");

        CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
        MemberManagementVO realUser = wrapper.getRealUser();
        String username = realUser.getMemberId(); // 사용자명 또는 ID를 가져옵니다.

        ChattingRoomDefaultVO newRoom = new ChattingRoomDefaultVO();
        newRoom.setRoomName(roomName);

        try {
            System.out.println("Creating room with name: " + roomName);
            String roomId = service.createChattingRoom(newRoom);
            System.out.println("Created room ID: " + roomId);

            if (roomId != null) {
                System.out.println("Adding members to room: " + members);
                boolean membersAdded = service.addMembersToRoom(roomId, members);
                System.out.println("Members added: " + membersAdded);

                if (membersAdded) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "채팅방 생성이 완료되었습니다.");
                    response.put("roomId", roomId);
                    return ResponseEntity.ok(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "채팅방 생성을 실패하였습니다.");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "채팅방 생성에 실패하였습니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 예외 로그를 콘솔에 출력
            Map<String, Object> response = new HashMap<>();
            response.put("message", "채팅방 생성 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 채팅방 조회
    @GetMapping("/{chattingRoomId}")
    public String selectChattingRoom(Model model, @PathVariable String chattingRoomId, Authentication authentication
    		) {
        List<ChattingRoomDefaultVO> chatRooms = service.selectChattingRoomList();
        model.addAttribute("chatRooms", chatRooms);
        ChattingRoomDefaultVO chattingRoom = service.selectChattingRoomById(chattingRoomId);
        CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
        MemberManagementVO realUser = wrapper.getRealUser();
        model.addAttribute("realUser", realUser);
        model.addAttribute("chattingRoom", chattingRoom);
        return "tiles:groupMember/chat";
    }

    // 채팅방 리스트
    @GetMapping("/list")
    public List<ChattingRoomDefaultVO> selectChattingRoomList(Model model, Authentication authentication
    		) {
        List<ChattingRoomDefaultVO> chatRooms = service.selectChattingRoomList();
        model.addAttribute("chatRooms", chatRooms);
        CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
        MemberManagementVO realUser = wrapper.getRealUser();
        model.addAttribute("realUser", realUser);
        return chatRooms;
    }

    // 메인 페이지
    @GetMapping("/main")
    public String mainPage(Model model, Authentication authentication,
                           @RequestParam(required = false) String chattingRoomId,
                           HttpSession session,
                           @PathVariable String groupId
                       ) {
        List<ChattingRoomDefaultVO> chatRooms = service.selectChattingRoomList();
        model.addAttribute("chatRooms", chatRooms);
        CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
        MemberManagementVO realUser = wrapper.getRealUser();
        model.addAttribute("realUser", realUser);
        if (chattingRoomId != null && !chattingRoomId.isEmpty()) {
            ChattingRoomDefaultVO chattingRoom = service.selectChattingRoomById(chattingRoomId);
            model.addAttribute("chattingRoom", chattingRoom);
        }
        List<MemberManagementVO> groupMembers = service.getGroupMembersByUserId(realUser.getMemberId());
        model.addAttribute("groupMembers", groupMembers);
        MemberManagementVO user =  (MemberManagementVO) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("groupId",groupId);

        return "tiles:groupMember/chat";
    }

    @GetMapping("/groupMembers")
    @ResponseBody
    public ResponseEntity<List<MemberManagementVO>> getGroupMembers(Authentication authentication) {
        try {
            // 현재 로그인한 사용자 정보 가져오기
            CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
            MemberManagementVO realUser = wrapper.getRealUser();

            // 그룹원 목록을 조회하는 서비스 호출
            List<MemberManagementVO> groupMembers = service.getGroupMembersByUserId(realUser.getMemberId());

            // 조회된 그룹원 목록을 리턴
            return new ResponseEntity<>(groupMembers, HttpStatus.OK);
        } catch (Exception e) {
            // 예외를 로그에 기록하고, 클라이언트에게 서버 오류를 응답
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/leave/{chattingRoomId}")
    @ResponseBody
    public ResponseEntity<String> leaveChattingRoom(@PathVariable String chattingRoomId, Authentication authentication) {
        CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
        MemberManagementVO realUser = wrapper.getRealUser();
        String memberId = realUser.getMemberId();

        boolean success = service.removeMemberFromRoom(chattingRoomId, memberId);

        if (success) {
            return new ResponseEntity<>("채팅방에서 나가셨습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("채팅방 나가기를 실패하셨습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addMembers")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addMembersToRoom(@RequestBody Map<String, Object> request) {
        String chattingRoomId = (String) request.get("chattingRoomId");
        @SuppressWarnings("unchecked")
        List<String> members = (List<String>) request.get("members");

        // 서비스 계층에 리스트 형태로 멤버 추가 요청
        boolean success = service.addMembersToRoom(chattingRoomId, members);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "Members added successfully" : "Failed to add members");

        return new ResponseEntity<>(response, success ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/invite")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> inviteMembers(@RequestBody Map<String, Object> requestPayload,
                                                               Authentication authentication) {
        // 1. 요청 본문에서 채팅방 ID와 멤버 목록 추출
        String roomId = (String) requestPayload.get("roomId");
        @SuppressWarnings("unchecked")
        List<String> members = (List<String>) requestPayload.get("members");

        // 2. 현재 인증된 사용자 정보 확인
        CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
        MemberManagementVO realUser = wrapper.getRealUser();
        String username = realUser.getMemberId(); // 사용자명 또는 ID를 가져옵니다.

        try {
            System.out.println("Inviting members to room ID: " + roomId);
            System.out.println("Members to be invited: " + members);

            // 3. 서비스 호출하여 멤버 추가
            boolean membersInvited = service.addMembersToRoom(roomId, members);
            System.out.println("Members invited: " + membersInvited);

            if (membersInvited) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "멤버 초대가 완료되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "멤버 초대를 실패하였습니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 예외 로그를 콘솔에 출력
            Map<String, Object> response = new HashMap<>();
            response.put("message", "멤버 초대 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/members/{chattingRoomId}")
    @ResponseBody
    public ResponseEntity<List<MemberManagementVO>> getMembersByRoomId(@PathVariable String chattingRoomId) {
        try {
            List<MemberManagementVO> members = service.getMembersByRoomId(chattingRoomId);
            return new ResponseEntity<>(members, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

















}
