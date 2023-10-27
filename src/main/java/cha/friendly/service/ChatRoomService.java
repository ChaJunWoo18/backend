package cha.friendly.service;

import cha.friendly.domain.ChatMessage;
import cha.friendly.domain.ChatRoom;
import cha.friendly.domain.Dto.ChatReqName;
import cha.friendly.repository.ChatRoomRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepositoryImpl chatRoomRepository;

    public List<ChatRoom> findAllRoom() {
        // 채팅방 생성순서 최근 순으로 반환
        List<ChatRoom> findRooms = chatRoomRepository.findAll();
//        Collections.reverse(findRooms);
        return findRooms;
    }

    //채팅방 생성
    public ChatRoom createChatRoom(ChatReqName chatReqName) {
        ChatRoom chatRoom = ChatRoom.create(chatReqName);
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    public int delChatRoom(String _id) {
        try {
            chatRoomRepository.deleteById(_id);
        } catch (Exception e) {
            log.info("방 삭제 실패!!!");
            return 0;
        }
        return 1;
    }

    public ConcurrentHashMap<Object, ChatRoom> findRooms(String username) {
        List<ChatRoom> rooms1 = chatRoomRepository.findByUsername1(username);
        List<ChatRoom> rooms2 = chatRoomRepository.findByUsername2(username);
        ConcurrentHashMap<Object, ChatRoom> map = new ConcurrentHashMap<>();
        int i =1;
        if(rooms1 != null) {
            for (ChatRoom room : rooms1)
                map.put(i++,room);
        }
        if (rooms2 != null) {
            for (ChatRoom room : rooms2)
                map.put(i++, room);
        }
        return map;
    }

    public ChatRoom findByRoomId(String roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }
}
