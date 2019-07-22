package com.lee;

import com.lee.business.UserAccount;
import com.lee.model.TopicReply;
import com.lee.model.User;
import com.lee.repository.TopicReplyRepository;
import com.lee.repository.UserRepository;
import com.lee.repository.util.DriverConnectionManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class testMain {
  public static void main(String[] args) throws SQLException {
    DriverConnectionManager dcm = new DriverConnectionManager();
    List<TopicReply> list = new TopicReplyRepository(dcm.getConnection()).readAll();

//    UserAccount userAccount = new UserAccount(new UserRepository(dcm.getConnection()));
//    boolean bool = userAccount.checkIfExist("email","gh243700@.com",dcm.getConnection());
//    Map map = userAccount.checkifvaliduser("gh243","admin",dcm.getConnection());
//    User user = (User) map.get("User");
//    boolean boo = (boolean)map.get("boolean");
//    System.out.println(user);
//    System.out.println(boo);
//
//    UserRepository userRepository = new UserRepository(dcm.getConnection());
//    User user1 = userRepository.readById("gh243","admin");
//    System.out.println(user1);




    //    String srt = "DIs-ddd";
    //     System.out.println(srt.replace("-","*"));
    //    Parent_forumDAO parent_forumDAO = new Parent_forumDAO(new
    // DriverConnectionManager().getConnection());
    //    List<Parent_Forum> parent_forums = parent_forumDAO.readAll();
    //    for (Parent_Forum obj: parent_forums){
    //      System.out.println(obj);
    //    }
    //
    //    System.out.println("--------------------------------------------------");
    //
    //    ForumDao forumDAO =
    //        new ForumDao(new DriverConnectionManager().getConnection());
    //   List<Forum> list = forumDAO.readAll();
    //   for (Forum obj: list) {
    //       System.out.println(obj);
    //   }
  }
}
