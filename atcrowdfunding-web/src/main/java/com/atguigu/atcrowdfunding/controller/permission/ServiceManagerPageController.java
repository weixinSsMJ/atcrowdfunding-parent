package com.atguigu.atcrowdfunding.controller.permission;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServiceManagerPageController {

    @GetMapping("/advert/index.html")
    public String toAdvertisementPage(){
        return "serviceManager/advertisement";
    }

    @GetMapping("/cert/index.html")
    public String toCertPage() {

        return "serviceManager/cert";

    }

    @GetMapping("/message/index.html")
    public String toMessagePage() {

        return "serviceManager/message";

    }

    @GetMapping("/process/index.html")
    public String toProcessPage() {

        return "serviceManager/process";

    }

    @GetMapping("/projectType/index.html")
    public String toProjectTypePage() {

        return "serviceManager/project_type";

    }

    @GetMapping("/tag/index.html")
    public String toTagPage() {

        return "serviceManager/tag";

    }

    @GetMapping("/certtype/index.html")
    public String toTypePage() {

        return "serviceManager/type";

    }

}