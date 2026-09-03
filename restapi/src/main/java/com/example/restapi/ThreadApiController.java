package com.example.restapi;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/threads")
@CrossOrigin(origins = "*")
public class ThreadApiController {
    
    private final ThreadRepository threadRepository;

    public ThreadApiController(ThreadRepository threadRepository){
        this.threadRepository = threadRepository;
    }

    @GetMapping 
    public List<Thread> getAllThreads(){
        return threadRepository.findAll();
    }
    
    @PostMapping
    public Thread createThread(@RequestBody Thread thread){
        return threadRepository.save(thread);
    }
}
