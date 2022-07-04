package com.hongdatchy.parkingspaceandbikeshare.socket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

public class UserInterceptor implements ChannelInterceptor {


    /**
     * hàm xác thực hành socket message từ android app
     * hiện tại có thể chưa cần tới nhưng trong tương lai nếu cần xác thực thì sẽ cần
     *
     * vì tác vụ của dự án nên hàm chỉ cho app giử 1 trong 3 loại bản tin CONNECT, SUBSCRIBE và DISCONNECT
     *
     * @param message message từ android gửi lên
     * @param channel channel
     * @return message nếu là 1 trong 3 bản tin ở trên thì trả về message, nếu không trả về null
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        System.out.println("message:       " + message);
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        System.out.println("accessor.getCommand()" + accessor.getCommand());

        if (StompCommand.CONNECT.equals(accessor.getCommand())
        || StompCommand.SUBSCRIBE.equals(accessor.getCommand())
        || StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            return message;
        }
        return null;
    }
}
