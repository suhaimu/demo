package com.example.demo.coco.controller;

import com.example.demo.coco.mapper.QuestionMapper;
import com.example.demo.coco.model.Question;
import com.example.demo.coco.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import com.example.demo.coco.mapper.UserMapper;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            Model model,
            HttpServletRequest request){

        User user=null;
        Cookie[] cookies=request.getCookies();
        for(Cookie cookie :cookies){
            if(cookie.getName().equals("token")){
                String token =cookie.getValue();
                user =userMapper.findByToken(token);
                if(user != null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question =new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";
    }
}
