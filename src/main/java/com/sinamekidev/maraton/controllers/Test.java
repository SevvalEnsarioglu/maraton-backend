package com.sinamekidev.maraton.controllers;

import com.sinamekidev.maraton.cloud.BucketManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/test")
public class Test {
    @GetMapping("/")
    public byte[] test() throws IOException {
        return BucketManager.getInstance().readFile("https://maraton.obs.ap-southeast-1.myhuaweicloud.com/c6e471d4-9bb8-4acd-a331-6a781f564c9d.jpg");
    }
}
