package com.driver;

import java.util.Date;
import java.util.List;

public class WhatsappService extends Exception{

    WhatsappRepository whatsappRepository = new WhatsappRepository();
    public String createUser(String name, String mobile) {

        if(!whatsappRepository.getUserMobile().contains(mobile)) {
            try {
                throw new Exception("User already exists");
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        whatsappRepository.createUser(name, mobile);
        return "SUCCESS";

    }

    public Group createGroup(List<User> users) {
        if(users.size() == 2) {
            return whatsappRepository.createGroup(users.get(1).getName(), users);
        }
        int id = whatsappRepository.getCustomGroupCount()+1;
        whatsappRepository.setCustomGroupCount(id);
        return whatsappRepository.createGroup("Group"+id, users);
    }

    public int createMessage(String content) {
        int msg_id = whatsappRepository.getMessageId()+1;
        whatsappRepository.setMessageId(msg_id);
        Message message = new Message(msg_id, content);
        return msg_id;
    }

    public int sendMessage(Message message, User sender, Group group) {
//        if(!whatsappRepository.getGroupUserMap().containsKey(group)) {
//            throw new Exception("Group does not exist");
//
//        }
        return whatsappRepository.sendMessage(message, sender, group);
    }

    public String changeAdmin(User approver, User user, Group group) {
        if(!whatsappRepository.getGroupUserMap().containsKey(group)) {
            try{
                throw new Exception("Group does not exist");
            }
            catch (Exception e) {
                return e.getMessage();
            }
        }
        if(!whatsappRepository.getAdminMap().get(group).equals(user)) {
            try{
                throw new Exception("Approver does not have rights");
            }
            catch (Exception e) {
                return e.getMessage();
            }
        }
        if(whatsappRepository.getGroupUserMap().get(group).indexOf(user) == -1) {
            try{
                throw new Exception("User is not a participant");
            }
            catch (Exception e) {
                return e.getMessage();
            }
        }

        return whatsappRepository.changeAdmin(user, group);
    }

//    public int removeUser(User user) {
//        return whatsappRepository.removeUser(user);
//    }

//    public String findMessage(Date start, Date end, int k) {
//        return whatsappRepository.findMessage(start, end, k);
//    }
}
