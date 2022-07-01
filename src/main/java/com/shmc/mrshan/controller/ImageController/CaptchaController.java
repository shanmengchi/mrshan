package com.shmc.mrshan.controller.ImageController;

import com.baomidou.kisso.captcha.ICaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码
 */
@RestController
@RequestMapping("/yanzhengma")
public class CaptchaController {
    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;
    @Autowired
    private ICaptcha captcha;

    // 生成验证，例如：http://localhost:8088/v1/captcha/image?ticket=123456
    @GetMapping("/image")
    public void image(String ticket) {
        try {
            // 验证码信息存放在缓存中，key = ticket 、 value = 验证码文本内容
            captcha.generate(request, response.getOutputStream(), ticket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 校验图片验证码
    @GetMapping("/verification")
    public boolean verification(String ticket, String code) {
        // ticket 为生成验证码的票据， code 为图片验证码文本内容
        return captcha.verification(request, ticket, code);
    }
}
