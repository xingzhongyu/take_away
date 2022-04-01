package com.example.demo07.controller;

import com.example.demo07.bean.*;
import com.example.demo07.service.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.code.kaptcha.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
public class MainController {
    @Autowired
    TaUserService taUserService;
    @Autowired
    TaDinerService taDinerService;
    @Autowired
    TaFoodService taFoodService;
    @Autowired
    TaOrderService taOrderService;
    @Autowired
    TaCommentService taCommentService;
    @Autowired
    TaCourierService taCourierService;
    @Autowired
    TaChatService taChatService;
    @Autowired
    TaCouponService taCouponService;

    @PostMapping(value = "/register")
    public ResponseEntity<TaUser> register(@RequestBody TaUser taUser){
        if (taUserService.register(taUser).getUserId()==0){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(taUser,HttpStatus.OK);
        }
    }
    @Value("${ta.admin.username}")
    String adminUsername;
    @Value("${ta.admin.password}")
    String adminPassword;
    @PostMapping(value = "/login")
    public ResponseEntity<TaUser> login(HttpServletRequest request,@RequestBody Map<String, String > map){
        String nickname=map.get("nickname");
        String password=map.get("password");
        String type=map.get("type");
        String code=map.get("code");
        HttpSession session=request.getSession();
        Cookie[] cookies=request.getCookies();
        String sessionCode = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        System.out.println(" session中获取的验证码:  " + sessionCode);
        if("admin".equals(type)&&adminUsername.equals(nickname)&&adminPassword.equals(password)){
            TaUser admin=new TaUser();
            admin.setUserId(-1);
            return new ResponseEntity<>(admin,HttpStatus.OK);
        }
        TaUser taUser=taUserService.login(nickname,password);
        if (taUser==null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }else {
            if(code!=null&&(!code.equals(sessionCode))){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }else {
                return new ResponseEntity<>(taUser,HttpStatus.OK);
            }
        }
    }
    @GetMapping(value = "/getUser")
    public ResponseEntity<TaUser> getUserById(@RequestParam("userId")Long userId){
        TaUser taUser= taUserService.getUserById(userId);
        if(taUser==null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(taUser,HttpStatus.OK);
        }
    }
    @PostMapping(value = "/insertDiner")
    public ResponseEntity<Long> insertDiner(@RequestBody TaDiner taDiner){
        long dinerId=taDinerService.insertTaDiner(taDiner);
        if(dinerId==0){
            return new ResponseEntity<>(dinerId,HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(dinerId,HttpStatus.OK);
        }
    }
    @GetMapping(value = "/getTaDiner")
    public ResponseEntity<List<TaDiner>> getTaDiner(@RequestParam(value = "userId",required = false) Long userId,@RequestParam(value = "word",required = false) String word){
        if (userId==null){
            return new ResponseEntity<>(taDinerService.getTaDiner(word),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(taDinerService.getTaDinerByUserId(userId),HttpStatus.OK);
        }
    }
    @GetMapping(value = "/TaDinerPage")
    public ResponseEntity<PageInfo<TaDiner>> getTaDinerPage(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,@RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,@RequestParam(value = "word",required = false)String word){
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<TaDiner> pageInfo=new PageInfo<>(taDinerService.getTaDiner(word));
        return new ResponseEntity<>(pageInfo,HttpStatus.OK);
    }
    /**上传地址*/
    @Value("${file.upload.path}")
    private String path;
    /**图片url前缀*/
    @Value("${com.test.base_picture_url}")
    private String base_picture_url;
    @PostMapping(value = "/upload")
    public ResponseEntity<Map<String,Object>> upload(@RequestParam("file") MultipartFile file) {
        File filePath = new File(path);
        if(!filePath.exists() && !filePath.isDirectory()){
            filePath.mkdir();
        }
        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String name = originalFileName.substring(0,originalFileName.lastIndexOf("."));
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String fileName = date + name + "." +type;
        File targetFile = new File(path,fileName);
        Map<String,Object> result = new HashMap<>();//定义结果
        try{
            file.transferTo(targetFile);
        }catch (IOException e){
            result.put("code",400);
            e.printStackTrace();
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        result.put("code",200);
        result.put("picture",base_picture_url+ fileName);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @PostMapping(value = "/insertTaFood")
    public ResponseEntity<TaFood> insertTaFood(@RequestBody TaFood taFood){
        return new ResponseEntity<>(taFoodService.insertTaFood(taFood),HttpStatus.OK);
    }
    @DeleteMapping(value = "/TaFood")
        public ResponseEntity<Long> deleteTaFood(@RequestParam("foodId") Long foodId){
        return new ResponseEntity<>(taFoodService.deleteTaFood(foodId),HttpStatus.OK);
    }
    @GetMapping(value = "/TaFoodByCategory")
    public ResponseEntity<List<TaFood>> getTaDinerByCategoryId(@RequestParam("category") String category, @RequestParam("dinerId")Long dinerId){
        return new ResponseEntity<>(taFoodService.getTaFoodByCategory(category,dinerId),HttpStatus.OK);
    }
    @GetMapping(value = "/TaFoodByDinerId")
    public ResponseEntity<List<TaFood>> getTaDinerByDinerId(@RequestParam("dinerId") Long dinerId){
        return new ResponseEntity<>(taFoodService.getTaFoodByDinerId(dinerId),HttpStatus.OK);
    }
    @PutMapping(value = "/TaFood")
    public ResponseEntity<Long> changeTaFood(@RequestBody TaFood taFood){
        return new ResponseEntity<>(taFoodService.changeTaFood(taFood),HttpStatus.OK);
    }
    @GetMapping(value = "/TaOrder")
    @ResponseBody
    public ResponseEntity<List<Web_Order>> TaOrderByUserId(@RequestParam(value = "id",required = false)Long id, @RequestParam("type")String type){
        return new ResponseEntity<>(taOrderService.getTaOrder(id,type),HttpStatus.OK);
    }
    @GetMapping(value = "/TaOrderPage")
    public ResponseEntity<PageInfo<Web_Order>> TaOrderByUserIdPage(@RequestParam(value = "id",required = false)Long id,@RequestParam("type")String type,@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,@RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize){
        Page<Web_Order> page=PageHelper.startPage(pageNum,pageSize);
        PageInfo<Web_Order> pageInfo=new PageInfo<>(taOrderService.getTaOrder(id, type));
        pageInfo.setEndRow(page.getEndRow());
        pageInfo.setStartRow(page.getStartRow());
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(page.getPageSize());
        pageInfo.setTotal(page.getTotal());
        return new ResponseEntity<>(pageInfo,HttpStatus.OK);
    }
    @PostMapping(value = "/TaOrder")
    public ResponseEntity<TaOrder> insertTaOrder(@RequestBody TaOrder taOrder){
        return new ResponseEntity<>(taOrderService.insertTaOrder(taOrder),HttpStatus.OK);
    }
    @PutMapping(value = "/TaOrder")
    public ResponseEntity<Long> changeTaOrder(@RequestBody TaOrder taOrder){
        return new ResponseEntity<>(taOrderService.changeTaOrder(taOrder),HttpStatus.OK);
    }
    @GetMapping(value = "/TaComment")
    public ResponseEntity<TaComment> getTaComment(@RequestParam("orderId")Long orderId,@RequestParam("type")String type){
        return new ResponseEntity<>(taCommentService.getTaComment(orderId, type),HttpStatus.OK);
    }
    @PostMapping(value = "/TaComment")
    public ResponseEntity<TaComment> insertTaComment(@RequestBody TaComment taComment){
        return new ResponseEntity<>(taCommentService.insertTaComment(taComment),HttpStatus.OK);
    }
    @GetMapping(value = "/TaCommentByDinerId")
    public ResponseEntity<List<Web_Comment>> getTaCommentByDinerId(@RequestParam("dinerId") Long dinerId ){
        return new ResponseEntity<>(taCommentService.getTaCommentByDinerId(dinerId),HttpStatus.OK);
    }
    @GetMapping(value = "/TaCommentByCourierId")
    public ResponseEntity<List<Web_Comment>> getTaCommentByCourierId(@RequestParam("courierId") Long courierId){
        return new ResponseEntity<>(taCommentService.getTaCommentByCourierId(courierId),HttpStatus.OK);
    }
    @PostMapping(value = "/registerCourier")
    public ResponseEntity<TaCourier> registerCourier(@RequestBody TaCourier taCourier){
        return new ResponseEntity<>(taCourierService.register(taCourier),HttpStatus.OK);
    }
    @PostMapping(value = "/loginCourier")
    public ResponseEntity<TaCourier> loginCourier(@RequestBody Map<String,String> map){
        String username=map.get("nickname");
        String password=map.get("password");
        TaCourier taCourier=taCourierService.login(username,password);
        if(taCourier==null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(taCourier,HttpStatus.OK);

        }
    }
    @GetMapping(value = "/TaChat")
    public ResponseEntity<List<TaChat>> getTaChat(@RequestParam("orderId")Long orderId){
        return new ResponseEntity<>(taChatService.getTaChatByOrderId(orderId),HttpStatus.OK);
    }
    @PostMapping(value = "/TaChat")
    public ResponseEntity<TaChat> insertTaChat(@RequestBody TaChat taChat){
        return  new ResponseEntity<>(taChatService.insertTaChat(taChat),HttpStatus.OK);
    }
    @PostMapping(value = "/TaCoupon")
    public ResponseEntity<TaCoupon> insertTaCoupon(@RequestBody TaCoupon taCoupon){
        return new ResponseEntity<>(taCouponService.insertTaCoupon(taCoupon),HttpStatus.OK);
    }
    @GetMapping(value = "/TaCouponByUserId")
    public ResponseEntity<List<TaCoupon>> getTaCouponByUserId(@RequestParam("userId")Long userId){
        return new ResponseEntity<>(taCouponService.getTaCouponByUserId(userId),HttpStatus.OK);
    }
    @GetMapping(value = "/TaCoupon")
    public ResponseEntity<List<TaCoupon>> getTaCoupon(){
        return new ResponseEntity<>(taCouponService.getTaCoupon(),HttpStatus.OK);
    }
    @DeleteMapping(value = "/TaCoupon")
    @Transactional
    public ResponseEntity<Long> deleteTaCoupon(@RequestParam("couponId") Long couponId){
        return new ResponseEntity<>(taCouponService.deleteTaCoupon(couponId),HttpStatus.OK);
    }
    @PutMapping(value = "/TaCoupon")
    public ResponseEntity<Long> changeTaCoupon(@RequestBody TaCoupon taCoupon){
        return new ResponseEntity<>(taCouponService.changeTaCoupon(taCoupon),HttpStatus.OK);
    }
}
