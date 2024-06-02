package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

// Stub
public class PostRepository {
    private final ConcurrentMap<Long, Post> storagePosts = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger();

    public Collection<Post> all() {
        return storagePosts.values();
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(storagePosts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() != 0) {
            Optional<Post> currentPost = getById(post.getId());
            if (currentPost.isEmpty()) {
                throw new NotFoundException("Post with id " + post.getId() + " not found");
            } else {
                currentPost.get().setContent(post.getContent());
                return currentPost.get();
            }
        } else {
            Post newPost = new Post(counter.incrementAndGet(), post.getContent());
            storagePosts.put(newPost.getId(), newPost);
            return newPost;
        }
    }

    public void removeById(long id) {
        storagePosts.remove(id);
    }
}
