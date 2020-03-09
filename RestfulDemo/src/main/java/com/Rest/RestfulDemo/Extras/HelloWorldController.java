package com.Rest.RestfulDemo.Extras;

import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@RestController
public class HelloWorldController {
    @GetMapping("/hello-world")
   /* @RequestMapping(method = RequestMethod.GET,path = "/hello-world")*/
    public String hello()
    {
        return "hello world !!";
    }

    @GetMapping(path="/hello-world-bean")

    public HelloWorldBean helloWorldBean()
    {
        return new HelloWorldBean("Hello World");
    }

    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }




    @PostMapping("/hello-world-Bean")

    public HelloWorldBean helloWorldBean(@RequestBody HelloWorldBean helloWorldBean)
    {
        return helloWorldBean;
    }
}
