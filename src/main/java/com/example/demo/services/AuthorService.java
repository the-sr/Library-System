package com.example.demo.services;

import com.example.demo.models.Author;
import com.example.demo.payloads.req.AuthorReq;

public interface AuthorService {

    Author addAuthor(AuthorReq authorReq);

}
