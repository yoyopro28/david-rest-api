package com.example.restapi;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/threads")
public class BoardController {

    private final ThreadRepository threadRepository;

    public BoardController(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    @GetMapping
    public List<Thread> getThreads(
        @RequestParam(required = false) String search){
            if(search == null || search.trim().isEmpty()) {
                return threadRepository.findAll();
            }
            return threadRepository.searchThreads(search);
        }
    
        
        
    

    @GetMapping("/{id}")
    public ResponseEntity<Thread> getById(@PathVariable int id){
        return threadRepository.findById(id).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Thread> createThread(@RequestBody Thread newThread){
        Thread savedThread = threadRepository.save(newThread);
        return ResponseEntity.status(201).body(savedThread);
    }

    @PostMapping("/{id}/replies") 
    public ResponseEntity<Reply> createReply(@PathVariable Integer id, @RequestBody Reply newReply)
    {
        Optional<Thread> threadOptional = threadRepository.findById(id);

        if(threadOptional.isPresent()){
            Thread thread = threadOptional.get();

            thread.addReply(newReply);

            Thread updated = threadRepository.save(thread);

            Reply saved = updated.getReplies().get(updated.getReplies().size()-1);
            return ResponseEntity.status(201).body(saved);
        }
        else return ResponseEntity.notFound().build();
        
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThread(@PathVariable Integer id) {
        if(threadRepository.existsById(id)){
            threadRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/replies/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Integer threadId, @PathVariable Integer replyId) {
        
        Optional<Thread> threadOptional = threadRepository.findById(threadId);
        
        if(threadOptional.isPresent()){
            Thread thread = threadOptional.get();
            boolean removed = thread.getReplies().removeIf(reply -> reply.getId().equals(replyId));

            if(removed) {
                threadRepository.save(thread);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
