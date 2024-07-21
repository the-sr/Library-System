package com.example.demo.services;

import com.example.demo.payloads.req.AuthorReq;
import com.example.demo.payloads.res.AuthorRes;

public interface AuthorService {

    AuthorRes addAuthor(AuthorReq authorReq,long BookId);

}
