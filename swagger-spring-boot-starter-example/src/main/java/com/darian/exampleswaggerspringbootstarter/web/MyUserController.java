package com.darian.exampleswaggerspringbootstarter.web;


import com.darian.exampleswaggerspringbootstarter.utils.Response;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import static com.darian.exampleswaggerspringbootstarter.utils.Response.*;

@RestController
public class MyUserController {

    @GetMapping("/usererror")
    public Response usererror() {
        return success(new MyUser("darian", "123456"));
    }

    @GetMapping("/usertrue")
    public Response<MyUser> usertrue() {
        return success(new MyUser("darian", "123456"));
    }

    @PostMapping("/postUser")
    public Response<MyUser> postUser(@RequestBody @Validated MyUser myUser) {
        return success(myUser);
    }


}
