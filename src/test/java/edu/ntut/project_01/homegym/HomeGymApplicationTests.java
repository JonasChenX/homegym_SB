package edu.ntut.project_01.homegym;

import edu.ntut.project_01.homegym.exception.category.MemberNotExistException;
import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.model.Orders;
import edu.ntut.project_01.homegym.repository.CourseRepository;
import edu.ntut.project_01.homegym.repository.MemberRepository;
import edu.ntut.project_01.homegym.repository.OrdersRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HomeGymApplicationTests {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private JavaMailSender mailSender;

    @Test
    void contextLoads() {
    }

    @Test
    void insertTest() {
        List<Course> vbList;
        Course vb;
        String row;
        String[] col;
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        vbList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("/Users/chin/Desktop/video.csv"))) {
            int times = 0;
            while ((row = br.readLine()) != null) {
                if (times != 0) {
                    vb = new Course();
                    col = row.split(",");
                    vb.setCourseName(col[1]);
                    vb.setCourseInfo((col[2]).toCharArray());
                    vb.setCategory(col[3]);
                    vb.setPartOfBody(col[4]);
                    try {
                        vb.setUploadTime(format.parse(col[6]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    vb.setPrice(Integer.parseInt(col[7]));
                    vb.setEquipment(col[8]);
                    vb.setLevel(col[9]);
                    vb.setPass(Integer.parseInt(col[10]));
                    vb.setChecked(Integer.parseInt(col[11]));
                    vbList.add(vb);
                }
                times++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        courseRepository.saveAll(vbList);
        System.out.println("程式結束(Done...!!)");
    }

    @Autowired
    private FQARepository fQARepository;

    @Test
    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("homegym_ntut_pj01@outlook.com");
        message.setTo("zhps7239@yahoo.com.tw");
        message.setSubject("主旨：JavaMail測試！！！！");
        message.setText("內容：這是一封測試信件，恭喜您成功發送了唷");

        mailSender.send(message);
    }

    @Transactional
    @Test
    public void fkTest() {
        Optional<Member> member = memberRepository.findMemberByMemberId(2);
//        System.out.println(member.getCoach().getExperience());

        if (member.isPresent()) {
            Set<Orders> orders = member.get().getOrders();
            for (Orders v : orders) {
                System.out.println(v.getCourses());
            }
        } else {
            throw new MemberNotExistException("用戶不存在");
        }
    }

    @Transactional
    @Test
    public void courseList() {
        Optional<Member> member = memberRepository.findById(6);
        Set<Course> myCourses;
        if (member.isPresent()) {
            Set<Orders> orders = member.get().getOrders();
            myCourses = new HashSet<>();
            for (Orders orderList : orders) {
                Set<Course> courses = orderList.getCourses();
                for (Course course : courses) {
                    System.out.println(course.getCourseName());
                    myCourses.add(course);
                }
            }
        }
    }
//    @Transactional
//    @Test
//    public void orderStatusOK(){
//        Collection<String> status = new HashSet<>();
//        status.add("付款完成");
//        List<Orders> orders = ordersRepository.findOrdersByOrderStatusIn(status);
//
//        for (Orders o : orders){
//            System.out.println("/////////////////////////////////////");
//            System.out.println(o.getOrderId());
//            System.out.println(o.getCourses());
//            System.out.println(o.getMember().getMemberId());
//            System.out.println("/////////////////////////////////////");
//        }
//    }

}
