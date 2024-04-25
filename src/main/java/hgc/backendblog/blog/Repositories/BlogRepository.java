package hgc.backendblog.blog.Repositories;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import hgc.backendblog.blog.Entity.Blog;
/*
@Repository
public class BlogRepository {

    private final Firestore firestore;
    private final CollectionReference blogsCollection;

    public BlogRepository() {
        this.firestore = FirestoreClient.getFirestore();
        this.blogsCollection = firestore.collection("blogs");
    }

    public List<Blog> findAll() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshot = blogsCollection.get();
        List<Blog> blogs = new ArrayList<>();
        for (var document : querySnapshot.get().getDocuments()) {
            blogs.add(document.toObject(Blog.class));
        }
        return blogs;
    }

    public Blog findById(String blogId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = blogsCollection.document(blogId);
        ApiFuture<DocumentSnapshot> documentSnapshot = docRef.get();
        DocumentSnapshot document = documentSnapshot.get();
        if (document.exists()) {
            return document.toObject(Blog.class);
        }
        return null;
    }

    public Blog save(Blog blog) {
        DocumentReference docRef = blogsCollection.document();
        blog.setId(docRef.getId());
        docRef.set(blog);
        return blog;
    }

    public void deleteById(String blogId) {
        blogsCollection.document(blogId).delete();
    }
}*/
